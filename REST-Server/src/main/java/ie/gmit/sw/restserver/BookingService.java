package ie.gmit.sw.restserver;

import ie.gmit.sw.model.Booking;
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
    public Response addBooking(Booking c) {
        if (c.getCar() == null || c.getBookingTimeFrame() == null || c.getPerson() == null) {
            return Response.status(400).build();
        }
        c.setId(UUID.randomUUID().toString());
        c.getPerson().setId(UUID.randomUUID().toString());
        if (RMIClient.getInstance(servletContext).addBooking(c)) {
            return Response.status(200).entity(c).build();
        }
        return Response.status(409).build();
    }

    @Override
    public Response changeBooking(Booking c) {
        if (c.getCar() == null || c.getBookingTimeFrame() == null || c.getPerson() == null) {
            return Response.status(400).build();
        }
        if (RMIClient.getInstance(servletContext).changeBooking(c)) {
            return Response.status(200).entity(c).build();
        }
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
