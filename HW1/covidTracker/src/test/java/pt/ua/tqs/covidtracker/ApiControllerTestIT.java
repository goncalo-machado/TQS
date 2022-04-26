package pt.ua.tqs.covidtracker;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import pt.ua.tqs.covidtracker.services.CovidDataService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CovidTrackerApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ApiControllerTestIT {
    
    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private CovidDataService service;


}
