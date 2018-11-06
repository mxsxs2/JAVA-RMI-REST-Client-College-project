package ie.gmit.sw.restserver;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.Bookingmessage;
import ie.gmit.sw.server.RMIClient;

import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.UUID;


@Path("booking")
public class BookingService implements IBookingService {
    @Context
    private ServletContext servletContext;

    @Override
    public Response getBooking(String id) {
        Booking c = RMIClient.getInstance(servletContext).getBooking(id);
        if (c != null) {
            return Response.status(200).entity(c).build();
        }
        return Response.status(204).build();
    }

    @Override
    public Response addBooking(Bookingmessage bs) {
        if (bs.getBooking() == null ||
                bs.getBooking().getCar() == null ||
                bs.getBooking().getBookingTimeFrame() == null ||
                bs.getBooking().getPerson() == null) {
            return Response.status(400).build();
        }
        Booking c = bs.getBooking();
        //Check if the car is available
        if (RMIClient.getInstance(servletContext).isCarAvailable(
                c.getCar().getId(),
                c.getBookingTimeFrame(),
                c.getId())
        ) {
            c.setId(UUID.randomUUID().toString());
            c.getPerson().setId(UUID.randomUUID().toString());
            if (RMIClient.getInstance(servletContext).addBooking(c)) {
                bs.setBooking(c);
                bs.setMessage("ok");
                return Response.status(200).entity(bs).build();
            }
        } else {
            bs.setBooking(c);
            bs.setMessage("carnotavailable");
            return Response.status(200).entity(bs).build();
        }


        return Response.status(409).build();
    }

    @Override
    public Response changeBooking(Bookingmessage bs) {
        if (bs.getBooking() == null ||
                bs.getBooking().getCar() == null ||
                bs.getBooking().getBookingTimeFrame() == null ||
                bs.getBooking().getPerson() == null) {
            return Response.status(400).build();
        }
        Booking c = bs.getBooking();
        //Check if the car is available
        if (RMIClient.getInstance(servletContext).isCarAvailable(
                c.getCar().getId(),
                c.getBookingTimeFrame(),
                c.getId())
        ) {
            if (RMIClient.getInstance(servletContext).changeBooking(c)) {
                bs.setBooking(c);
                bs.setMessage("ok");
                return Response.status(200).entity(bs).build();
            }
        } else {
            bs.setBooking(c);
            bs.setMessage("carnotavailable");
            return Response.status(200).entity(bs).build();
        }
        System.out.println("409");
        return Response.status(409).build();
    }

    @Override
    public Response deleteBooking(String id) {
        if (RMIClient.getInstance(servletContext).deleteBooking(id)) {
            return Response.status(200).build();
        }
        return Response.status(204).build();
    }

}
