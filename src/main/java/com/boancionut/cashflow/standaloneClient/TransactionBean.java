package com.boancionut.cashflow.standaloneClient;

import com.boancionut.cashflow.ejb.model.Budget;
import com.boancionut.cashflow.ejb.model.Category;
import com.boancionut.cashflow.ejb.model.Transaction;
import com.boancionut.cashflow.ejbClient.TransactionStatelessEjbRemote;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("transactionBean")
@ViewScoped
public class TransactionBean implements Serializable {

    private String description;
    private double amount;
    private Long categoryId; // ID of the selected category
    private Long budgetId; // ID of the selected budget
    private List<Category> categories;
    private List<Budget> budgets;
    private List<Transaction> transactions;

    @EJB
    private TransactionStatelessEjbRemote transactionStatelessEjbRemote;

    @PostConstruct
    public void init() {
        description = "test";
        transactions = transactionStatelessEjbRemote.getAll();
        categories = transactionStatelessEjbRemote.getCategories();
        budgets = transactionStatelessEjbRemote.getBudgets();
    }

    public String saveTransaction() {
        Category selectedCategory = categories.stream()
                .filter(cat -> cat.getId() == categoryId)
                .findFirst()
                .orElse(null);

        Budget selectedBudget = budgets.stream()
                .filter(bud -> bud.getId() == budgetId)
                .findFirst()
                .orElse(null);

        Transaction transaction = new Transaction(description, amount);
        transaction.setCategory(selectedCategory);
        transaction.setBudget(selectedBudget);
        transactionStatelessEjbRemote.insert(transaction);

        return "index?faces-redirect=true";
    }

    public String cancel() {
        return "index?faces-redirect=true";
    }

    public void deleteTransaction(Long transactionId) {
        transactionStatelessEjbRemote.delete(transactionId);
        transactions = transactionStatelessEjbRemote.getAll();
    }

    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}