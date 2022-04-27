package pt.ua.tqs.covidtracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pt.ua.tqs.covidtracker.models.CovidData;
import pt.ua.tqs.covidtracker.repository.CovidDataRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CovidTrackerApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ApiControllerTestIT {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    CovidDataRepository covidRepo;

    @AfterEach
    public void resetDb() {
        covidRepo.deleteAll();
    }

    @Test
    void testGetDataUsingExternalAPI() throws Exception{
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
    void testGetDataUsingCache() throws Exception{
        CovidData data = buildCovidDataObject();
        
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
    void testGetDataBadDayofData() throws Exception{
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
        mvc.perform(get("/api/get/country")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCountryOnlyDayofData() throws Exception{
        mvc.perform(get("/api/get/country")
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
        mvc.perform(get("/api/get/continent")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetDataOnlyDayofData() throws Exception{
        mvc.perform(get("/api/get/continent")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCacheStatistics() throws Exception{
        mvc.perform(get("/api/cacheStats")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        /*.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("hits").isNotEmpty())
        .andExpect(jsonPath("misses").isNotEmpty())
        .andExpect(jsonPath("getRequests").isNotEmpty())
        .andExpect(jsonPath("saveRequests").isNotEmpty())
        .andExpect(jsonPath("deleteRequests").isNotEmpty())*/;
    }
    CovidData buildCovidDataObject(){
        
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
        covidData.setCountry("Portugal");
        covidData.setContinent("Europe");
        covidData.setDayOfData(0);

        covidRepo.saveAndFlush(covidData);
        return covidData;
    }
}
