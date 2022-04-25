package pt.ua.tqs.covid_tracker.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ua.tqs.covid_tracker.Cache.Cache;
import pt.ua.tqs.covid_tracker.Models.CovidData;
import pt.ua.tqs.covid_tracker.Resolver.CovidDataResolver;

@Service
public class CovidDataService {
    private static final Logger log = LoggerFactory.getLogger(CovidDataService.class);

    @Autowired
    CovidDataResolver resolver;

    @Autowired
    Cache cache;

    public CovidData getDataByCountryAndDayOfData(String country, int day){
        log.info("Checking data from cache");

        CovidData data = cache.getData(country, day);
        if(data == null){
            log.info("Data not in cache, getting from external API");
            data = resolver.getCountryData(country, day);
            if(data == null){
                log.error("Error getting data from external API -> Returning null");
                return null;
            }
            log.info("Successful get request from API, saving data in cache");
            cache.saveData(data);
            return data;
        }else{
            log.info("Data in cache");
            return data;
        }
    }
}
