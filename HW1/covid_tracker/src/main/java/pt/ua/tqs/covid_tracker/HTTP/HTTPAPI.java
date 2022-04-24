package pt.ua.tqs.covid_tracker.HTTP;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class HTTPAPI {
    private static final Logger log = LoggerFactory.getLogger(HTTPAPI.class);

    public HTTPAPI(){
        
    }

    public String httpGet(String url){

        HttpRequest request;
        HttpResponse<String> response = null;

        log.info("----Start -> API call to {}----", url);

        try{
            request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .method("GET",HttpRequest.BodyPublishers.noBody())
                            .build();
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Success of API Call to {}", url);
        } catch (Exception e){
            System.err.println(e);
            log.error("Error -> API Call to " + url, e);
        }

        log.info("----End -> API call to {}----", url);
        return response.body();
    }
}