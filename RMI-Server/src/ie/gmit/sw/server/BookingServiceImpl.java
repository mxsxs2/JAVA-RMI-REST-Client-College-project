package ie.gmit.sw.server;


import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.Car;
import ie.gmit.sw.model.Person;

import java.rmi.*;
import java.rmi.server.*;

public class BookingServiceImpl extends UnicastRemoteObject implements BookingService {
    private static final long serialVersionUID = 1L;

    protected BookingServiceImpl() throws RemoteException {
        super();
    }


    @Override
    public Booking getBooking(String id) throws RemoteException {
        return null;
    }

    @Override
    public boolean addBooking(Person p, Car c) throws RemoteException {
        return false;
    }

    @Override
    public boolean changeBooking(Booking b) throws RemoteException {
        return false;
    }
}
