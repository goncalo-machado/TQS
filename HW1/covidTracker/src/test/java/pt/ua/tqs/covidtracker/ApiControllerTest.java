package pt.ua.tqs.covidtracker;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pt.ua.tqs.covidtracker.controllers.APIController;
import pt.ua.tqs.covidtracker.models.CovidData;
import pt.ua.tqs.covidtracker.services.CovidDataService;

@WebMvcTest(APIController.class)
class ApiControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CovidDataService service;

    private static final String WORLD = "World";
    private static final String EUROPE = "Europe";
    private static final String PORTUGAL = "Portugal";

    @Test
    void testGetCountryDataUsingExternalAPI() throws Exception{

        CovidData data1 = buildCovidDataObject(PORTUGAL, EUROPE, 0);
        CovidData data2 = buildCovidDataObject(PORTUGAL, EUROPE, 1);
        CovidData data3 = buildCovidDataObject(PORTUGAL, EUROPE, 2);

        Mockito.when(service.getDataByPlaceAndDayOfData(data1.getCountry(), data1.getDayOfData(), false)).thenReturn(data1);
        Mockito.when(service.getDataByPlaceAndDayOfData(data2.getCountry(), data2.getDayOfData(), false)).thenReturn(data2);
        Mockito.when(service.getDataByPlaceAndDayOfData(data3.getCountry(), data3.getDayOfData(), false)).thenReturn(data3);

        mvc.perform(get("/api/get/country")
        .param("country", "Portugal")
        .param("dayOfData", "Today")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.updated").isNotEmpty())
        .andExpect(jsonPath("$.cases").isNotEmpty())
        .andExpect(jsonPath("$.todayCases").isNotEmpty())
        .andExpect(jsonPath("$.deaths").isNotEmpty())
        .andExpect(jsonPath("$.todayDeaths").isNotEmpty())
        .andExpect(jsonPath("$.recovered").isNotEmpty())
        .andExpect(jsonPath("$.todayRecovered").isNotEmpty())
        .andExpect(jsonPath("$.active").isNotEmpty())
        .andExpect(jsonPath("$.critical").isNotEmpty())
        .andExpect(jsonPath("$.casesPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.deathsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.tests").isNotEmpty())
        .andExpect(jsonPath("$.testsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.population").isNotEmpty())
        .andExpect(jsonPath("$.oneCasePerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneDeathPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneTestPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.activePerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.recoveredPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.criticalPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.country").isNotEmpty())
        .andExpect(jsonPath("$.continent").isNotEmpty())
        .andExpect(jsonPath("$.dayOfData").isNotEmpty());

        mvc.perform(get("/api/get/country")
        .param("country", "Portugal")
        .param("dayOfData", "Yesterday")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.updated").isNotEmpty())
        .andExpect(jsonPath("$.cases").isNotEmpty())
        .andExpect(jsonPath("$.todayCases").isNotEmpty())
        .andExpect(jsonPath("$.deaths").isNotEmpty())
        .andExpect(jsonPath("$.todayDeaths").isNotEmpty())
        .andExpect(jsonPath("$.recovered").isNotEmpty())
        .andExpect(jsonPath("$.todayRecovered").isNotEmpty())
        .andExpect(jsonPath("$.active").isNotEmpty())
        .andExpect(jsonPath("$.critical").isNotEmpty())
        .andExpect(jsonPath("$.casesPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.deathsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.tests").isNotEmpty())
        .andExpect(jsonPath("$.testsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.population").isNotEmpty())
        .andExpect(jsonPath("$.oneCasePerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneDeathPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneTestPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.activePerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.recoveredPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.criticalPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.country").isNotEmpty())
        .andExpect(jsonPath("$.continent").isNotEmpty())
        .andExpect(jsonPath("$.dayOfData").isNotEmpty());

        mvc.perform(get("/api/get/country")
        .param("country", "Portugal")
        .param("dayOfData", "Two Days Ago")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.updated").isNotEmpty())
        .andExpect(jsonPath("$.cases").isNotEmpty())
        .andExpect(jsonPath("$.todayCases").isNotEmpty())
        .andExpect(jsonPath("$.deaths").isNotEmpty())
        .andExpect(jsonPath("$.todayDeaths").isNotEmpty())
        .andExpect(jsonPath("$.recovered").isNotEmpty())
        .andExpect(jsonPath("$.todayRecovered").isNotEmpty())
        .andExpect(jsonPath("$.active").isNotEmpty())
        .andExpect(jsonPath("$.critical").isNotEmpty())
        .andExpect(jsonPath("$.casesPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.deathsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.tests").isNotEmpty())
        .andExpect(jsonPath("$.testsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.population").isNotEmpty())
        .andExpect(jsonPath("$.oneCasePerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneDeathPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneTestPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.activePerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.recoveredPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.criticalPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.country").isNotEmpty())
        .andExpect(jsonPath("$.continent").isNotEmpty())
        .andExpect(jsonPath("$.dayOfData").isNotEmpty());
    }

    @Test
    void testGetContinentData() throws Exception{

        CovidData data1 = buildCovidDataObject(EUROPE, EUROPE, 0);
        CovidData data2 = buildCovidDataObject(EUROPE, EUROPE, 1);
        CovidData data3 = buildCovidDataObject(EUROPE, EUROPE, 2);

        Mockito.when(service.getDataByPlaceAndDayOfData(data1.getCountry(), data1.getDayOfData(), true)).thenReturn(data1);
        Mockito.when(service.getDataByPlaceAndDayOfData(data2.getCountry(), data2.getDayOfData(), true)).thenReturn(data2);
        Mockito.when(service.getDataByPlaceAndDayOfData(data3.getCountry(), data3.getDayOfData(), true)).thenReturn(data3);

        mvc.perform(get("/api/get/continent")
        .param("continent", "Europe")
        .param("dayOfData", "Today")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.updated").isNotEmpty())
        .andExpect(jsonPath("$.cases").isNotEmpty())
        .andExpect(jsonPath("$.todayCases").isNotEmpty())
        .andExpect(jsonPath("$.deaths").isNotEmpty())
        .andExpect(jsonPath("$.todayDeaths").isNotEmpty())
        .andExpect(jsonPath("$.recovered").isNotEmpty())
        .andExpect(jsonPath("$.todayRecovered").isNotEmpty())
        .andExpect(jsonPath("$.active").isNotEmpty())
        .andExpect(jsonPath("$.critical").isNotEmpty())
        .andExpect(jsonPath("$.casesPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.deathsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.tests").isNotEmpty())
        .andExpect(jsonPath("$.testsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.population").isNotEmpty())
        .andExpect(jsonPath("$.activePerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.recoveredPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.criticalPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.country").isNotEmpty())
        .andExpect(jsonPath("$.continent").isNotEmpty())
        .andExpect(jsonPath("$.dayOfData").isNotEmpty());

        mvc.perform(get("/api/get/continent")
        .param("continent", "Europe")
        .param("dayOfData", "Yesterday")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.updated").isNotEmpty())
        .andExpect(jsonPath("$.cases").isNotEmpty())
        .andExpect(jsonPath("$.todayCases").isNotEmpty())
        .andExpect(jsonPath("$.deaths").isNotEmpty())
        .andExpect(jsonPath("$.todayDeaths").isNotEmpty())
        .andExpect(jsonPath("$.recovered").isNotEmpty())
        .andExpect(jsonPath("$.todayRecovered").isNotEmpty())
        .andExpect(jsonPath("$.active").isNotEmpty())
        .andExpect(jsonPath("$.critical").isNotEmpty())
        .andExpect(jsonPath("$.casesPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.deathsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.tests").isNotEmpty())
        .andExpect(jsonPath("$.testsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.population").isNotEmpty())
        .andExpect(jsonPath("$.activePerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.recoveredPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.criticalPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.country").isNotEmpty())
        .andExpect(jsonPath("$.continent").isNotEmpty())
        .andExpect(jsonPath("$.dayOfData").isNotEmpty());

        mvc.perform(get("/api/get/continent")
        .param("continent", "Europe")
        .param("dayOfData", "Two Days Ago")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.updated").isNotEmpty())
        .andExpect(jsonPath("$.cases").isNotEmpty())
        .andExpect(jsonPath("$.todayCases").isNotEmpty())
        .andExpect(jsonPath("$.deaths").isNotEmpty())
        .andExpect(jsonPath("$.todayDeaths").isNotEmpty())
        .andExpect(jsonPath("$.recovered").isNotEmpty())
        .andExpect(jsonPath("$.todayRecovered").isNotEmpty())
        .andExpect(jsonPath("$.active").isNotEmpty())
        .andExpect(jsonPath("$.critical").isNotEmpty())
        .andExpect(jsonPath("$.casesPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.deathsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.tests").isNotEmpty())
        .andExpect(jsonPath("$.testsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.population").isNotEmpty())
        .andExpect(jsonPath("$.activePerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.recoveredPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.criticalPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.country").isNotEmpty())
        .andExpect(jsonPath("$.continent").isNotEmpty())
        .andExpect(jsonPath("$.dayOfData").isNotEmpty());
    }


    @Test
    void testGetWorldDataUsingExternalAPI() throws Exception{

        CovidData data1 = buildCovidDataObject(WORLD, WORLD, 0);
        CovidData data2 = buildCovidDataObject(WORLD, WORLD, 1);
        CovidData data3 = buildCovidDataObject(WORLD, WORLD, 2);

        Mockito.when(service.getDataByPlaceAndDayOfData(data1.getCountry(), data1.getDayOfData(), false)).thenReturn(data1);
        Mockito.when(service.getDataByPlaceAndDayOfData(data2.getCountry(), data2.getDayOfData(), false)).thenReturn(data2);
        Mockito.when(service.getDataByPlaceAndDayOfData(data3.getCountry(), data3.getDayOfData(), false)).thenReturn(data3);

        mvc.perform(get("/api/get/world")
        .param("dayOfData", "Today")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.updated").isNotEmpty())
        .andExpect(jsonPath("$.cases").isNotEmpty())
        .andExpect(jsonPath("$.todayCases").isNotEmpty())
        .andExpect(jsonPath("$.deaths").isNotEmpty())
        .andExpect(jsonPath("$.todayDeaths").isNotEmpty())
        .andExpect(jsonPath("$.recovered").isNotEmpty())
        .andExpect(jsonPath("$.todayRecovered").isNotEmpty())
        .andExpect(jsonPath("$.active").isNotEmpty())
        .andExpect(jsonPath("$.critical").isNotEmpty())
        .andExpect(jsonPath("$.casesPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.deathsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.tests").isNotEmpty())
        .andExpect(jsonPath("$.testsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.population").isNotEmpty())
        .andExpect(jsonPath("$.oneCasePerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneDeathPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneTestPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.activePerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.recoveredPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.criticalPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.country").isNotEmpty())
        .andExpect(jsonPath("$.continent").isNotEmpty())
        .andExpect(jsonPath("$.dayOfData").isNotEmpty());

        mvc.perform(get("/api/get/world")
        .param("dayOfData", "Yesterday")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.updated").isNotEmpty())
        .andExpect(jsonPath("$.cases").isNotEmpty())
        .andExpect(jsonPath("$.todayCases").isNotEmpty())
        .andExpect(jsonPath("$.deaths").isNotEmpty())
        .andExpect(jsonPath("$.todayDeaths").isNotEmpty())
        .andExpect(jsonPath("$.recovered").isNotEmpty())
        .andExpect(jsonPath("$.todayRecovered").isNotEmpty())
        .andExpect(jsonPath("$.active").isNotEmpty())
        .andExpect(jsonPath("$.critical").isNotEmpty())
        .andExpect(jsonPath("$.casesPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.deathsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.tests").isNotEmpty())
        .andExpect(jsonPath("$.testsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.population").isNotEmpty())
        .andExpect(jsonPath("$.oneCasePerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneDeathPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneTestPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.activePerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.recoveredPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.criticalPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.country").isNotEmpty())
        .andExpect(jsonPath("$.continent").isNotEmpty())
        .andExpect(jsonPath("$.dayOfData").isNotEmpty());

        mvc.perform(get("/api/get/world")
        .param("dayOfData", "Two Days Ago")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.updated").isNotEmpty())
        .andExpect(jsonPath("$.cases").isNotEmpty())
        .andExpect(jsonPath("$.todayCases").isNotEmpty())
        .andExpect(jsonPath("$.deaths").isNotEmpty())
        .andExpect(jsonPath("$.todayDeaths").isNotEmpty())
        .andExpect(jsonPath("$.recovered").isNotEmpty())
        .andExpect(jsonPath("$.todayRecovered").isNotEmpty())
        .andExpect(jsonPath("$.active").isNotEmpty())
        .andExpect(jsonPath("$.critical").isNotEmpty())
        .andExpect(jsonPath("$.casesPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.deathsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.tests").isNotEmpty())
        .andExpect(jsonPath("$.testsPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.population").isNotEmpty())
        .andExpect(jsonPath("$.oneCasePerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneDeathPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.oneTestPerPeople").isNotEmpty())
        .andExpect(jsonPath("$.activePerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.recoveredPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.criticalPerOneMillion").isNotEmpty())
        .andExpect(jsonPath("$.country").isNotEmpty())
        .andExpect(jsonPath("$.continent").isNotEmpty())
        .andExpect(jsonPath("$.dayOfData").isNotEmpty());
    }
    @Test
    void testGetDataUsingCache() throws Exception{
        
        CovidData data = buildCovidDataObject(PORTUGAL, EUROPE, 0);

        Mockito.when(service.getDataByPlaceAndDayOfData(data.getCountry(), data.getDayOfData(), false)).thenReturn(data);

        mvc.perform(get("/api/get/country")
        .param("country", "Portugal")
        .param("dayOfData", "Today")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.updated").value(data.getUpdated()))
        .andExpect(jsonPath("$.cases").value(data.getCases()))
        .andExpect(jsonPath("$.todayCases").value(data.getTodayCases()))
        .andExpect(jsonPath("$.deaths").value(data.getDeaths()))
        .andExpect(jsonPath("$.todayDeaths").value(data.getTodayDeaths()))
        .andExpect(jsonPath("$.recovered").value(data.getRecovered()))
        .andExpect(jsonPath("$.todayRecovered").value(data.getTodayRecovered()))
        .andExpect(jsonPath("$.active").value(data.getActive()))
        .andExpect(jsonPath("$.critical").value(data.getCritical()))
        .andExpect(jsonPath("$.casesPerOneMillion").value(data.getCasesPerOneMillion()))
        .andExpect(jsonPath("$.deathsPerOneMillion").value(data.getDeathsPerOneMillion()))
        .andExpect(jsonPath("$.tests").value(data.getTests()))
        .andExpect(jsonPath("$.testsPerOneMillion").value(data.getTestsPerOneMillion()))
        .andExpect(jsonPath("$.population").value(data.getPopulation()))
        .andExpect(jsonPath("$.oneCasePerPeople").value(data.getOneCasePerPeople()))
        .andExpect(jsonPath("$.oneDeathPerPeople").value(data.getOneDeathPerPeople()))
        .andExpect(jsonPath("$.oneTestPerPeople").value(data.getOneTestPerPeople()))
        .andExpect(jsonPath("$.activePerOneMillion").value(data.getActivePerOneMillion()))
        .andExpect(jsonPath("$.recoveredPerOneMillion").value(data.getRecoveredPerOneMillion()))
        .andExpect(jsonPath("$.criticalPerOneMillion").value(data.getCriticalPerOneMillion()))
        .andExpect(jsonPath("$.country").value(data.getCountry()))
        .andExpect(jsonPath("$.continent").value(data.getContinent()))
        .andExpect(jsonPath("$.dayOfData").value(data.getDayOfData()));

    }

    @Test
    void testGetCountryBadDayofData() throws Exception{
        mvc.perform(get("/api/get/country")
        .param("country", "Portugal")
        .param("dayOfData", "asdasda")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCountryBadCountry() throws Exception{
        mvc.perform(get("/api/get/country")
        .param("country", "asdfgh")
        .param("dayOfData", "Today")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCountrytBadDayOfData() throws Exception{
        mvc.perform(get("/api/get/continent")
        .param("country", "Portugal")
        .param("dayOfData", "asdfgh")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCountryNoParameters() throws Exception{
        mvc.perform(get("/api/get/country")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCountryOnlyCountry() throws Exception{

        CovidData data = buildCovidDataObject(PORTUGAL, EUROPE, 0);

        Mockito.when(service.getDataByPlaceAndDayOfData(data.getCountry(), data.getDayOfData(), false)).thenReturn(data);

        mvc.perform(get("/api/get/country")
        .param("country", "Portugal")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dayOfData").value(0));
    }

    @Test
    void testGetCountryOnlyDayofData() throws Exception{
        mvc.perform(get("/api/get/country")
        .param("dayOfData", "Today")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetContinentBadContinent() throws Exception{
        mvc.perform(get("/api/get/continent")
        .param("continent", "asdfgh")
        .param("dayOfData", "Today")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetContinentBadDayOfData() throws Exception{
        mvc.perform(get("/api/get/continent")
        .param("continent", "Europe")
        .param("dayOfData", "asdfgh")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetContinentNoParameters() throws Exception{
        mvc.perform(get("/api/get/continent")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetContinentOnlyContinent() throws Exception{

        CovidData data = buildCovidDataObject(EUROPE, EUROPE, 0);

        Mockito.when(service.getDataByPlaceAndDayOfData(data.getCountry(), data.getDayOfData(), true)).thenReturn(data);

        mvc.perform(get("/api/get/continent")
        .param("continent", "Europe")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dayOfData").value(0));
    }

    @Test
    void testGetContinentOnlyDayofData() throws Exception{
        mvc.perform(get("/api/get/continent")
        .param("dayOfData", "Today")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetWorldBadDayOfData() throws Exception{
        mvc.perform(get("/api/get/world")
        .param("dayOfData", "asdfgh")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetWorldNoParameters() throws Exception{
        CovidData data = buildCovidDataObject(WORLD, WORLD, 0);

        Mockito.when(service.getDataByPlaceAndDayOfData(data.getCountry(), data.getDayOfData(), false)).thenReturn(data);

        mvc.perform(get("/api/get/world")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dayOfData").value(0));
    }

    @Test
    void testGetCacheStatistics() throws Exception{
        mvc.perform(get("/api/cacheStats")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
        .andExpect(jsonPath("hits").isNotEmpty())
        .andExpect(jsonPath("misses").isNotEmpty())
        .andExpect(jsonPath("getRequests").isNotEmpty())
        .andExpect(jsonPath("saveRequests").isNotEmpty())
        .andExpect(jsonPath("deleteRequests").isNotEmpty());
    }
    
    CovidData buildCovidDataObject(String country, String continent, int dayOfData){
        
        CovidData covidData = new CovidData();
        covidData.setUpdated(1234567890);
        covidData.setCases(1000);
        covidData.setTodayCases(50);
        covidData.setDeaths(30);
        covidData.setTodayDeaths(10);
        covidData.setRecovered(300);
        covidData.setTodayRecovered(30);
        covidData.setActive(500);
        covidData.setCritical(14);
        covidData.setCasesPerOneMillion(3);
        covidData.setDeathsPerOneMillion(1);
        covidData.setTests(4000);
        covidData.setTestsPerOneMillion(2);
        covidData.setPopulation(10000000);
        covidData.setOneCasePerPeople(2);
        covidData.setOneDeathPerPeople(5);
        covidData.setOneTestPerPeople(4);
        covidData.setActivePerOneMillion(3);
        covidData.setRecoveredPerOneMillion(1);
        covidData.setCriticalPerOneMillion(0);
        covidData.setCountry(country);
        covidData.setContinent(continent);
        covidData.setDayOfData(dayOfData);
        return covidData;
    }
}

