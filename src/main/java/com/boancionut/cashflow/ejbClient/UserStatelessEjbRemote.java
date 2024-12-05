package com.boancionut.cashflow.ejbClient;

import com.boancionut.cashflow.ejb.model.User;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface UserStatelessEjbRemote {

    void insert(String name, String email, String password);
    List<User> getAllUsers();
}

