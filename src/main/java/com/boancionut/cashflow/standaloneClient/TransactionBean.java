package com.boancionut.cashflow.standaloneClient;

import com.boancionut.cashflow.ejb.TransactionEvent;
import com.boancionut.cashflow.ejb.model.Budget;
import com.boancionut.cashflow.ejb.model.Category;
import com.boancionut.cashflow.ejb.model.Transaction;
import com.boancionut.cashflow.ejbClient.TransactionStatelessEjbRemote;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Named("transactionBean")
@SessionScoped
public class TransactionBean implements Serializable {

    private static final int PAGE_SIZE = 10;
    private int currentPage = 0;
    private String description;
    private double amount;
    private Long categoryId; // ID of the selected category
    private Long budgetId; // ID of the selected budget
    private List<Transaction> transactions;
    private List<Transaction> paginatedTransactions;

    private Transaction currentTransaction;

    @Inject
    private Event<TransactionEvent> transactionEvent;

    @Inject
    private CategoryBean categoryBean;

    @Inject
    private BudgetBean budgetBean;

    @EJB
    private TransactionStatelessEjbRemote transactionStatelessEjbRemote;

    @PostConstruct
    public void init() {
        transactions = transactionStatelessEjbRemote.getAll();
        updatePaginatedTransactions();
    }

    public String saveTransaction() {
        Category selectedCategory = categoryBean.getCategories().stream()
                .filter(cat -> cat.getId() == (categoryId))
                .findFirst()
                .orElse(null);

        Budget selectedBudget = budgetBean.getBudgets().stream()
                .filter(bud -> bud.getId() == (budgetId))
                .findFirst()
                .orElse(null);

        Transaction transaction = new Transaction(description, amount);
        transaction.setCategory(selectedCategory);
        transaction.setBudget(selectedBudget);
        transactionStatelessEjbRemote.insert(transaction);

        // Update the budget's total amount
        if (selectedBudget != null) {
            double newTotalAmount = selectedBudget.getTotalAmount() + amount;
            selectedBudget.setTotalAmount(newTotalAmount);
            transactionStatelessEjbRemote.updateBudget(selectedBudget);
        }

        transactionEvent.fire(new TransactionEvent());

        transactions = transactionStatelessEjbRemote.getAll();
        updatePaginatedTransactions();
        clearFields();
        return "index?faces-redirect=true";
    }

    public String cancel() {
        return "index?faces-redirect=true";
    }

    public void clearFields() {
        description = "";
        amount = 0;
        categoryId = null;
        budgetId = null;
    }

    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionStatelessEjbRemote.findById(transactionId);
        if (transaction != null) {
            Budget budget = transaction.getBudget();
            if (budget != null) {
                double newTotalAmount = budget.getTotalAmount() - transaction.getAmount();
                budget.setTotalAmount(newTotalAmount);
                transactionStatelessEjbRemote.updateBudget(budget);
                transactionEvent.fire(new TransactionEvent());
            }
            transactionStatelessEjbRemote.delete(transactionId);
        }
        transactions = transactionStatelessEjbRemote.getAll();
        updatePaginatedTransactions();
    }

    public void loadTransaction(Transaction transaction) {
        this.currentTransaction = transaction;
    }

    public String saveEditedTransaction() {
        if (currentTransaction != null) {
            Transaction oldTransaction = transactionStatelessEjbRemote.findById(currentTransaction.getId());
            transactionStatelessEjbRemote.update(currentTransaction.getId(), currentTransaction);

            Budget budget = currentTransaction.getBudget();
            if (budget != null && oldTransaction != null) {
                double newTotalAmount = budget.getTotalAmount() - oldTransaction.getAmount() + currentTransaction.getAmount();
                budget.setTotalAmount(newTotalAmount);
                transactionStatelessEjbRemote.updateBudget(budget);
            }

            transactions = transactionStatelessEjbRemote.getAll();
            transactionEvent.fire(new TransactionEvent());
            clearFields();
        }
        return "index?faces-redirect=true";
    }

    public String cancelEdit() {
        return "index?faces-redirect=true";
    }

    public void nextPage() {
        if (hasNextPage()) {
            currentPage++;
            updatePaginatedTransactions();
        }
    }

    public void previousPage() {
        if (hasPreviousPage()) {
            currentPage--;
            updatePaginatedTransactions();
        }
    }

    public boolean hasNextPage() {
        return (currentPage + 1) * PAGE_SIZE < transactions.size();
    }

    public boolean hasPreviousPage() {
        return currentPage > 0;
    }

    public boolean getHasNextPage() {
        return hasNextPage();
    }

    public boolean getHasPreviousPage() {
        return hasPreviousPage();
    }

    public List<Transaction> getLastFiveTransactions() {
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private void updatePaginatedTransactions() {
        int start = currentPage * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, transactions.size());
        paginatedTransactions = transactions.subList(start, end);
    }

    public List<Transaction> getPaginatedTransactions() {
        return paginatedTransactions;
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
        return categoryBean.getCategories();
    }

    public List<Budget> getBudgets() {
        return budgetBean.getBudgets();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public String navigateToEditPage(Long transactionId) {
        this.currentTransaction = transactionStatelessEjbRemote.findById(transactionId);
        return "editTransaction?faces-redirect=true";
    }

    public double getTotalPositiveTransactions() {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() > 0)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalNegativeTransactions() {
        return transactions.stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}