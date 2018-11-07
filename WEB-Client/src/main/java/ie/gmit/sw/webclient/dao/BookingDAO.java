package ie.gmit.sw.webclient.dao;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.Bookingmessage;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class BookingDAO extends DAO {
    public static String restBookingPath = "/booking/";

    public BookingDAO() {
        super();
    }

    @Override
    public Booking forId(String id) throws Exception {
        //Get the booking
        return (Booking) super.restService.restRequest(restBookingPath + "/" + id, new Booking(), HttpMethod.GET);
    }

    @Override
    public Object save(Object o) throws Exception {
        Bookingmessage bs = new Bookingmessage();
        bs.setBooking((Booking) o);
        return super.restService.restRequest(restBookingPath + "/create", bs, HttpMethod.PUT);
    }

    /**
     * Change a booking
     *
     * @param o
     * @return
     */
    public Object change(Booking o) throws Exception {
        Bookingmessage bs = new Bookingmessage();
        bs.setBooking(o);
        return super.restService.restRequest(restBookingPath + "/change", bs, HttpMethod.PUT);
    }

    /**
     * Delete a booking
     *
     * @param id
     * @return
     */
    public boolean delete(String id) throws Exception {
        return super.restService.restBooleanRequest(restBookingPath + "/delete/" + id, new Booking(), HttpMethod.DELETE);
    }

}
