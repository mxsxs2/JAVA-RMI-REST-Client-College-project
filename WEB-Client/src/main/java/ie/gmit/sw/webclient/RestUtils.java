package ie.gmit.sw.webclient;

import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RestUtils {
    private Environment env;

    public static String restURI;

    public static String restBookingPath = "/booking/";
    public static String restCarPath = "/car/";

    @Autowired
    public RestUtils(@Value("${spring.data.rest.base-path}") final String v) {
        //Set the rest url from application properties
        restURI = v;
    }


    /**
     * Get object from rest server
     *
     * @param path
     * @param obj
     * @return
     */
    public Object restRequest(String path, Object obj, HttpMethod method) throws HttpClientErrorException {
        //Get new template
        RestTemplate restTemplate = new RestTemplate();
        //Get new headers
        HttpHeaders headers = new HttpHeaders();
        //Set accept type
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        //Set content type
        headers.setContentType(MediaType.APPLICATION_XML);
        //Create new request with the new headers
        HttpEntity<Object> request = new HttpEntity<>(obj, headers);
        //Get the response from the server
        ResponseEntity<Object> responseEntity = restTemplate.exchange(restURI + path, method, request, (Class<Object>) obj.getClass());
        //Arrays.asList(responseEntity.getBody().getCars()).forEach(System.out::println);
        //Return response
        return responseEntity.getBody();
    }

    public Object restPostRequest(String path, Object obj) {
        //Get new template
        RestTemplate restTemplate = new RestTemplate();
        //Get new headers
        HttpHeaders headers = new HttpHeaders();
        //Set accept type
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        //Set content type
        headers.setContentType(MediaType.APPLICATION_XML);
        //Create new request with the new headers
        HttpEntity<Object> request = new HttpEntity<>(obj, headers);
        //Get the response from the server
        ResponseEntity<Object> responseEntity = restTemplate.exchange(restURI + path, HttpMethod.POST, request, (Class<Object>) obj.getClass());
        //Arrays.asList(responseEntity.getBody().getCars()).forEach(System.out::println);
        //Return response
        return responseEntity.getBody();
    }
}
