package com.boancionut.testejb.ejbClient;

import com.boancionut.testejb.ejb.model.User;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface FirstStatelessEjbRemote {

    void insert(String name);
    List<User> getAllUsers();
}

