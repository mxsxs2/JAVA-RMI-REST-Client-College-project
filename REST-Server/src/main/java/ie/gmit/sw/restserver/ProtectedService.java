package ie.gmit.sw.restserver;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.Car;
import ie.gmit.sw.server.RMIClient;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("admin")
public class ProtectedService {
    @PUT
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("add/car")
    public Response getCar(Car c) {
        if (c.getId() == null || c.getModel() == null || c.getMake() == null || c.getColor() == null) {
            return Response.status(400).build();
        }
        if (RMIClient.getInstance().addCar(c)) {
            return Response.status(200).entity(c).build();
        }
        return Response.status(409).build();
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("booking/list")
    public Response getBookings() {
        List<Booking> list = RMIClient.getInstance().getBookings();

        if (list.size() != 0) {
            return Response.status(200).entity(new GenericEntity<List<Booking>>(list) {
            }).build();
        }
        return Response.status(204).build();
    }
}
