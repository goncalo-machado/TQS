package pt.ua.tqs.covidtracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import pt.ua.tqs.covidtracker.cache.Cache;
import pt.ua.tqs.covidtracker.exceptions.BadRequestException;
import pt.ua.tqs.covidtracker.models.CountryDayofData;
import pt.ua.tqs.covidtracker.models.CovidData;
import pt.ua.tqs.covidtracker.services.CovidDataService;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ViewController {
    private static final Logger log = LoggerFactory.getLogger(ViewController.class);

    @Autowired
    CovidDataService service;

    @GetMapping("/")
	public String index(Model model) {
		log.info("Getting the index template");
		return "index";
	}

    @GetMapping("/cache")
	public String cache(Model model) {
		log.info("Get cache template");
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Hits", Cache.getHits());
        hashMap.put("Misses", Cache.getMisses());
        hashMap.put("Get Requests", Cache.getGetRequests());
        hashMap.put("Save Requests", Cache.getSaveRequests());
        hashMap.put("Delete Requests", Cache.getDeleteRequests());

        model.addAttribute("cacheStats", hashMap);
        
		return "cache";
	}

    @PostMapping("/countryStats")
	public String countryStats(@ModelAttribute("countryDayOfData") CountryDayofData countryDayofData, Model model) throws BadRequestException {
		log.info("-- Start -- Getting data for contryStatsPage");

        CovidData data = service.getDataByPlaceAndDayOfData(countryDayofData.getCountry(), countryDayofData.getDayOfData(), false);

        if (data == null){
            throw new BadRequestException("Bad request -> country :" + countryDayofData.getCountry() + " day: " + countryDayofData.getDayOfData());
        }

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("Updated", data.getUpdated());
        hashMap.put("Cases", data.getCases());
        hashMap.put("TodayCases", data.getTodayCases());
        hashMap.put("Deaths", data.getDeaths());
        hashMap.put("TodayDeaths", data.getTodayDeaths());
        hashMap.put("Recovered", data.getRecovered());
        hashMap.put("TodayRecovered", data.getTodayRecovered());
        hashMap.put("Active", data.getActive());
        hashMap.put("Critical", data.getCritical());
        hashMap.put("CasesPerOneMillion", data.getCasesPerOneMillion());
        hashMap.put("DeathsPerOneMillion", data.getDeathsPerOneMillion());
        hashMap.put("Tests", data.getTests());
        hashMap.put("TestsPerOneMillion", data.getTestsPerOneMillion());
        hashMap.put("Population", data.getPopulation());
        hashMap.put("OneCasePerPeople", data.getOneCasePerPeople());
        hashMap.put("OneDeathPerPeople", data.getOneDeathPerPeople());
        hashMap.put("OneTestPerPeople", data.getOneTestPerPeople());
        hashMap.put("ActivePerOneMillion", data.getActivePerOneMillion());
        hashMap.put("RecoveredPerOneMillion", data.getRecoveredPerOneMillion());
        hashMap.put("CriticalPerOneMillion", data.getCriticalPerOneMillion());
        hashMap.put("Country", data.getCountry());
        hashMap.put("Continent", data.getContinent());
        String day = "";
        if(data.getDayOfData() == 0){
            day = "Today";
        }else if(data.getDayOfData() == 1){
            day = "Yesterday";
        }else if(data.getDayOfData() == 2){
            day = "Two Days Ago";
        }
        hashMap.put("DayOfData", day);

        model.addAttribute("covidDataMap", hashMap);


		log.info("-- End -- Getting data for contryStatsPage");
		return "countryStats";
	}
    
}
