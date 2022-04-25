package pt.ua.tqs.covid_tracker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.tqs.covid_tracker.Cache.Cache;
import pt.ua.tqs.covid_tracker.Models.CovidData;
import pt.ua.tqs.covid_tracker.Resolver.CovidDataResolver;
import pt.ua.tqs.covid_tracker.Services.CovidDataService;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    
    @Mock(lenient = true)
    private CovidDataResolver resolver;

    @Mock(lenient = true)
    private Cache cache;

    @InjectMocks
    private CovidDataService service;

    @BeforeEach
    void setUp(){
        CovidData data1 = new CovidData();
        data1.setCountry("Portugal");
        data1.setDayOfData(0);

        CovidData data2 = new CovidData();
        data2.setCountry("Portugal");
        data2.setDayOfData(1);


        Mockito.when(cache.getData(data1.getCountry(), data1.getDayOfData())).thenReturn(data1);
        Mockito.when(resolver.getCountryData(data2.getCountry(), data2.getDayOfData())).thenReturn(data2);
    }

    @Test
    void whenDataInCache_ReturnsDataFromCache(){
        String country = "Portugal";
        int dayOfData = 0;
        CovidData data = service.getDataByCountryAndDayOfData(country, dayOfData);
        assertEquals(data.getCountry(), country);
        assertEquals(data.getDayOfData(), dayOfData);
    }

    @Test
    void whenDataNotInCache_ButDataInResolver_ReturnDataFromResolver(){
        String country = "Portugal";
        int dayOfData = 1;
        CovidData data = service.getDataByCountryAndDayOfData(country, dayOfData);
        assertEquals(data.getCountry(), country);
        assertEquals(data.getDayOfData(), dayOfData);
    }

    @Test
    void whenDataNotInCacheAndNotInResolverThrowJsonException(){
        String country = "Portugal";
        int dayOfData = 2;
        assertEquals(service.getDataByCountryAndDayOfData(country, dayOfData), null);
    }

}
