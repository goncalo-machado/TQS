package pt.ua.tqs.covidtracker.resolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ua.tqs.covidtracker.exceptions.BadUrlException;
import pt.ua.tqs.covidtracker.http.HTTPAPI;
import pt.ua.tqs.covidtracker.models.CovidData;

@Component
public class CovidDataResolver {

    private static final Logger log = LoggerFactory.getLogger(CovidDataResolver.class);
    private static final String WORLD = "World";

    @Autowired
    HTTPAPI httpApi;

    //date = 0 -> today, date = 1 -> 1 day ago, date = 2 -> 2 days ago
    public CovidData getData(String country, int date, boolean isContinent){ 
        String response = null;
        log.info("----Start -> Getting all data from external API----");
        
        try {
            response = this.httpApi.httpGet(urlBuilder(country, date, isContinent));
        } catch (BadUrlException e1) {
            log.error("BadUrlException", e1);
            return null;
        }
        JSONObject json;
        try{
            json = new JSONObject(response);
        }catch(JSONException e){
            log.error("Error in transforming response to JSONObject -> Returning null");
            return null;
        }

        if(json.has("message")){
            log.error("Error in get request to external API -> Returning null");
            return null;
        }
        log.info("---- Successful get request to external API");

        log.info("---- Making CovidData objects out of data");
        CovidData data = jsonToData(response, country.equalsIgnoreCase(WORLD), isContinent);
        data.setDayOfData(date);

        log.info("----End -> Getting all data from external API----");
        return data;
    }

    public String urlBuilder(String country, int date, boolean isContinent){
        
        StringBuilder url = new StringBuilder();
        if(country.equalsIgnoreCase(WORLD)){
            url = url.append("https://disease.sh/v3/covid-19/all");
        
            if (date == 1){
                url = url.append("?yesterday=true");
            } else if (date == 2){
                url = url.append("?twoDaysAgo=true");
            }

        }else{
            if (isContinent){
                url = url.append("https://disease.sh/v3/covid-19/continents/").append(country).append("?");
            }else{
                url = url.append("https://disease.sh/v3/covid-19/countries/").append(country).append("?");
            }
            if (date == 1){
                url = url.append("yesterday=true&");
            } else if (date == 2){
                url = url.append("twoDaysAgo=true&");
            }
            url = url.append("strict=true");
        }
        return url.toString();
    }

    public CovidData jsonToData(String data, boolean worldData, boolean isContinent){
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
            if(!isContinent){
                covidData.setOneCasePerPeople(json.getLong("oneCasePerPeople"));
                covidData.setOneDeathPerPeople(json.getLong("oneDeathPerPeople"));
                covidData.setOneTestPerPeople(json.getLong("oneTestPerPeople"));
            }
            covidData.setActivePerOneMillion(json.getLong("activePerOneMillion"));
            covidData.setRecoveredPerOneMillion(json.getLong("recoveredPerOneMillion"));
            covidData.setCriticalPerOneMillion(json.getLong("criticalPerOneMillion"));
            if(worldData){
                covidData.setCountry(WORLD);
                covidData.setContinent(WORLD);
            }else if(isContinent){
                covidData.setCountry(json.getString("continent"));
                covidData.setContinent(json.getString("continent"));
            }else{
                covidData.setCountry(json.getString("country"));
                covidData.setContinent(json.getString("continent"));
            }
        }catch(JSONException e){
            log.error("Error -> Transforming json to CovidData object", e);
            return null;
        }
        return covidData;
    }
}
