package com.boancionut.cashflow.ejbClient;

import com.boancionut.cashflow.ejb.model.User;
import jakarta.ejb.Remote;


@Remote
public interface UserStatelessEjbRemote extends BaseStatelessEjbRemote<User> {

}