package ie.gmit.sw.restserver;

import ie.gmit.sw.model.Car;
import ie.gmit.sw.server.RMIClient;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


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
}
