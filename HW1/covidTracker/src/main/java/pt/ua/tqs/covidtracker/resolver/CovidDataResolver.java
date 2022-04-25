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

    @Autowired
    HTTPAPI httpApi;

    //date = 0 -> today, date = 1 -> 1 day ago, date = 2 -> 2 days ago
    public CovidData getCountryData(String country, int date){ 
        String response = null;
        log.info("----Start -> Getting all data from external API----");

        try {
            response = this.httpApi.httpGet(urlBuilder(country, date));
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
            log.error("Error in get request to external API -> Returnin null");
            return null;
        }
        log.info("---- Successful get request to external API");

        log.info("RESPONSE ----------- " + response);

        log.info("---- Making CovidData objects out of data");
        CovidData data = jsonToData(response, country.equalsIgnoreCase("World"));

        log.info("DATA ----------- " + data);

        log.info("----End -> Getting all data from external API----");
        return data;
    }

    public String urlBuilder(String country, int date){
        
        StringBuilder url = new StringBuilder();
        if(country.equalsIgnoreCase("World")){
            url = url.append("https://disease.sh/v3/covid-19/all");
        
            if (date == 1){
                url = url.append("?yesterday=true");
            } else if (date == 2){
                url = url.append("?twoDaysAgo=true");
            }

        }else{
            url = url.append("https://disease.sh/v3/covid-19/countries/").append(country).append("?");
            if (date == 1){
                url = url.append("yesterday=true&");
            } else if (date == 2){
                url = url.append("twoDaysAgo=true&");
            }
            url = url.append("strict=true");
        }
        return url.toString();
    }

    public CovidData jsonToData(String data, boolean worldData){
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
            if(worldData){
                covidData.setCountry("World");
                covidData.setContinent("World");
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
