package ie.gmit.sw.restserver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ie.gmit.sw.server.RMIClient;
@Path("rmi")
public class StartServer {
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String connectToRMI(){
        try{
            new RMIClient();
            System.out.print("asd");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "asd";
    }
}
