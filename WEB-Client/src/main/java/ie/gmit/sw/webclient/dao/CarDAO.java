package ie.gmit.sw.webclient.dao;

import ie.gmit.sw.model.Car;
import ie.gmit.sw.model.Cars;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CarDAO extends DAO {
    public static String restCarPath = "/car/";

    public CarDAO() {
        super();
    }

    /**
     * Get the list for cars
     *
     * @return
     */
    public List<Car> getCars() throws Exception {
        //Get a list of cars from the rest server
        return ((Cars) super.restService.restRequest(restCarPath + "list", new Cars(), HttpMethod.GET)).getCars();
    }

    @Override
    public Object forId(String id) throws Exception {
        //Filter the cars list to match the id
        return this.getCars().stream().filter((c) -> c.getId().equals(id)).findFirst().get();
    }

    @Override
    public Object save(Object o) {
        return null;
    }

}
