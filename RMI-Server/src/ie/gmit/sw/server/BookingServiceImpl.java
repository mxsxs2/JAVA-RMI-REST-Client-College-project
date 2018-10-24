package ie.gmit.sw.server;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import ie.gmit.sw.model.Booking;
import ie.gmit.sw.model.BookingTimeFrame;
import ie.gmit.sw.model.Car;
import org.bson.Document;

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
        //Create new query object
        Document query = new Document();
        //Find by id
        query.append("_id", id);
        try {
            //Find the firs object
            return (Booking) bookingCollection.find(query).first();
        } catch (Exception e) {
        }

        return null;
    }

    @Override
    public boolean addBooking(Booking b) throws RemoteException {
        try {
            //Parse the object into JSON then insert ti the db
            bookingCollection.insertOne(b);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public boolean changeBooking(Booking b) throws RemoteException {
        try {
            //Create new query object
            Document query = new Document();
            //Find by id
            query.append("_id", b.getId());
            //Parse the object into JSON then try to find and replace
            UpdateResult updateResult = bookingCollection.replaceOne(query, Document.parse(objectToJSON(b)));
            //Check if any was modified
            return updateResult.getModifiedCount() == 1;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public ArrayList<Car> getCars() throws RemoteException {
        try {
            //Get a list of cars from mongo
            return (ArrayList<Car>) carCollection.find(Car.class).into(new ArrayList<Car>());
        } catch (Exception e) {
        }

        return new ArrayList<>();
    }

    @Override
    public boolean isCarAvailable(String carId, BookingTimeFrame timeFrame) throws RemoteException {
        //TODO: Implement this!!
        return false;
    }

    /**
     * Parses an object to JSON with JAXB
     *
     * @param o
     * @return
     * @throws Exception
     */
    private String objectToJSON(Booking o) throws Exception {
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
