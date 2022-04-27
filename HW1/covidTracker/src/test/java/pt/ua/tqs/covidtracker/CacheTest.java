package pt.ua.tqs.covidtracker;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import pt.ua.tqs.covidtracker.cache.Cache;
import pt.ua.tqs.covidtracker.models.CovidData;
import pt.ua.tqs.covidtracker.repository.CovidDataRepository;

@ExtendWith(MockitoExtension.class)
class CacheTest {
    
    @Mock
    CovidDataRepository covidRepo;

    @InjectMocks
    Cache cache;

    @AfterEach
    void setUp(){
        Cache.resetCache();
    }

    @Test
    void testDeleteSingleData(){
        CovidData data1 = new CovidData();

        cache.deleteSingleData(data1);

        assertEquals(0, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(0, Cache.getGetRequests());
        assertEquals(0, Cache.getSaveRequests());
        assertEquals(1, Cache.getDeleteRequests());

        verify(covidRepo, times(1)).delete(data1);
    }

    @Test
    void checkGettersAndSetters(){
        int ttlFirstValue = Cache.getTtl();
        int hitsFirstValue= Cache.getHits();
        int missesFirstValue= Cache.getMisses();
        int getRequestsFirstValue= Cache.getGetRequests();
        int saveRequestsFirstValue= Cache.getSaveRequests();
        int deleteRequestsFirstValue= Cache.getDeleteRequests();

        int ttl = 200;
        int hits = 10, misses = 20, getRequests = 30, saveRequests = 40, deleteRequests = 50;

        Cache.setTtl(ttl);
        Cache.setHits(hits);
        Cache.setMisses(misses);
        Cache.setGetRequests(getRequests);
        Cache.setSaveRequests(saveRequests);
        Cache.setDeleteRequests(deleteRequests);

        assertEquals(ttl, Cache.getTtl());
        assertEquals(hits, Cache.getHits());
        assertEquals(misses, Cache.getMisses());
        assertEquals(getRequests, Cache.getGetRequests());
        assertEquals(saveRequests, Cache.getSaveRequests());
        assertEquals(deleteRequests, Cache.getDeleteRequests());

        Cache.setTtl(ttlFirstValue);
        Cache.setHits(hitsFirstValue);
        Cache.setMisses(missesFirstValue);
        Cache.setGetRequests(getRequestsFirstValue);
        Cache.setSaveRequests(saveRequestsFirstValue);
        Cache.setDeleteRequests(deleteRequestsFirstValue);
    }

    @Test
    void checkIncreaseFunctions(){
        assertEquals(0, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(0, Cache.getGetRequests());
        assertEquals(0, Cache.getSaveRequests());
        assertEquals(0, Cache.getDeleteRequests());

        int maxLoops = 5;

        for(int i = 0; i < maxLoops;i++){
            Cache.increaseHits();
            if (i<maxLoops-1){
                Cache.increaseMisses();
                if (i<maxLoops-2){
                    Cache.increaseGetRequests();
                    if (i<maxLoops-3){
                        Cache.increaseSaveRequests();
                        if (i<maxLoops-4){
                            Cache.increaseDeleteRequests();
                        }
                    }
                }
            }
        }

        assertEquals(maxLoops, Cache.getHits());
        assertEquals(maxLoops-1, Cache.getMisses());
        assertEquals(maxLoops-2, Cache.getGetRequests());
        assertEquals(maxLoops-3, Cache.getSaveRequests());
        assertEquals(maxLoops-4, Cache.getDeleteRequests());
    }

    @Test
    void testIsExpiredFunction(){
        CovidData validData = new CovidData();
        validData.setCreated(System.currentTimeMillis());
        CovidData invalidData = new CovidData();
        invalidData.setCreated(System.currentTimeMillis() - 300 * 1000); //5 minutos
        assertTrue(cache.isExpired(invalidData));
        assertFalse(cache.isExpired(validData));
    }

    @Test
    void testGetData(){
        CovidData validData = new CovidData();
        CovidData expiredData = new CovidData();
        CovidData notFoundData = null;

        validData.setCountry("Portugal");
        validData.setDayOfData(0);

        expiredData.setCountry("Spain");
        expiredData.setDayOfData(1);
        expiredData.setCreated(System.currentTimeMillis() - 300 * 1000);

        Mockito.when(covidRepo.findByCountryAndDayOfData("Portugal", 0)).thenReturn(validData);
        Mockito.when(covidRepo.findByCountryAndDayOfData("Spain", 1)).thenReturn(expiredData);
        Mockito.when(covidRepo.findByCountryAndDayOfData("Portugal", 2)).thenReturn(notFoundData);

        assertEquals(validData, cache.getData("Portugal", 0));
        assertEquals(null, cache.getData("Spain",1));
        assertEquals(null, cache.getData("Portugal", 2));

        assertEquals(1, Cache.getHits());
        assertEquals(2, Cache.getMisses());
        assertEquals(3, Cache.getGetRequests());
        assertEquals(0, Cache.getSaveRequests());
        assertEquals(1, Cache.getDeleteRequests());
    }

    @Test
    void testDeleteMultipleData(){
        CovidData data1 = new CovidData();
        CovidData data2 = new CovidData();
        List<CovidData> list = new ArrayList<>();
        list.add(data1);
        list.add(data2);

        cache.deleteMultipleData(list);

        assertEquals(0, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(0, Cache.getGetRequests());
        assertEquals(0, Cache.getSaveRequests());
        assertEquals(1, Cache.getDeleteRequests());

        verify(covidRepo, times(1)).deleteAll(list);
    }

    @Test
    void testSaveData(){
        CovidData data1 = new CovidData();
        data1.setActive(30);
        CovidData data2 = new CovidData();
        data2.setActive(20);
        CovidData data3 = new CovidData();
        data3.setActive(40);
        List<CovidData> list = new ArrayList<>();
        list.add(data1);
        list.add(data3);

        Mockito.when(covidRepo.findAll()).thenReturn(list);

        cache.saveData(data1);
        cache.saveData(data2);

        assertEquals(1, Cache.getHits());
        assertEquals(1, Cache.getMisses());
        assertEquals(0, Cache.getGetRequests());
        assertEquals(2, Cache.getSaveRequests());
        assertEquals(0, Cache.getDeleteRequests());

        verify(covidRepo, times(2)).findAll();
        verify(covidRepo, times(1)).save(data2);
    }
}
