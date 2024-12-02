package com.boancionut.testejb.ejb;

import com.boancionut.testejb.ejb.model.User;
import com.boancionut.testejb.ejbClient.FirstStatelessEjbRemote;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
@LocalBean
public class FirstStatelessEjb implements FirstStatelessEjbRemote {

    @PersistenceContext
    private EntityManager entityManager;
    public FirstStatelessEjb()
    {

    }

    @Override
    public void insert(String name) {
        entityManager.persist(new User(name));
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createNamedQuery("User.findAll", User.class);
        return query.getResultList();
    }
}
