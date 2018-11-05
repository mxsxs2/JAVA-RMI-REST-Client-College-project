package ie.gmit.sw.server;

/**
 * Runner class for the RMI Server application
 * If 'port' arg exists and it is numeric then this port will be used. Otherwise 1099
 */
public class ServiceSetup {
    public static void main(String[] args) {
        //System.setProperty("java.rmi.server.hostname","192.168.0.199");
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

