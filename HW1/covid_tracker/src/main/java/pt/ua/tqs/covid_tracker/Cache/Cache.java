package pt.ua.tqs.covid_tracker.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import pt.ua.tqs.covid_tracker.Models.CovidData;
import pt.ua.tqs.covid_tracker.Repository.CovidDataRepository;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Cache {
    
    private static final Logger log = LoggerFactory.getLogger(Cache.class);

    private static int hits = 0;
    private static int misses = 0;
    private static int get_requests = 0;
    private static int save_requests = 0;
    private static int delete_requests = 0;

    @Autowired
    CovidDataRepository covidRepo;

    private int ttl;

    public Cache(int ttl){
        this.ttl = ttl;
    }

    public Cache(){
        this.ttl = 180;
    }

    public CovidData getData(String country, int dayOfData){
        
        get_requests++;
        
        log.info("Getting data from repo for country " + country + " and dayOfData " + dayOfData);
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
        log.info("Deleting multiple data from cache -> " + data);
        covidRepo.deleteAll(data);
        delete_requests ++;
    }

    public void deleteSingleData(CovidData data){
        log.info("Deleting single data from cache -> " + data);
        covidRepo.delete(data);
        delete_requests ++;
    }

    public void saveData(CovidData data){
        save_requests++;
        List<CovidData> dataInCache = covidRepo.findAll();
        for (CovidData covidData : dataInCache) {
            if(data.isEqual(covidData)){
                log.info("Data already exists in Cache -> " + data);
                hits ++;
                return;
            }
        }
        misses++;
        log.info("Savig data in cache -> " + data);
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

    public static int getGet_requests() {
        return get_requests;
    }

    public void setGet_requests(int get_requests2) {
        get_requests = get_requests2;
    }

    public static int getSave_requests() {
        return save_requests;
    }

    public void setSave_requests(int save_requests2) {
        save_requests = save_requests2;
    }

    public static int getDelete_requests() {
        return delete_requests;
    }

    public void setDelete_requests(int delete_requests2) {
        delete_requests = delete_requests2;
    }
}
