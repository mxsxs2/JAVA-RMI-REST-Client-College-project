package ie.gmit.sw.webclient.dao;

import ie.gmit.sw.model.Booking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BookingDAO extends DAO<Booking> {
    @Value("${spring.data.rest.base-path}")
    private String restURI;
    public static String restBookingPath = "/booking/";

    public BookingDAO() {
        super();
    }

    @Override
    public Booking forId(String id) {
        //Get the booking
        return (Booking) super.restUtils.restRequest(restBookingPath + "/" + id, new Booking(), HttpMethod.GET);
    }

    @Override
    public Booking save(Booking o) {
        try {
            Object obj = super.restUtils.restRequest(restBookingPath + "/create", o, HttpMethod.PUT);
            return (Booking) obj;
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Change a booking
     *
     * @param o
     * @return
     */
    public Booking change(Booking o) {
        try {
            Object obj = super.restUtils.restRequest(restBookingPath + "/change", o, HttpMethod.PUT);
            return (Booking) obj;
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Delete a booking
     *
     * @param id
     * @return
     */
    public boolean delete(String id) {
        try {
            return super.restUtils.restBooleanRequest(restBookingPath + "/delete/" + id, new Booking(), HttpMethod.DELETE);
        } catch (HttpClientErrorException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
