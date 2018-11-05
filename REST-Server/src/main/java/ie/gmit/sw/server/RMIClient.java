package ie.gmit.sw.server;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.BookingTimeFrame;
import ie.gmit.sw.model.Car;

import java.rmi.Naming;
import java.util.ArrayList;

/**
 * RMI connector singleton
 */
public class RMIClient implements BookingService {
    //Encapsulated RMIClient instance
    private static final RMIClient rmic = new RMIClient();

    /**
     * Private constructor to block instantiation
     */
    private RMIClient() {
    }

    /**
     * Returns the RMIClient
     *
     * @return
     */
    public static RMIClient getInstance() {
        return rmic;
    }

    /**
     * Connects to the remote RMI service
     *
     * @return
     * @throws Exception
     */
    private BookingService connect() throws Exception {
        return (BookingService) Naming.lookup("rmi://127.0.0.1:1099/BookingRMIService");
    }

    /**
     * Invokes a method without parameters
     *
     * @param method
     * @return
     */
    private Object invokeNull(String method) {
        try {
            return BookingService.class.getDeclaredMethod(method).invoke(connect());
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Invokes a method with one parameter
     *
     * @param method
     * @param a
     * @return
     */
    private Object invokeOne(String method, Object a) {
        try {
            return BookingService.class.getDeclaredMethod(method, a.getClass()).invoke(connect(), a);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Invokes a method with two parameters
     *
     * @param method
     * @param a
     * @param b
     * @return
     */
    private Object invokeTwo(String method, Object a, Object b) {
        try {
            return BookingService.class.getDeclaredMethod(method, a.getClass(), b.getClass()).invoke(connect(), a, b);
        } catch (Exception e) {
        }
        return null;
    }


    @Override
    public Booking getBooking(String id) {
        return (Booking) invokeOne("getBooking", id);
    }

    @Override
    public boolean addBooking(Booking b) {
        return (Boolean) invokeOne("addBooking", b);
    }

    @Override
    public boolean changeBooking(Booking b) {
        return (Boolean) invokeOne("changeBooking", b);
    }

    @Override
    public ArrayList<Booking> getBookings() {
        ArrayList<Booking> a = (ArrayList<Booking>) invokeNull("getBookings");
        if (a == null)
            a = new ArrayList<>();

        return a;
    }

    @Override
    public boolean deleteBooking(String id) {
        return (Boolean) invokeOne("deleteBooking", id);
    }

    @Override
    public boolean addCar(Car c) {
        return (Boolean) invokeOne("addCar", c);
    }

    @Override
    public Car getCar(String id) {
        return (Car) invokeOne("getCar", id);
    }

    @Override
    public ArrayList<Car> getCars() {
        ArrayList<Car> a = (ArrayList<Car>) invokeNull("getCars");
        if (a == null)
            a = new ArrayList<>();

        return a;
    }

    @Override
    public boolean isCarAvailable(String carId, BookingTimeFrame timeFrame) {
        return (Boolean) invokeTwo("isCarAvailable", carId, timeFrame);
    }
}
