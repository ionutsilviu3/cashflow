package com.boancionut.testejb.ejb.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@NamedQuery(name= "User.findAll", query = "SELECT u from User u")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String userName;

    public User(){
    }

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
