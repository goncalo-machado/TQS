package pt.ua.tqs.covidtracker;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import pt.ua.tqs.covidtracker.exceptions.BadUrlException;
import pt.ua.tqs.covidtracker.http.HTTPAPI;

class HTTPAPITest {

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
