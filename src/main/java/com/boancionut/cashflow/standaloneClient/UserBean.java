package com.boancionut.cashflow.standaloneClient;

import com.boancionut.cashflow.ejb.model.User;
import com.boancionut.cashflow.ejbClient.UserStatelessEjbRemote;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("userBean")
@ViewScoped
public class UserBean implements Serializable {

    private String name;
    private String email;
    private String password;
    private List<User> userList;

    @EJB
    private UserStatelessEjbRemote firstStatelessEjb;

    @PostConstruct
    public void init() {
        userList = firstStatelessEjb.getAllUsers();
    }

    public void addUser() {
        firstStatelessEjb.insert(name, email, password);
        userList = firstStatelessEjb.getAllUsers();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getUserList() {
        return userList;
    }
}
