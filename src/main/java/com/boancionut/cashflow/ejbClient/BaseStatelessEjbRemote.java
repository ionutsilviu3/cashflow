package com.boancionut.cashflow.ejbClient;

import java.util.List;

public interface BaseStatelessEjbRemote<T> {

    void insert(T entity);

    List<T> getAll();

    T findById(Long id);

    void update(Long id, T entity);

    void delete(Long id);
}
