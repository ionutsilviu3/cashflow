package com.boancionut.cashflow.ejb;

import com.boancionut.cashflow.ejb.model.User;
import com.boancionut.cashflow.ejbClient.UserStatelessEjbRemote;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
@LocalBean
public class UserStatelessEjb extends BaseStatelessEjb<User> implements UserStatelessEjbRemote {

    public UserStatelessEjb() {
        super(User.class);
    }

    @Override
    public boolean findByEmail(String email) {
        return entityManager.createNamedQuery("User.findByEmail")
                .setParameter("email", email)
                .getResultList()
                .size() > 0;
    }
}