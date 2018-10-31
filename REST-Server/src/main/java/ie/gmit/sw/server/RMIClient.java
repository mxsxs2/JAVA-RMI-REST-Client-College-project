package ie.gmit.sw.server;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.BookingTimeFrame;
import ie.gmit.sw.model.Car;

import java.rmi.Naming;
import java.util.ArrayList;

/**
 * Client for the RMI service
 */
public class RMIClient implements BookingService {
    private static final RMIClient rmic = new RMIClient();

    private RMIClient() {
    }

    public static RMIClient getInstance() {
        return rmic;
    }

    private BookingService connect() throws Exception {
        return (BookingService) Naming.lookup("rmi://127.0.0.1:1099/BookingRMIService");/*
		Booking b = s.getBooking("asd3");
		out.println(b);
		out.println(b.getId());
		out.println(b.getPerson().getId());
		out.println(b.getCar().getId());


		Address a= new Address();
		a.setCity("Galway");
		a.setCounty("Galway");
		a.setStreet("my street");

		Person p = new Person();
		p.setId("pasd");
		p.setFirstname("firstname");
		p.setLastname("lastname");
		p.setAddress(a);



		Car c= new Car();
		c.setId("181-D-7589");
		c.setColor("Black");
		c.setMake("A8");
		c.setModel("Audi");

		BookingTimeFrame bt= new BookingTimeFrame();
		bt.setBookingTimeFrom(new Date().getTime());
		bt.setBookingTimeTo(new Date().getTime());


		Booking nb =new Booking();
		nb.setId("asd4");
		nb.setPerson(p);
		nb.setCar(c);
		nb.setBookingTimeFrame(bt);
		nb.setReservationTime(new Date().getTime());
		out.println(s.addBooking(nb));


		BookingTimeFrame bt2= new BookingTimeFrame();
		bt2.setBookingTimeFrom(new SimpleDateFormat("yyy-MM-dd").parse("2018-10-25").getTime());
		bt2.setBookingTimeTo(new SimpleDateFormat("yyy-MM-dd").parse("2018-10-30").getTime());

		Booking nb2 =new Booking();
		nb2.setId("asd4");
		nb2.setPerson(p);
		nb2.setCar(c);
		nb2.setBookingTimeFrame(bt2);
		nb2.setReservationTime(new Date().getTime());
		out.println(s.changeBooking(nb2));


		s.getCars().forEach(System.out::println);
*/
    }

    @Override
    public Booking getBooking(String id) {
        try {
            return connect().getBooking(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addBooking(Booking b) {
        try {
            return connect().addBooking(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean changeBooking(Booking b) {
        try {
            return connect().changeBooking(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addCar(Car c) {
        try {
            return connect().addCar(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Car getCar(String id) {
        try {
            return connect().getCar(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Car> getCars() {
        try {
            return connect().getCars();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isCarAvailable(String carId, BookingTimeFrame timeFrame) {
        try {
            return connect().isCarAvailable(carId, timeFrame);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
