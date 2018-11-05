package ie.gmit.sw.server;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.BookingTimeFrame;
import ie.gmit.sw.model.Car;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class BookingServiceImpl extends UnicastRemoteObject implements BookingService {
    private static final long serialVersionUID = 1L;
    //Name of the database collection
    private static final String bookingCollectionName = "bookings";
    //Name of the database collection
    private static final String carCollectionName = "cars";
    //The database booking collection
    private MongoCollection bookingCollection;
    //The database car collection
    private MongoCollection carCollection;

    protected BookingServiceImpl(MongoDatabase db) throws RemoteException {
        super();
        bookingCollection = db.getCollection(bookingCollectionName, Booking.class);
        carCollection = db.getCollection(carCollectionName, Car.class);
    }


    @Override
    public Booking getBooking(String id) throws RemoteException {
        try {
            //Find the firs object
            return (Booking) bookingCollection.find(Filters.eq("_id", id)).first();
        } catch (Exception e) {
        }

        return null;
    }

    @Override
    public boolean addBooking(Booking b) throws RemoteException {
        try {
            //Parse the object into JSON then insert ti the db
            bookingCollection.insertOne(b);
            //Check if it was added
            return bookingCollection.find(Filters.eq("_id", b.getId())).first() != null;
        } catch (Exception e) {
            //Return true if duplicate
            return e.getMessage().contains("E11000");
        }
    }

    @Override
    public boolean changeBooking(Booking b) throws RemoteException {
        try {
            Document d=Document.parse(objectToJSON(b));
            //Remove the _id for replace to awoid an exception
            d.remove("_id");
            //Parse the object into JSON then try to find and replace
            UpdateResult updateResult = bookingCollection.replaceOne(
                    //Filter by id
                    Filters.eq("_id", b.getId()),
                    d
            );
            //Check if any was modified
            return updateResult.getModifiedCount() == 1;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public ArrayList<Booking> getBookings() throws RemoteException {
        try {
            //Get a list of cars from mongo
            return (ArrayList<Booking>) bookingCollection.find(Booking.class).into(new ArrayList<Booking>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean deleteBooking(String id) throws RemoteException {
        try {
            //Delete one
            DeleteResult deleteResult = bookingCollection.deleteOne(
                    //Filter by id
                    Filters.eq("_id", id)
            );
            //Check if any was modified
            return deleteResult.getDeletedCount()== 1;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public boolean addCar(Car c) throws RemoteException {

        try {
            //Insert the document
            carCollection.insertOne(Document.parse((objectToJSON(c))));
            //Check if it was added
            return carCollection.find(Filters.eq("_id", c.getId())).first() != null;
        } catch (Exception e) {
            //Return true if duplicate
            return e.getMessage().contains("E11000");
        }
    }

    @Override
    public Car getCar(String id) throws RemoteException {
        try {
            //Find the firs object
            return (Car) carCollection.find(Filters.eq("_id", id)).first();
        } catch (Exception e) {
        }

        return null;
    }

    @Override
    public ArrayList<Car> getCars() throws RemoteException {
        try {
            //Get a list of cars from mongo
            return (ArrayList<Car>) carCollection.find(Car.class).into(new ArrayList<Car>());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isCarAvailable(String carId, BookingTimeFrame timeFrame) throws RemoteException {
        //Check if a car with the given id at the given date range is available
        Bson f2 = Filters.and(
                Filters.eq("car._id", carId),
                Filters.lte("bookingTimeFrame.bookingTimeFrom", timeFrame.getBookingTimeTo()),
                Filters.gte("bookingTimeFrame.bookingTimeTo", timeFrame.getBookingTimeFrom())
        );
        return bookingCollection.find(f2).first() == null;
    }

    /**
     * Parses an object to JSON with JAXB
     *
     * @param o
     * @return
     * @throws Exception
     */
    private String objectToJSON(Object o) throws Exception {
        JAXBContext jc = JAXBContext.newInstance("ie.gmit.sw.model");
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        m.setProperty("eclipselink.media-type", "application/json");
        m.setProperty("eclipselink.json.include-root", false);
        StringWriter sw = new StringWriter();
        m.marshal(o, sw);
        return sw.toString();
    }
}
