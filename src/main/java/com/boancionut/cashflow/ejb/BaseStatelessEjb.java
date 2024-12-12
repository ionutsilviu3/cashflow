package com.boancionut.cashflow.ejb;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

public abstract class BaseStatelessEjb<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> entityClass;

    public BaseStatelessEjb(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void insert(T entity) {
        entityManager.persist(entity);
    }

    public List<T> getAll() {
        TypedQuery<T> query = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
        return query.getResultList();
    }

    public T findById(Long id) {
        return entityManager.find(entityClass, id);
    }

    public void update(Long id, T entity) {
        T existingEntity = entityManager.find(entityClass, id);
        if (existingEntity != null) {
            entityManager.merge(entity);
        }
    }

    public void delete(Long id) {
        T entity = entityManager.find(entityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}