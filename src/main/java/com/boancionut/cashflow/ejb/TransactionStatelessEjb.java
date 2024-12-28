package com.boancionut.cashflow.ejb;

import com.boancionut.cashflow.ejb.model.Budget;
import com.boancionut.cashflow.ejb.model.Category;
import com.boancionut.cashflow.ejb.model.Transaction;
import com.boancionut.cashflow.ejbClient.TransactionStatelessEjbRemote;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
@LocalBean
public class TransactionStatelessEjb extends BaseStatelessEjb<Transaction> implements TransactionStatelessEjbRemote {

    public TransactionStatelessEjb() {
        super(Transaction.class);
    }

    @Override
    public List<Category> getCategories() {
        return super.entityManager.createNamedQuery("Category.findAll").getResultList();
    }

    @Override
    public List<Budget> getBudgets() {
        return super.entityManager.createNamedQuery("Budget.findAll").getResultList();
    }

    @Override
    public Budget findBudgetById(long id) {
        return super.entityManager.find(Budget.class, id);
    }

    @Override
    public Category findCategoryById(long id) {
        return super.entityManager.find(Category.class, id);
    }

    @Override
    public void updateBudget(Budget selectedBudget) {
        super.entityManager.merge(selectedBudget);
    }

}
