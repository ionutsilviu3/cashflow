package com.boancionut.cashflow.ejb;

import com.boancionut.cashflow.ejb.model.Budget;
import com.boancionut.cashflow.ejb.model.Category;
import com.boancionut.cashflow.ejb.model.User;
import com.boancionut.cashflow.ejbClient.BudgetStatelessEjbRemote;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
@LocalBean
public class BudgetStatelessEjb extends BaseStatelessEjb<Budget> implements BudgetStatelessEjbRemote {

    public BudgetStatelessEjb() {
        super(Budget.class);
    }

    @Override
    public User getUserById(long id) {
        return super.entityManager.find(User.class, id);
    }
}
