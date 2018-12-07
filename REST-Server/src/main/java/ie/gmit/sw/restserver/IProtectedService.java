package ie.gmit.sw.restserver;

import ie.gmit.sw.model.Car;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface IProtectedService {
    @PUT
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("add/car")
    Response addCar(Car c);

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("booking/list")
    Response getBookings();
}
