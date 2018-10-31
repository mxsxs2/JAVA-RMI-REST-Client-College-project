package ie.gmit.sw.restserver;

import ie.gmit.sw.model.Car;
import ie.gmit.sw.server.RMIClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("car")
public class CarService {
    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}")
    public Response getCar(@PathParam("id") String id) {
        Car c = RMIClient.getInstance().getCar(id);
        if (c != null) {
            return Response.status(200).entity(c).build();
        }
        return Response.status(204).build();
    }
}
