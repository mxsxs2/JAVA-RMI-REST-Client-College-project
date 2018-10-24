package ie.gmit.sw;

import ie.gmit.sw.server.MongoConnection;
import ie.gmit.sw.server.Server;

/**
 * Runner class for the RMI Server application
 * If 'port' arg exists and it is numeric then this port will be used. Otherwise 1099
 */
public class StartServer {
    public static void main(String[] args) {
        Server s = new Server(args);

        try {
            //Get a mongodatabase connection
            MongoConnection m = new MongoConnection(args);
            //Start the RMI server
            s.Start(m.getDatabase());
        } catch (Exception e) {
            System.out.println("Server could not start because of: ");
            System.out.println(e.getMessage());
        }


    }

}

