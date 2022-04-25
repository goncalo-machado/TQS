package pt.ua.tqs.covidTracker.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.tqs.covidTracker.Exceptions.BadDayOfDataException;
import pt.ua.tqs.covidTracker.Exceptions.BadRequestException;
import pt.ua.tqs.covidTracker.Models.CovidData;
import pt.ua.tqs.covidTracker.Services.CovidDataService;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class APIController {
    private static final Logger log = LoggerFactory.getLogger(APIController.class);

    @Autowired
    private CovidDataService service;

    @GetMapping("/get/country")
    public ResponseEntity<CovidData> getDataByCountry_AndOr_DayOfData(@RequestParam(value = "country", required = true) String country, @RequestParam(value = "dayOfData", defaultValue = "Today") String dayOfData) throws BadRequestException, BadDayOfDataException, IOException{
        log.info("Get Request: Data by country and or dayOfData");
        int day = -1;
        if(dayOfData.toUpperCase().equals("TODAY")){
            day = 0;
        } else if(dayOfData.toUpperCase().equals("YESTERDAY")){
            day = 1;
        } else if(dayOfData.toUpperCase().equals("DAY BEFORE YESTERDAY")){
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

}
