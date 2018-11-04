package ie.gmit.sw.restserver;

import ie.gmit.sw.model.BookingTimeFrame;
import ie.gmit.sw.model.Car;
import ie.gmit.sw.model.Cars;
import ie.gmit.sw.server.RMIClient;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;


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

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{id}/{from}/{to}")
    public Response isCarAvailable(@PathParam("id") String id, @PathParam("from") long from, @PathParam("to") long to) {
        BookingTimeFrame bt = new BookingTimeFrame();
        bt.setBookingTimeFrom(from);
        bt.setBookingTimeTo(to);

        if (RMIClient.getInstance().isCarAvailable(id, bt)) {
            return Response.status(200).build();
        }
        return Response.status(204).build();
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("list")
    public Response getCars() {
        List<Car> list = RMIClient.getInstance().getCars();
        Cars cars = new Cars();
        cars.setCars(list);
        if (list.size() != 0) {
            return Response.status(200).entity(cars).build();
        }
        return Response.status(204).build();
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    //@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("list2")
    public Response getCarsr(@Context HttpHeaders headers) {

        System.out.println(headers.getMediaType());
        List<Car> list = RMIClient.getInstance().getCars();

        if (list.size() != 0) {
            return Response.status(200).entity(new GenericEntity<List<Car>>(list) {
            }).build();
        }
        return Response.status(204).build();
    }

}
