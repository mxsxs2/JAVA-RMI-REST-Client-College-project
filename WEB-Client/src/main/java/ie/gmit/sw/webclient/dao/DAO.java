package ie.gmit.sw.webclient.dao;

import ie.gmit.sw.webclient.RestService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DAO {

    @Autowired
    public RestService restService;

    /**
     * Get entity for id
     *
     * @return
     */
    public abstract Object forId(String id) throws Exception;

    /**
     * Save entity
     *
     * @param o
     */
    public abstract Object save(Object o) throws Exception;
}
