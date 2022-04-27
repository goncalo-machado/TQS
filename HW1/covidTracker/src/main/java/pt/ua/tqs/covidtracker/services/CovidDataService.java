package pt.ua.tqs.covidtracker.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.ua.tqs.covidtracker.cache.Cache;
import pt.ua.tqs.covidtracker.models.CovidData;
import pt.ua.tqs.covidtracker.resolver.CovidDataResolver;

@Service
public class CovidDataService {
    private static final Logger log = LoggerFactory.getLogger(CovidDataService.class);

    @Autowired
    CovidDataResolver resolver;

    @Autowired
    Cache cache;

    public CovidData getDataByPlaceAndDayOfData(String country, int day , boolean isContinent){
        log.info("Checking data from cache");

        CovidData data = cache.getData(country, day);
        if(data == null){
            log.info("Data not in cache, getting from external API");
            data = resolver.getData(country, day, isContinent);
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
