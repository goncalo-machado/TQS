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

    private static final String INVALIDDAY = "Invalid Day -> ";

    @Autowired
    private CovidDataService service;

    @GetMapping("/get/country")
    public ResponseEntity<CovidData> getDataByCountryAndOrDayOfData(@RequestParam(value = "country", required = true) String country, @RequestParam(value = "dayOfData", defaultValue = "Today") String dayOfData) throws BadRequestException, BadDayOfDataException{
        log.info("Get Request: Data by country and or dayOfData");
        int day = stringDayToIntDay(dayOfData);

        if (day == -1){
            throw new BadDayOfDataException(INVALIDDAY + day);
        }

        CovidData data = service.getDataByPlaceAndDayOfData(country, day, false);

        if (data == null){
            throw new BadRequestException("Bad request -> country :" + country + " day: " + day);
        }
        
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/get/world")
    public ResponseEntity<CovidData> getDataofWorldByDayOfData(@RequestParam(value = "dayOfData", defaultValue = "Today") String dayOfData) throws BadRequestException, BadDayOfDataException{
        log.info("Get Request: Data by country and or dayOfData");
        int day = stringDayToIntDay(dayOfData);

        if (day == -1){
            throw new BadDayOfDataException(INVALIDDAY + day);
        }

        CovidData data = service.getDataByPlaceAndDayOfData("World", day, false);

        if (data == null){
            throw new BadRequestException("Bad request -> World day: " + day);
        }
        
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/get/continent")
    public ResponseEntity<CovidData> getDataByContinentAndOrDayOfData(@RequestParam(value = "continent", required = true) String continent, @RequestParam(value = "dayOfData", defaultValue = "Today") String dayOfData) throws BadRequestException, BadDayOfDataException{
        log.info("Get Request: Data by continent and or dayOfData");
        int dayofdata = stringDayToIntDay(dayOfData);

        if (dayofdata == -1){
            throw new BadDayOfDataException(INVALIDDAY + dayofdata);
        }

        CovidData data = service.getDataByPlaceAndDayOfData(continent, dayofdata, true);

        if (data == null){
            throw new BadRequestException("Bad request -> continent :" + continent + " day: " + dayofdata);
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

    public int stringDayToIntDay(String stringDay){
        int day = -1;
        if(stringDay.equalsIgnoreCase("TODAY")){
            day = 0;
        } else if(stringDay.equalsIgnoreCase("YESTERDAY")){
            day = 1;
        } else if(stringDay.equalsIgnoreCase("TWO DAYS AGO")){
            day = 2;
        }
        return day;
    }

}
