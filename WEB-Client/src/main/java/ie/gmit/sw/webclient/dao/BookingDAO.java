package ie.gmit.sw.webclient.dao;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.Bookingmessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.net.ConnectException;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BookingDAO extends DAO {
    @Value("${spring.data.rest.base-path}")
    private String restURI;
    public static String restBookingPath = "/booking/";

    public BookingDAO() {
        super();
    }

    @Override
    public Booking forId(String id) throws HttpClientErrorException, ConnectException {
        //Get the booking
        return (Booking) super.restUtils.restRequest(restBookingPath + "/" + id, new Booking(), HttpMethod.GET);
    }

    @Override
    public Object save(Object o) throws HttpClientErrorException, ConnectException {
        Bookingmessage bs = new Bookingmessage();
        bs.setBooking((Booking) o);
        return super.restUtils.restRequest(restBookingPath + "/create", bs, HttpMethod.PUT);
    }

    /**
     * Change a booking
     *
     * @param o
     * @return
     */
    public Object change(Booking o) throws HttpClientErrorException, ConnectException {
        Bookingmessage bs = new Bookingmessage();
        bs.setBooking(o);
        return super.restUtils.restRequest(restBookingPath + "/change", bs, HttpMethod.PUT);
    }

    /**
     * Delete a booking
     *
     * @param id
     * @return
     */
    public boolean delete(String id) throws HttpClientErrorException, ConnectException {
        return super.restUtils.restBooleanRequest(restBookingPath + "/delete/" + id, new Booking(), HttpMethod.DELETE);
    }

}
