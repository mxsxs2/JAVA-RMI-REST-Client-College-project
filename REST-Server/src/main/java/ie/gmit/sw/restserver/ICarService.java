package ie.gmit.sw.restserver;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface ICarService {
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    Response getCar(@PathParam("id") String id);

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}/{from}/{to}/{bookingid:(/bookingid/[^/]+?)?}")
    Response isCarAvailable(@PathParam("id") String id, @PathParam("from") long from, @PathParam("to") long to, @PathParam("bookingid") String bid);

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("list")
    Response getCars();
}
