package pt.ua.tqs.covid_tracker.Resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.exceptions.TemplateInputException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.ua.tqs.covid_tracker.HTTP.HTTPAPI;
import pt.ua.tqs.covid_tracker.Models.CovidData;

@Component
public class CovidDataResolver {

    private static final Logger log = LoggerFactory.getLogger(CovidDataResolver.class);

    @Autowired
    HTTPAPI httpApi;

    //date = 0 -> today, date = 1 -> 1 day ago, date = 2 -> 2 days ago
    public CovidData getAllData(int date){ 
        String response = null;
        log.info("----Start -> Getting all data from external API----");

        String url = "https://disease.sh/v3/covid-19/all";
        
        if (date == 1){
            url += "?yesterday=true";
        } else if (date == 2){
            url += "?twoDaysAgo=true";
        }

        try {
            response = this.httpApi.httpGet(url);
            log.info("---- Successful get request to external API");
        } catch (JSONException e) {
            System.err.println(e);
            log.error("Error in get request to external API", e);
            return null;
        }

        log.info("---- Making CovidData objects out of data");
        CovidData data = jsonToData(response, true);

        log.info("----End -> Getting all data from external API----");
        return data;
    }

    //date = 0 -> today, date = 1 -> 1 day ago, date = 2 -> 2 days ago
    public CovidData getCountryData(String country, int date){ 
        String response = null;
        log.info("----Start -> Getting all data from external API----");
        StringBuilder url = new StringBuilder().append("https://disease.sh/v3/covid-19/countries/").append(country);
        
        if (date == 1){
            url = url.append("?yesterday=true&strict=true");
        } else if (date == 2){
            url = url.append("?twoDaysAgo=true&strict=true");
        } else{
            url = url.append("?strict=true");
        }

        try {
            response = this.httpApi.httpGet(url.toString());
            log.info("---- Successful get request to external API");
        } catch (JSONException e) {
            System.err.println(e);
            log.error("Error in get request to external API", e);
            return null;
        }

        log.info("RESPONSE ----------- " + response);
        log.info("---- Making CovidData objects out of data");
        CovidData data = jsonToData(response, false);

        log.info("DATA ----------- " + data);

        log.info("----End -> Getting all data from external API----");
        return data;
    }

    public CovidData jsonToData(String data, boolean allData){
        CovidData covidData = new CovidData();
        try{
            JSONObject json = new JSONObject(data);
            
            covidData.setUpdated( json.getLong("updated"));
            covidData.setCases(json.getInt("cases"));
            covidData.setTodayCases(json.getInt("todayCases"));
            covidData.setDeaths(json.getInt("deaths"));
            covidData.setTodayDeaths(json.getInt("todayDeaths"));
            covidData.setRecovered(json.getInt("recovered"));
            covidData.setTodayRecovered(json.getInt("todayRecovered"));
            covidData.setActive(json.getInt("active"));
            covidData.setCritical(json.getInt("critical"));
            covidData.setCasesPerOneMillion(json.getLong("casesPerOneMillion"));
            covidData.setDeathsPerOneMillion(json.getLong("deathsPerOneMillion"));
            covidData.setTests(json.getInt("tests"));
            covidData.setTestsPerOneMillion(json.getLong("testsPerOneMillion"));
            covidData.setPopulation(json.getInt("population"));
            covidData.setOneCasePerPeople(json.getLong("oneCasePerPeople"));
            covidData.setOneDeathPerPeople(json.getLong("oneDeathPerPeople"));
            covidData.setOneTestPerPeople(json.getLong("oneTestPerPeople"));
            covidData.setActivePerOneMillion(json.getLong("activePerOneMillion"));
            covidData.setRecoveredPerOneMillion(json.getLong("recoveredPerOneMillion"));
            covidData.setCriticalPerOneMillion(json.getLong("criticalPerOneMillion"));
            if(allData){
                covidData.setCountry("World");
                covidData.setContinent("World");
            }else{
                covidData.setCountry(json.getString("country"));
                covidData.setContinent(json.getString("continent"));
            }
        }catch(TemplateInputException e){
            System.err.println(e);
            log.error("Error -> Transforming json to CovidData object", e);
            return null;
        }
        return covidData;
    }
}
