package ie.gmit.sw.webclient.dao;

import ie.gmit.sw.model.Car;
import ie.gmit.sw.model.Cars;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope( BeanDefinition.SCOPE_PROTOTYPE )
public class CarDAO extends DAO<Car>{
    @Value("${spring.data.rest.base-path}")
    private String restURI;
    public static String restCarPath="/car/";

    public CarDAO(){
        super();
    }

    /**
     * Get the list for cars
     * @return
     */
    public List<Car> getCars(){
        //Get a list of cars from the rest server
        return ((Cars) super.restUtils.restRequest(restCarPath+"list", new Cars())).getCars();
    }

    @Override
    public Car forId(String id){
        //Filter the cars list to match the id
        return this.getCars().stream().filter((c)->c.getId().equals(id)).findFirst().get();
    }

    @Override
    public void save(Car o) {

    }

}
