package com.boancionut.cashflow.standaloneClient;

import com.boancionut.cashflow.ejb.model.Budget;
import com.boancionut.cashflow.ejb.model.Category;
import com.boancionut.cashflow.ejb.model.User;
import com.boancionut.cashflow.ejbClient.BudgetStatelessEjbRemote;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("budgetBean")
@ViewScoped
public class BudgetBean implements Serializable {

    private String name;
    private double totalAmount;
    private List<Budget> budgets;
    private User user;

    @EJB
    private BudgetStatelessEjbRemote budgetStatelessEjbRemote;

    @PostConstruct
    public void init() {
        budgets = budgetStatelessEjbRemote.getAll();
    }

    public String addBudget() {
        user = budgetStatelessEjbRemote.getUserById(1L);
        budgetStatelessEjbRemote.insert(new Budget(name, totalAmount, user));
        return "index?faces-redirect=true";
    }

    public String deleteBudget(Long budgetId) {
        budgetStatelessEjbRemote.delete(budgetId);
        budgets = budgetStatelessEjbRemote.getAll();
        return "index?faces-redirect=true";
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String navigateToAddBudget() {
        return "addBudget?faces-redirect=true";
    }
}