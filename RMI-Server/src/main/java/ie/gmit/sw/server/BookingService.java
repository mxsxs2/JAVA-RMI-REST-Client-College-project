package ie.gmit.sw.server;

/* BookingService represents the Remote Interface. An interface is an abstraction
 * that defines what an implementing class must do, but not how it will do it.
 * Interfaces are declarative - they may contain constants and method signatures,
 * but have no implementation code. Interfaces are also a type. Any class that
 * implements an interface may be cast to the interface type.
 *
 * Our remote interface exposes the public service methods that may be invoked by
 * a remote object. All remote methods must throw a RemoteException. In RMI, a class
 * that implements a remote interface is called a Remote Object.
 */

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.BookingTimeFrame;
import ie.gmit.sw.model.Car;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

interface BookingService extends Remote {
    /**
     * Return a booking bi its id
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public Booking getBooking(String id) throws RemoteException;

    /**
     * Adds a booking to the database
     *
     * @param b
     * @return
     * @throws RemoteException
     */
    public boolean addBooking(Booking b) throws RemoteException;

    /**
     * Changes a booking in the database
     *
     * @param b
     * @return
     * @throws RemoteException
     */
    public boolean changeBooking(Booking b) throws RemoteException;

    /**
     * Returns a list of bookings
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<Booking> getBookings() throws RemoteException;

    /**
     * Deletes a booking in the database
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public boolean deleteBooking(String id) throws RemoteException;

    /**
     * Adds a car to the database
     *
     * @param c
     * @return
     * @throws RemoteException
     */
    public boolean addCar(Car c) throws RemoteException;

    /**
     * Returns a car by its id
     *
     * @param id
     * @return
     * @throws RemoteException
     */
    public Car getCar(String id) throws RemoteException;

    /**
     * Returns a list of cars
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<Car> getCars() throws RemoteException;

    /**
     * Checks whether the car is available for a given period or not.
     * If the bookingId empty, it will search every record
     * If the bookingId is empty, it exclude that it from search (a.k.a update)
     *
     * @param carId
     * @param timeFrame
     * @param bookingId
     * @return
     * @throws RemoteException
     */
    public boolean isCarAvailable(String carId, BookingTimeFrame timeFrame, String bookingId) throws RemoteException;
}
