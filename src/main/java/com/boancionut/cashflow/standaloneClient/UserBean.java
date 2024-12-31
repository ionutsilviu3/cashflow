package com.boancionut.cashflow.standaloneClient;

import com.boancionut.cashflow.ejb.model.User;
import com.boancionut.cashflow.ejbClient.UserStatelessEjbRemote;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("userBean")
@SessionScoped
public class UserBean implements Serializable {

    private User user;

    @EJB
    private UserStatelessEjbRemote userStatelessEjbRemote;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (!userStatelessEjbRemote.findByEmail(user.getEmail())) {
            userStatelessEjbRemote.insert(user);
        }
        this.user = user;
    }
}