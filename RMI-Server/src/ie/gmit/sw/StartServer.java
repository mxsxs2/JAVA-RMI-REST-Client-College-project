package ie.gmit.sw;

import ie.gmit.sw.server.Server;

/**
 * Runner class for the RMI Server application
 * If 'port' arg exists and it is numeric then this port will be used. Otherwise 1099
 */
public class StartServer {
    public static void main(String[] args) {
        Server s = new Server(args);
        try {
            s.Start();
        } catch (Exception e) {
            System.out.println("Server could not start because of: ");
            System.out.println(e.getMessage());
        }


    }

}

