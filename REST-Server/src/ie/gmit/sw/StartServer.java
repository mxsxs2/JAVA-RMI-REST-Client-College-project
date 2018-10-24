package ie.gmit.sw;

import ie.gmit.sw.server.RMIClient;

public class StartServer {
    public static void main(String[] args){
        try{
            new RMIClient();
            System.out.print("asd");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
