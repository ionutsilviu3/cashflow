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
    private double amount;
    private List<Budget> budgets;
    private User user;
    private List<Category> categoryList;

    @EJB
    private BudgetStatelessEjbRemote budgetStatelessEjbRemote;

    @PostConstruct
    public void init() {
        budgets = budgetStatelessEjbRemote.getAll();
    }

    public String addBudget() {
        user = budgetStatelessEjbRemote.getUserById(1L);
        categoryList = budgetStatelessEjbRemote.getCategories();
        budgetStatelessEjbRemote.insert(new Budget(name, amount, user, categoryList));
        return "index?faces-redirect=true";
    }

    public String deleteBudget(int budgetId) {
        budgetStatelessEjbRemote.delete((long) budgetId);
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String navigateToAddBudget() {
        return "addBudget?faces-redirect=true";
    }
}