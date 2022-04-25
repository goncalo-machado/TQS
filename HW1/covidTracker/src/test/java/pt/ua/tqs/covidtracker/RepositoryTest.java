package pt.ua.tqs.covidtracker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import pt.ua.tqs.covidtracker.models.CovidData;
import pt.ua.tqs.covidtracker.repository.CovidDataRepository;

@DataJpaTest
class RepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CovidDataRepository covidDataRepository;

    CovidData data;

    @BeforeEach
    void setUp(){
        data = new CovidData();
        data.setCountry("Portugal");
        data.setDayOfData(0);
        data.setDeaths(30);
        data.setCases(500);
        data.setCreated(3000);

        entityManager.persistAndFlush(data);
    }

    @AfterEach
    void cleanUp(){
        entityManager.clear();
    }

    @Test
    void whenFindByPortugalAndToday_ReturnValidCovidData(){
        CovidData result = covidDataRepository.findByCountryAndDayOfData("Portugal", 0);
        assertEquals(data, result);
    }

    @Test
    void whenFindByInvalidAndToday_ReturnNull(){
        CovidData result = covidDataRepository.findByCountryAndDayOfData("Not Exists", 0);
        assertEquals(null, result);
    }

    @Test
    void whenFindByPortugalAndInvalid_ReturnNull(){
        CovidData result = covidDataRepository.findByCountryAndDayOfData("Portugal", 4);
        assertEquals(null, result);
    }

    @Test
    void whenAllByCreatedLessThanEqual_ReturnValid(){  
        CovidData data2 = new CovidData();
        data2.setCountry("Spain");
        data2.setDayOfData(0);
        data2.setDeaths(30);
        data2.setCases(500);
        data2.setCreated(2000);

        entityManager.persistAndFlush(data2);

        List<CovidData> result = covidDataRepository.findAllByCreatedLessThanEqual(2500);
        assertEquals(1, result.size());
        assertEquals(data2, result.get(0));
    }

    @Test
    void whenAllByCreatedLessThanEqual_ButNoValidValues_ReturnEmptyList(){
        CovidData data2 = new CovidData();
        data2.setCountry("Spain");
        data2.setDayOfData(0);
        data2.setDeaths(30);
        data2.setCases(500);
        data2.setCreated(3500);

        entityManager.persistAndFlush(data2);

        List<CovidData> result = covidDataRepository.findAllByCreatedLessThanEqual(2500);
        assertEquals(0, result.size());
    }
    
}
