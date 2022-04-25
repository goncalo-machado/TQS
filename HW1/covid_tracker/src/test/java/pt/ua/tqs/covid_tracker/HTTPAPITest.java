package pt.ua.tqs.covid_tracker;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import pt.ua.tqs.covid_tracker.Exceptions.BadUrlException;
import pt.ua.tqs.covid_tracker.HTTP.HTTPAPI;

public class HTTPAPITest {

    HTTPAPI httpapi = new HTTPAPI();
    
    @Test
    void whenValidRequest_ExistsResponse() throws BadUrlException{
        assertFalse(httpapi.httpGet("https://www.google.com/").isEmpty());
    }

    @Test
    void whenInvalidRequest_ThrowBadURLException(){
        assertThrows(BadUrlException.class, () -> {httpapi.httpGet("NotarealURL");});
    }
}
