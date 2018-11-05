package ie.gmit.sw.server;

import com.mongodb.client.MongoDatabase;

import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Sets up the RMI server and starts it
 */
public class Server {
    //Serving port
    private int port = 1099;
    private String name= "BookingRMIService";

    /**
     * Constuctor taking the system args
     *
     * @param args
     */
    public Server(String[] args) {
        setPortNumberFromArgs(args);
    }

    /**
     * Starts injects mongodb and starts rmiservice
     *
     * @param db
     * @throws Exception
     */
    public void Start(MongoDatabase db) throws Exception {

        //Interface for booking
        BookingService s = new BookingServiceImpl(db);
        System.out.println(port);
        //Start the RMI registry on port 1099
        Registry r=LocateRegistry.createRegistry(port);
        try {
            r.bind(name, s);
        } catch (AlreadyBoundException ae) {
            r.rebind(name, s);
        }
        //Bind to "RMIService
        //Naming.rebind("BookingRMIService", s);

        System.out.println("Server started on port:" + port + " as \""+name+"\"");
    }

    /**
     * Checks if the args array contains port.
     * If it does then sets the port to be the provided one
     *
     * @param args
     */
    private void setPortNumberFromArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            //Check if there is next
            if (args.length > i + 1) {
                //Decide which parameter
                switch (args[i]) {
                    case "-port":
                        try {
                            //Try to parse the port number
                            port = Integer.valueOf(args[i + 1]);
                        } catch (Exception e) {
                        }
                        break;
                    case "-name":
                        name = args[i + 1];
                        break;
                }
            }
        }
    }
}
