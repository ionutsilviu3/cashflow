package com.boancionut.cashflow.ejbClient;

import com.boancionut.cashflow.ejb.model.Budget;
import com.boancionut.cashflow.ejb.model.Category;
import com.boancionut.cashflow.ejb.model.Transaction;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TransactionStatelessEjbRemote extends BaseStatelessEjbRemote<Transaction>{
    List<Category> getCategories();
    List<Budget> getBudgets();
    Budget findBudgetById(long id);
    Category findCategoryById(long id);

    void updateBudget(Budget selectedBudget);
}
