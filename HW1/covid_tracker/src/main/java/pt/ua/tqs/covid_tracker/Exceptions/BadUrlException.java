package pt.ua.tqs.covid_tracker.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadUrlException extends Exception {

    private static final long serialVersionUID = 1L;

    public BadUrlException(String message){
        super(message);
    }
}