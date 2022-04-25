package pt.ua.tqs.covidTracker.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import pt.ua.tqs.covidTracker.Models.CovidData;
import pt.ua.tqs.covidTracker.Repository.CovidDataRepository;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Cache {
    
    private static final Logger log = LoggerFactory.getLogger(Cache.class);

    private static int hits = 0;
    private static int misses = 0;
    private static int getRequests = 0;
    private static int saveRequests = 0;
    private static int deleteRequests = 0;

    @Autowired
    CovidDataRepository covidRepo;

    private int ttl;

    public Cache(int ttl){
        this.ttl = ttl;
    }

    public Cache(){
        this.ttl = 180;
    }

    public static void increaseGetRequests(){
        getRequests++;
    }

    public CovidData getData(String country, int dayOfData){
        
        increaseGetRequests();
        
        country = country.replaceAll("[\n\r\t]", "_");
        log.info("Getting data from repo for country {} and dayOfData {}",country, dayOfData);
        CovidData data = covidRepo.findByCountryAndDayOfData(country, dayOfData);

        if(data != null){
            if (isExpired(data)) {
                log.info("Data has expired in the cache");
                misses++;
                deleteSingleData(data);
                return null;
            } else {
                log.info("Data exists in cache");
                hits++;
                return data;
            }
        }else {
            log.info("Data not found in the cache");
            misses++;
            return null;
        }
    }

    public void deleteMultipleData(List<CovidData> data){
        log.info("Deleting multiple data from cache -> {}", data);
        covidRepo.deleteAll(data);
        deleteRequests ++;
    }

    public void deleteSingleData(CovidData data){
        log.info("Deleting single data from cache -> {}", data);
        covidRepo.delete(data);
        deleteRequests ++;
    }

    public void saveData(CovidData data){
        saveRequests++;
        List<CovidData> dataInCache = covidRepo.findAll();
        for (CovidData covidData : dataInCache) {
            if(data.isEqual(covidData)){
                log.info("Data already exists in Cache -> {}", data);
                hits ++;
                return;
            }
        }
        misses++;
        log.info("Savig data in cache -> {}", data);
        covidRepo.save(data);
    }
    
    @Scheduled(fixedRate = 60 * 1000)
    public void cleanCache() {
        log.info("Checking for expired data");
        long expiredDate = System.currentTimeMillis() - this.ttl * 1000;
        List<CovidData> expiredCovidData = covidRepo.findAllByCreatedLessThanEqual(expiredDate);
        deleteMultipleData(expiredCovidData);
    }

    public boolean isExpired(CovidData data){
        log.info("Checking if data {} is expired", data);
        Date expiredDate = new Date(System.currentTimeMillis() - this.ttl * 1000);
        Date dataCreated = new Date(data.getCreated());
        return dataCreated.before(expiredDate);
    }

    public int getTtl() {
        return this.ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }


    public static int getHits() {
        return hits;
    }

    public void setHits(int hits2) {
        hits = hits2;
    }

    public static int getMisses() {
        return misses;
    }

    public void setMisses(int misses2) {
        misses = misses2;
    }

    public static int getGetRequests() {
        return getRequests;
    }

    public void setGetRequests(int getRequests2) {
        getRequests = getRequests2;
    }

    public static int getSaveRequests() {
        return saveRequests;
    }

    public void setSaveRequests(int saveRequests2) {
        saveRequests = saveRequests2;
    }

    public static int getDeleteRequests() {
        return deleteRequests;
    }

    public void setDeleteRequests(int deleteRequests2) {
        deleteRequests = deleteRequests2;
    }
}
