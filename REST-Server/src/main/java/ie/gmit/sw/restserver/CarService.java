package ie.gmit.sw.restserver;

import ie.gmit.sw.model.BookingTimeFrame;
import ie.gmit.sw.model.Car;
import ie.gmit.sw.model.Cars;
import ie.gmit.sw.server.RMIClient;

import javax.servlet.ServletContext;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("car")
public class CarService implements ICarService {
    @Context
    private ServletContext servletContext;

    @Override
    public Response getCar(String id) {
        Car c = RMIClient.getInstance(servletContext).getCar(id);
        if (c != null) {
            return Response.status(200).entity(c).build();
        }
        return Response.status(204).build();
    }

    @Override
    public Response isCarAvailable(String id, long from, long to, String bid) {
        BookingTimeFrame bt = new BookingTimeFrame();
        bt.setBookingTimeFrom(from);
        bt.setBookingTimeTo(to);

        if (RMIClient.getInstance(servletContext).isCarAvailable(id, bt, bid)) {
            return Response.status(200).build();
        }
        return Response.status(204).build();
    }

    @Override
    public Response getCars() {
        List<Car> list = RMIClient.getInstance(servletContext).getCars();
        Cars cars = new Cars();
        cars.setCars(list);
        if (list.size() != 0) {
            return Response.status(200).entity(cars).build();
        }
        return Response.status(204).build();
    }
}
