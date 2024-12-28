package com.boancionut.cashflow.standaloneClient;

import com.boancionut.cashflow.ejb.TransactionEvent;
import com.boancionut.cashflow.ejb.model.Budget;
import com.boancionut.cashflow.ejb.model.User;
import com.boancionut.cashflow.ejbClient.BudgetStatelessEjbRemote;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named("budgetBean")
@SessionScoped
public class BudgetBean implements Serializable {

    private static final int PAGE_SIZE = 10;
    private int currentPage = 0;
    private String name;
    private double totalAmount;
    private List<Budget> budgets;
    private List<Budget> paginatedBudgets;
    private User user;
    private Budget currentBudget;

    @EJB
    private BudgetStatelessEjbRemote budgetStatelessEjbRemote;

    @PostConstruct
    public void init() {
        budgets = budgetStatelessEjbRemote.getAll();
        updatePaginatedBudgets();
    }

    public String addBudget() {
        user = budgetStatelessEjbRemote.getUserById(1L);
        budgetStatelessEjbRemote.insert(new Budget(name, totalAmount, user));
        budgets = budgetStatelessEjbRemote.getAll();
        updatePaginatedBudgets();
        return "index?faces-redirect=true";
    }

    public String deleteBudget(Long budgetId) {
        budgetStatelessEjbRemote.delete(budgetId);
        budgets = budgetStatelessEjbRemote.getAll();
        updatePaginatedBudgets();
        return "index?faces-redirect=true";
    }

    public void nextPage() {
        if (hasNextPage()) {
            currentPage++;
            updatePaginatedBudgets();
        }
    }

    public void previousPage() {
        if (hasPreviousPage()) {
            currentPage--;
            updatePaginatedBudgets();
        }
    }

    public boolean hasNextPage() {
        return (currentPage + 1) * PAGE_SIZE < budgets.size();
    }

    public boolean hasPreviousPage() {
        return currentPage > 0;
    }

    private void updatePaginatedBudgets() {
        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, budgets.size());
        paginatedBudgets = budgets.subList(start, end);
    }

    public List<Budget> getPaginatedBudgets() {
        return paginatedBudgets;
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
    public String navigateToEditPage(Long budgetId) {
        this.currentBudget = budgetStatelessEjbRemote.findById(budgetId);
        return "editBudget?faces-redirect=true";
    }
    public boolean getHasNextPage() {
        return hasNextPage();
    }

    public boolean getHasPreviousPage() {
        return hasPreviousPage();
    }
    public double getTotalBudgets() {
        return budgets.stream()
                .mapToDouble(Budget::getTotalAmount)
                .sum();
    }

    public Budget getCurrentBudget() {
        return currentBudget;
    }

    public void setCurrentBudget(Budget currentBudget) {
        this.currentBudget = currentBudget;
    }

    public String saveEditedBudget() {
        if (currentBudget != null) {
            budgetStatelessEjbRemote.update(currentBudget.getId(), currentBudget);
            budgets = budgetStatelessEjbRemote.getAll();
        }
        return "index?faces-redirect=true";
    }

    public String cancelEdit() {
        return "index?faces-redirect=true";
    }

    public void onTransactionEvent(@Observes TransactionEvent event) {
        budgets = budgetStatelessEjbRemote.getAll();
    }
}