package pt.ua.tqs.covidtracker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.ua.tqs.covidtracker.cache.Cache;
import pt.ua.tqs.covidtracker.models.CovidData;
import pt.ua.tqs.covidtracker.resolver.CovidDataResolver;
import pt.ua.tqs.covidtracker.services.CovidDataService;

@ExtendWith(MockitoExtension.class)
class ServiceTest {
    
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
        Mockito.when(resolver.getData(data2.getCountry(), data2.getDayOfData(), false)).thenReturn(data2);
    }

    @Test
    void whenDataInCache_ReturnsDataFromCache(){
        String country = "Portugal";
        int dayOfData = 0;
        CovidData data = service.getDataByPlaceAndDayOfData(country, dayOfData, false);
        assertEquals(country, data.getCountry());
        assertEquals(dayOfData, data.getDayOfData());
    }

    @Test
    void whenDataNotInCache_ButDataInResolver_ReturnDataFromResolver(){
        String country = "Portugal";
        int dayOfData = 1;
        CovidData data = service.getDataByPlaceAndDayOfData(country, dayOfData, false);
        assertEquals(country, data.getCountry());
        assertEquals(dayOfData,data.getDayOfData());
    }

    @Test
    void whenDataNotInCacheAndNotInResolverThrowJsonException(){
        String country = "Portugal";
        int dayOfData = 2;
        assertEquals(null, service.getDataByPlaceAndDayOfData(country, dayOfData, false));
    }

}
