package ie.gmit.sw.webclient.dao;

import ie.gmit.sw.webclient.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

import java.net.ConnectException;

public abstract class DAO {

    @Autowired
    public RestUtils restUtils;

    /**
     * Get entity for id
     *
     * @return
     */
    public abstract Object forId(String id) throws HttpClientErrorException, ConnectException;

    /**
     * Save entity
     *
     * @param o
     */
    public abstract Object save(Object o) throws HttpClientErrorException, ConnectException;
}
