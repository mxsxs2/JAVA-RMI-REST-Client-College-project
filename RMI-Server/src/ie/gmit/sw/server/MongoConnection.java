package ie.gmit.sw.server;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;

/**
 * Class to handle database operations
 */
public class MongoConnection {
    private String host = "localhost";
    private int port = 27017;
    private String dbName = "carbooking";
    private MongoDatabase connectedDB;

    /**
     * Creates a new connection at the default host
     *
     * @throws Exception
     */
    public MongoConnection() throws Exception {
        Connect();
    }

    /**
     * Creates a new connection at the given host on the given port
     *
     * @param host
     * @param port
     * @throws Exception
     */
    public MongoConnection(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
        Connect();
    }

    /**
     * Creates a new connection at the given host on the given port and sets the database
     *
     * @param host
     * @param port
     * @param dbName
     */
    public MongoConnection(String host, int port, String dbName) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
    }

    /**
     * Creates a new connection from command line parameters
     *
     * @param args
     * @throws Exception
     */
    public MongoConnection(String[] args) throws Exception {
        setPortNumberFromArgs(args);
        Connect();
    }


    /**
     * Connects to the mongodatabase server
     *
     * @throws Exception
     */
    private void Connect() throws Exception {
        //Connect to mongodb and get the database
        connectedDB = new MongoClient(host, port).getDatabase(dbName);
    }

    /**
     * Returns the connected mongodb
     *
     * @return
     */
    public MongoDatabase getDatabase() {
        return connectedDB;
    }


    /**
     * Checks if any of the command line parameters exists
     *
     * @param args
     */
    private void setPortNumberFromArgs(String[] args) {
        //Loop the args
        for (int i = 0; i < args.length; i++) {
            //Check if there is next
            if (args.length < i + 1) {
                //Decide which parameter
                switch (args[i]) {
                    case "-dbport":
                        try {
                            //Try to parse the port number
                            port = Integer.valueOf(args[i + 1]);
                        } catch (Exception e) {
                        }
                        break;
                    case "-dbhost":
                        host = args[i + 1];
                        break;
                    case "-dbname":
                        dbName = args[i + 1];
                        break;
                }
            }
        }
    }
}
