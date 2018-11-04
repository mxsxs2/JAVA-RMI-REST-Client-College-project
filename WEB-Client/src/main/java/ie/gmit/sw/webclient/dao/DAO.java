package ie.gmit.sw.webclient.dao;

import ie.gmit.sw.webclient.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DAO<T> {

    @Autowired
    public RestUtils restUtils;

    /**
     * Get entity for id
     * @return
     */
    public abstract T forId(String id);

    /**
     * Save entity
     * @param o
     */
    public abstract void save(T o);
}
