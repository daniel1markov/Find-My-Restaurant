package com.hit.dao;

import com.hit.dm.Restaurant;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IDao<ID extends java.io.Serializable,T>{
    void save(T entity) throws IOException;
    void delete(ID entity) throws IOException;
    void update (T entity) throws IOException;
    Map <ID, List <T>> findByCategory() throws IOException;
    List <T> findAll() throws IOException, ClassNotFoundException;
}
