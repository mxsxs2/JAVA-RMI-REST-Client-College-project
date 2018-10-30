package ie.gmit.sw.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.BookingTimeFrame;
import ie.gmit.sw.model.Car;

interface BookingService extends Remote {
    public Booking getBooking(String id) throws RemoteException;

    public boolean addBooking(Booking b) throws RemoteException;

    public boolean changeBooking(Booking b) throws RemoteException;

    public ArrayList<Car> getCars() throws RemoteException;

    public boolean isCarAvailable(String carId, BookingTimeFrame timeFrame) throws RemoteException;
}
