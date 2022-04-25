package pt.ua.tqs.covid_tracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.tqs.covid_tracker.Exceptions.BadUrlException;
import pt.ua.tqs.covid_tracker.HTTP.HTTPAPI;
import pt.ua.tqs.covid_tracker.Models.CovidData;
import pt.ua.tqs.covid_tracker.Resolver.CovidDataResolver;

@ExtendWith(MockitoExtension.class)
public class ResolverTest {
    
    @Mock
    HTTPAPI httpapi;

    @InjectMocks
    CovidDataResolver resolver;

    @Test
    void whenValidJsonUsedInJsonToDataThenReturnValidObject(){
        String stringPortugal = "{\"updated\":1650909316626,\"country\":\"Portugal\",\"countryInfo\":{\"_id\":620,\"iso2\":\"PT\",\"iso3\":\"PRT\",\"lat\":39.5,\"long\":-8,\"flag\":\"https://disease.sh/assets/img/flags/pt.png\"},\"cases\":3791744,\"todayCases\":0,\"deaths\":22162,\"todayDeaths\":0,\"recovered\":0,\"todayRecovered\":0,\"active\":3769582,\"critical\":61,\"casesPerOneMillion\":373830,\"deathsPerOneMillion\":2185,\"tests\":41038444,\"testsPerOneMillion\":4046001,\"population\":10142964,\"continent\":\"Europe\",\"oneCasePerPeople\":3,\"oneDeathPerPeople\":458,\"oneTestPerPeople\":0,\"activePerOneMillion\":371645.01,\"recoveredPerOneMillion\":0,\"criticalPerOneMillion\":6.01}";
        String stringWorld = "{\"updated\":1650911116796,\"cases\":509711160,\"todayCases\":235610,\"deaths\":6243959,\"todayDeaths\":1015,\"recovered\":462626399,\"todayRecovered\":577195,\"active\":40840802,\"critical\":42409,\"casesPerOneMillion\":65391,\"deathsPerOneMillion\":801,\"tests\":6256136679,\"testsPerOneMillion\":792339,\"population\":7895782874,\"oneCasePerPeople\":0,\"oneDeathPerPeople\":0,\"oneTestPerPeople\":0,\"activePerOneMillion\":5172.48,\"recoveredPerOneMillion\":58591.58,\"criticalPerOneMillion\":5.37,\"affectedCountries\":228}";
        JSONObject jsonPortugal;
        String countryPortugal = "", continentPortugal = "";
        try {
            jsonPortugal = new JSONObject(stringPortugal);
            continentPortugal = jsonPortugal.getString("continent");
            countryPortugal = jsonPortugal.getString("country");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        CovidData dataPortugal = resolver.jsonToData(stringPortugal, false);
        CovidData dataWorld = resolver.jsonToData(stringWorld, true);
        assertTrue(dataPortugal != null);
        assertEquals(continentPortugal, dataPortugal.getContinent());
        assertEquals(countryPortugal, dataPortugal.getCountry());
        assertTrue(dataWorld != null);
        assertEquals("World", dataWorld.getContinent());
        assertEquals("World", dataWorld.getCountry());
    }

    @Test
    void whenBadJsonUsedInJsonToDataThenReturnNull(){
        String message = "{\"message\":\"Country not found or doesn't have any cases\"}";
        assertEquals(resolver.jsonToData(message, true), null);

    }

    @Test
    void urlBuilderCheckAllInputsReturnCorrectStrings(){
        assertEquals("https://disease.sh/v3/covid-19/countries/Portugal?strict=true", resolver.urlBuilder("Portugal", 0));
        assertEquals("https://disease.sh/v3/covid-19/countries/Portugal?yesterday=true&strict=true", resolver.urlBuilder("Portugal", 1));
        assertEquals("https://disease.sh/v3/covid-19/countries/Portugal?twoDaysAgo=true&strict=true", resolver.urlBuilder("Portugal", 2));
        assertEquals("https://disease.sh/v3/covid-19/all", resolver.urlBuilder("World", 0));
        assertEquals("https://disease.sh/v3/covid-19/all?yesterday=true", resolver.urlBuilder("World", 1));
        assertEquals("https://disease.sh/v3/covid-19/all?twoDaysAgo=true", resolver.urlBuilder("World", 2));
    }

    @Test 
    void whenBadResponseFromHttpAPIReturnNull() throws BadUrlException{
        String badUrl = "https://disease.sh/v3/covid-19/countries/asdfg?strict=true";
        Mockito.when(httpapi.httpGet(badUrl)).thenReturn("{\"message\":\"Country not found or doesn't have any cases\"}");

        assertEquals(resolver.getCountryData("asdfg", 0), null);
    }

    @Test 
    void whenGoodResponseFromHttpAPIReturnCovidData() throws BadUrlException{
        String goodUrl = "https://disease.sh/v3/covid-19/all";
        String stringWorld = "{\"updated\":1650911116796,\"cases\":509711160,\"todayCases\":235610,\"deaths\":6243959,\"todayDeaths\":1015,\"recovered\":462626399,\"todayRecovered\":577195,\"active\":40840802,\"critical\":42409,\"casesPerOneMillion\":65391,\"deathsPerOneMillion\":801,\"tests\":6256136679,\"testsPerOneMillion\":792339,\"population\":7895782874,\"oneCasePerPeople\":0,\"oneDeathPerPeople\":0,\"oneTestPerPeople\":0,\"activePerOneMillion\":5172.48,\"recoveredPerOneMillion\":58591.58,\"criticalPerOneMillion\":5.37,\"affectedCountries\":228}";

        Mockito.when(httpapi.httpGet(goodUrl)).thenReturn(stringWorld);

        assertTrue(resolver.getCountryData("World", 0).isEqual(resolver.jsonToData(stringWorld, true)));
    }

}
