package com.boancionut.cashflow.ejb;

import com.boancionut.cashflow.ejb.model.User;
import com.boancionut.cashflow.ejbClient.UserStatelessEjbRemote;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;

@Stateless
@LocalBean
public class UserStatelessEjb implements UserStatelessEjbRemote {

    @PersistenceContext
    private EntityManager entityManager;
    public UserStatelessEjb()
    {

    }

    @Override
    public void insert(String name, String email, String password) {
        entityManager.persist(new User(name, email, password));
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createNamedQuery("User.findAll", User.class);
        return query.getResultList();
    }
}
