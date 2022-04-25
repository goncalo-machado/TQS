package pt.ua.tqs.covid_tracker;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import pt.ua.tqs.covid_tracker.HTTP.HTTPAPI;

public class HTTPAPITest {

    HTTPAPI httpapi = new HTTPAPI();
    
    @Test
    void whenValidRequest_ExistsResponse(){
        assertFalse(httpapi.httpGet("https://www.google.com/").isEmpty());
    }

    @Test
    void whenInvalidRequest_ThrowNullPointerException(){
        assertThrows(NullPointerException.class, () -> {httpapi.httpGet("Not a real URL");});
    }
}
