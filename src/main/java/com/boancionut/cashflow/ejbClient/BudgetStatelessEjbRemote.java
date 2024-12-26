package com.boancionut.cashflow.ejbClient;

import com.boancionut.cashflow.ejb.model.Budget;
import com.boancionut.cashflow.ejb.model.Category;
import com.boancionut.cashflow.ejb.model.User;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface BudgetStatelessEjbRemote extends BaseStatelessEjbRemote<Budget> {
    User getUserById(long id);
}
