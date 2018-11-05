package ie.gmit.sw.restserver;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.server.RMIClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;


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
        System.out.println(c);
        if (c.getCar() == null || c.getBookingTimeFrame() == null || c.getPerson() == null) {
            return Response.status(400).build();
        }
        c.setId(UUID.randomUUID().toString());
        c.getPerson().setId(UUID.randomUUID().toString());
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
        if (c.getCar() == null || c.getBookingTimeFrame() == null || c.getPerson() == null) {
            return Response.status(400).build();
        }
        if (RMIClient.getInstance().changeBooking(c)) {
            return Response.status(200).entity(c).build();
        }
        return Response.status(409).build();
    }

    @DELETE
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("delete/{id}")
    public Response deleteBooking(@PathParam("id") String id) {
        if (RMIClient.getInstance().deleteBooking(id)) {
            return Response.status(200).build();
        }
        return Response.status(204).build();
    }

}
