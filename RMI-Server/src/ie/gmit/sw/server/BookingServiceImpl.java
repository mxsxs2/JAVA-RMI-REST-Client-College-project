package ie.gmit.sw.server;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import ie.gmit.sw.model.Booking;
import org.bson.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.*;
import java.rmi.server.*;

public class BookingServiceImpl extends UnicastRemoteObject implements BookingService {
    private static final long serialVersionUID = 1L;
    //Name of the database collection
    private static final String collectionName = "carbooking";
    //The database collection
    private MongoCollection collection;

    protected BookingServiceImpl(MongoDatabase db) throws RemoteException {
        super();
        collection = db.getCollection(collectionName);
    }


    @Override
    public Booking getBooking(String id) throws RemoteException {
        //Create new query object
        BasicDBObject query = new BasicDBObject();
        //Find by id
        query.append("id", id);
        //Set up mongo find
        FindIterable fi = collection.find(query);
        //Limit to 1
        fi.limit(1);
        //Get the first one(the only one)
        DBObject dbo = (DBObject) fi.first();
        //Remove mongo's id
        dbo.removeField("_id");
        try {
            //Try to convert into a booking and return
            return JSONTOBooking(JSON.serialize(dbo));
        } catch (Exception e) {
        }

        return null;
    }

    @Override
    public boolean addBooking(Booking b) throws RemoteException {
        try {
            //Parse the object into JSON then insert ti the db
            collection.insertOne(JSON.parse(objectToJSON(b)));
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public boolean changeBooking(Booking b) throws RemoteException {
        try {
            //Create new query object
            BasicDBObject query = new BasicDBObject();
            //Find by id
            query.append("id", b.getId());
            //Parse the object into JSON then try to find and replace
            Document rs = (Document) collection.findOneAndUpdate(query, Document.parse(objectToJSON(b)));
            return rs.get("id").equals(b.getId());
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Parses a booking into JSON with JAXB
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

    /**
     * Parses JSON into a Booking class with JAXB
     *
     * @param json
     * @return
     * @throws Exception
     */
    private Booking JSONTOBooking(String json) throws Exception {
        JAXBContext jc = JAXBContext.newInstance("ie.gmit.sw.model");
        Unmarshaller um = jc.createUnmarshaller();
        um.setProperty("eclipselink.media-type", "application/json");
        um.setProperty("eclipselink.json.include-root", false);
        JAXBElement<Booking> poElement2 = um.unmarshal(new StreamSource(new StringReader(json)), Booking.class);
        return (Booking) poElement2.getValue();
    }
}
