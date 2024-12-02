package com.boancionut.testejb.standaloneClient;

import com.boancionut.testejb.ejb.model.User;
import com.boancionut.testejb.ejbClient.FirstStatelessEjbRemote;
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
    private List<User> userList;

    @EJB
    private FirstStatelessEjbRemote firstStatelessEjb;

    @PostConstruct
    public void init() {
        userList = firstStatelessEjb.getAllUsers();
    }

    public void addUser() {
        firstStatelessEjb.insert(name);
        userList = firstStatelessEjb.getAllUsers();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }
}
