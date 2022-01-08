package com.hit.dao;

import com.hit.dm.Restaurant;

import java.io.IOException;
import java.util.List;

public interface IDao<ID extends java.io.Serializable,T>{
    void save(T entity) throws IOException, ClassNotFoundException;
    void delete(ID entity) throws IOException, ClassNotFoundException;
    void update (T entity) throws IOException;
    List <T> findAll() throws IOException, ClassNotFoundException;
}
