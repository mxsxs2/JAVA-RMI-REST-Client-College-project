package ie.gmit.sw.server;

import com.mongodb.client.MongoDatabase;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * Sets up the RMI server and starts it
 */
public class Server {
    //Serving port
    private int port = 1099;

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

        //Start the RMI regstry on port 1099
        LocateRegistry.createRegistry(port);

        //Bind to "RMIService
        Naming.rebind("BookingRMIService", s);

        System.out.println("Server started on port:" + port);
    }

    /**
     * Checks if the args array contains port.
     * If it does then sets the port to be the provided one
     *
     * @param args
     */
    private void setPortNumberFromArgs(String[] args) {
        //Loop the args
        for (int i = 0; i < args.length; i++) {
            //Check if port exists
            if (args[i].equals("-port") && args.length < i + 1) {
                try {
                    //Try to parse the port number
                    port = Integer.valueOf(args[i + 1]);
                } catch (Exception e) {
                }
                break;
            }
        }
    }
}
