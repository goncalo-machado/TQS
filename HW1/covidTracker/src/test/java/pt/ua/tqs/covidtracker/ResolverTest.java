package pt.ua.tqs.covidtracker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.tqs.covidtracker.exceptions.BadUrlException;
import pt.ua.tqs.covidtracker.http.HTTPAPI;
import pt.ua.tqs.covidtracker.models.CovidData;
import pt.ua.tqs.covidtracker.resolver.CovidDataResolver;

@ExtendWith(MockitoExtension.class)
class ResolverTest {
    
    @Mock
    HTTPAPI httpapi;

    @InjectMocks
    CovidDataResolver resolver;

    @Test
    void whenValidJsonUsedInJsonToDataThenReturnValidObject(){
        String stringPortugal = "{\"updated\":1650909316626,\"country\":\"Portugal\",\"countryInfo\":{\"_id\":620,\"iso2\":\"PT\",\"iso3\":\"PRT\",\"lat\":39.5,\"long\":-8,\"flag\":\"https://disease.sh/assets/img/flags/pt.png\"},\"cases\":3791744,\"todayCases\":0,\"deaths\":22162,\"todayDeaths\":0,\"recovered\":0,\"todayRecovered\":0,\"active\":3769582,\"critical\":61,\"casesPerOneMillion\":373830,\"deathsPerOneMillion\":2185,\"tests\":41038444,\"testsPerOneMillion\":4046001,\"population\":10142964,\"continent\":\"Europe\",\"oneCasePerPeople\":3,\"oneDeathPerPeople\":458,\"oneTestPerPeople\":0,\"activePerOneMillion\":371645.01,\"recoveredPerOneMillion\":0,\"criticalPerOneMillion\":6.01}";
        String stringWorld = "{\"updated\":1650911116796,\"cases\":509711160,\"todayCases\":235610,\"deaths\":6243959,\"todayDeaths\":1015,\"recovered\":462626399,\"todayRecovered\":577195,\"active\":40840802,\"critical\":42409,\"casesPerOneMillion\":65391,\"deathsPerOneMillion\":801,\"tests\":6256136679,\"testsPerOneMillion\":792339,\"population\":7895782874,\"oneCasePerPeople\":0,\"oneDeathPerPeople\":0,\"oneTestPerPeople\":0,\"activePerOneMillion\":5172.48,\"recoveredPerOneMillion\":58591.58,\"criticalPerOneMillion\":5.37,\"affectedCountries\":228}";
        String stringEurope = "{\"updated\": 1651083986595,\"cases\": 190100240,\"todayCases\": 239544,\"deaths\": 1813941,\"todayDeaths\": 1091,\"recovered\": 174460318,\"todayRecovered\": 195782,\"active\": 13825981,\"critical\": 10065,\"casesPerOneMillion\": 253992.38,\"deathsPerOneMillion\": 2423.6,\"tests\": 2652937567,\"testsPerOneMillion\": 3544582.22,\"population\": 748448590,\"continent\": \"Europe\",\"activePerOneMillion\": 18472.85,\"recoveredPerOneMillion\": 233095.93,\"criticalPerOneMillion\": 13.45,\"continentInfo\": {\"lat\": 25.771324,\"long\": -35.6012256},\"countries\": [\"Albania\",\"Andorra\",\"Austria\",\"Belarus\",\"Belgium\",\"Bosnia\",\"Bulgaria\",\"Channel Islands\",\"Croatia\",\"Czechia\",\"Denmark\",\"Estonia\",\"Faroe Islands\",\"Finland\",\"France\",\"Germany\",\"Gibraltar\",\"Greece\",\"Holy See (Vatican City State)\",\"Hungary\",\"Iceland\",\"Ireland\",\"Isle of Man\",\"Italy\",\"Latvia\",\"Liechtenstein\",\"Lithuania\",\"Luxembourg\",\"Macedonia\",\"Malta\",\"Moldova\",\"Monaco\",\"Montenegro\",\"Netherlands\",\"Norway\",\"Poland\",\"Portugal\",\"Romania\",\"Russia\",\"San Marino\",\"Serbia\",\"Slovakia\",\"Slovenia\",\"Spain\",\"Sweden\",\"Switzerland\",\"UK\",\"Ukraine\"]}";
        JSONObject jsonPortugal, jsonEurope;
        String countryPortugal = "", continentPortugal = "", continentEurope = "";
        try {
            jsonPortugal = new JSONObject(stringPortugal);
            jsonEurope = new JSONObject(stringEurope);
            continentPortugal = jsonPortugal.getString("continent");
            countryPortugal = jsonPortugal.getString("country");
            continentEurope = jsonEurope.getString("continent");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CovidData dataPortugal = resolver.jsonToData(stringPortugal, false, false);
        CovidData dataWorld = resolver.jsonToData(stringWorld, true, false);
        CovidData dataEurope = resolver.jsonToData(stringEurope, false, true);
        assertNotNull(dataPortugal);
        assertEquals(continentPortugal, dataPortugal.getContinent());
        assertEquals(countryPortugal, dataPortugal.getCountry());
        assertNotNull(dataWorld);
        assertEquals("World", dataWorld.getContinent());
        assertEquals("World", dataWorld.getCountry());
        assertNotNull(dataEurope);
        assertEquals(continentEurope, dataEurope.getContinent());
        assertEquals(continentEurope, dataEurope.getCountry());
    }

    @Test
    void whenBadJsonUsedInJsonToDataThenReturnNull(){
        String message = "{\"message\":\"Country not found or doesn't have any cases\"}";
        assertEquals(null, resolver.jsonToData(message, true, false));

    }

    @Test
    void urlBuilderCheckAllInputsReturnCorrectStrings(){
        assertEquals("https://disease.sh/v3/covid-19/countries/Portugal?strict=true", resolver.urlBuilder("Portugal", 0, false));
        assertEquals("https://disease.sh/v3/covid-19/countries/Portugal?yesterday=true&strict=true", resolver.urlBuilder("Portugal", 1, false));
        assertEquals("https://disease.sh/v3/covid-19/countries/Portugal?twoDaysAgo=true&strict=true", resolver.urlBuilder("Portugal", 2, false));
        assertEquals("https://disease.sh/v3/covid-19/all", resolver.urlBuilder("World", 0, false));
        assertEquals("https://disease.sh/v3/covid-19/all?yesterday=true", resolver.urlBuilder("World", 1, false));
        assertEquals("https://disease.sh/v3/covid-19/all?twoDaysAgo=true", resolver.urlBuilder("World", 2, false));
        assertEquals("https://disease.sh/v3/covid-19/continents/Europe?strict=true", resolver.urlBuilder("Europe", 0, true));
        assertEquals("https://disease.sh/v3/covid-19/continents/Europe?yesterday=true&strict=true", resolver.urlBuilder("Europe", 1, true));
        assertEquals("https://disease.sh/v3/covid-19/continents/Europe?twoDaysAgo=true&strict=true", resolver.urlBuilder("Europe", 2, true));
    }

    @Test 
    void whenBadResponseFromHttpAPIReturnNull() throws BadUrlException{
        String badUrl = "https://disease.sh/v3/covid-19/countries/asdfg?strict=true";
        Mockito.when(httpapi.httpGet(badUrl)).thenReturn("{\"message\":\"Country not found or doesn't have any cases\"}");

        assertEquals(null, resolver.getData("asdfg", 0, false));
    }

    @Test 
    void whenGoodResponseFromHttpAPIReturnCovidData() throws BadUrlException{
        String goodUrl = "https://disease.sh/v3/covid-19/all";
        String stringWorld = "{\"updated\":1650911116796,\"cases\":509711160,\"todayCases\":235610,\"deaths\":6243959,\"todayDeaths\":1015,\"recovered\":462626399,\"todayRecovered\":577195,\"active\":40840802,\"critical\":42409,\"casesPerOneMillion\":65391,\"deathsPerOneMillion\":801,\"tests\":6256136679,\"testsPerOneMillion\":792339,\"population\":7895782874,\"oneCasePerPeople\":0,\"oneDeathPerPeople\":0,\"oneTestPerPeople\":0,\"activePerOneMillion\":5172.48,\"recoveredPerOneMillion\":58591.58,\"criticalPerOneMillion\":5.37,\"affectedCountries\":228}";

        Mockito.when(httpapi.httpGet(goodUrl)).thenReturn(stringWorld);

        assertTrue(resolver.getData("World", 0, false).isEqual(resolver.jsonToData(stringWorld, true, false)));
    }
}
