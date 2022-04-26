package pt.ua.tqs.covidtracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.tqs.covidtracker.cache.Cache;
import pt.ua.tqs.covidtracker.exceptions.BadDayOfDataException;
import pt.ua.tqs.covidtracker.exceptions.BadRequestException;
import pt.ua.tqs.covidtracker.models.CovidData;
import pt.ua.tqs.covidtracker.services.CovidDataService;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class APIController {
    private static final Logger log = LoggerFactory.getLogger(APIController.class);

    @Autowired
    private CovidDataService service;

    @GetMapping("/get/country")
    public ResponseEntity<CovidData> getDataByCountryAndOrDayOfData(@RequestParam(value = "country", required = true) String country, @RequestParam(value = "dayOfData", defaultValue = "Today") String dayOfData) throws BadRequestException, BadDayOfDataException{
        log.info("Get Request: Data by country and or dayOfData");
        int day = -1;
        if(dayOfData.equalsIgnoreCase("TODAY")){
            day = 0;
        } else if(dayOfData.equalsIgnoreCase("YESTERDAY")){
            day = 1;
        } else if(dayOfData.equalsIgnoreCase("DAY BEFORE YESTERDAY")){
            day = 2;
        }

        if (day == -1){
            throw new BadDayOfDataException("Invalid Day -> " + day);
        }

        CovidData data = service.getDataByCountryAndDayOfData(country, day);

        if (data == null){
            throw new BadRequestException("Bad request -> country :" + country + " day: " + day);
        }
        
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/cacheStats")
    public ResponseEntity<String> getCacheStats(){
        JSONObject json = new JSONObject();
        json.put("hits", Cache.getHits());
        json.put("misses", Cache.getMisses());
        json.put("getRequests", Cache.getGetRequests());
        json.put("saveRequests", Cache.getSaveRequests());
        json.put("deleteRequests", Cache.getDeleteRequests());

        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

}
