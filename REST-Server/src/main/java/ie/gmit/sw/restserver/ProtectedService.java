package ie.gmit.sw.restserver;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.Car;
import ie.gmit.sw.server.RMIClient;

import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("admin")
public class ProtectedService implements IProtectedService {
    @Context
    private ServletContext servletContext;

    @Override
    public Response getCar(Car c) {
        if (c.getId() == null || c.getModel() == null || c.getMake() == null || c.getColor() == null) {
            return Response.status(400).build();
        }
        if (RMIClient.getInstance(servletContext).addCar(c)) {
            return Response.status(200).entity(c).build();
        }
        return Response.status(204).build();
    }

    @Override
    public Response getBookings() {
        List<Booking> list = RMIClient.getInstance(servletContext).getBookings();

        if (list.size() != 0) {
            return Response.status(200).entity(new GenericEntity<List<Booking>>(list) {
            }).build();
        }
        return Response.status(204).build();
    }
}
