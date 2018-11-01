package ie.gmit.sw.restserver;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.server.RMIClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("booking")
public class BookingService {
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Response getBooking(@PathParam("id") String id) {
        Booking c = RMIClient.getInstance().getBooking(id);
        if (c != null) {
            return Response.status(200).entity(c).build();
        }
        return Response.status(204).build();
    }

    @PUT
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("create")
    public Response addBooking(Booking c) {
        if (c.getId() == null || c.getCar() == null || c.getBookingTimeFrame() == null || c.getPerson() == null) {
            return Response.status(400).build();
        }
        System.out.println(c.getBookingTimeFrame().getBookingTimeFrom());
        if (RMIClient.getInstance().addBooking(c)) {
            return Response.status(200).entity(c).build();
        }
        return Response.status(409).build();
    }

    @PUT
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("change")
    public Response changeBooking(Booking c) {
        if (c.getId() == null || c.getCar() == null || c.getBookingTimeFrame() == null || c.getPerson() == null) {
            return Response.status(400).build();
        }
        if (RMIClient.getInstance().changeBooking(c)) {
            return Response.status(200).entity(c).build();
        }
        return Response.status(409).build();
    }

}
