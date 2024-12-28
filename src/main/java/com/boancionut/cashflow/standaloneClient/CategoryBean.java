package com.boancionut.cashflow.standaloneClient;

import com.boancionut.cashflow.ejb.model.Category;
import com.boancionut.cashflow.ejbClient.CategoryStatelessEjbRemote;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("categoryBean")
@SessionScoped
public class CategoryBean implements Serializable {

    private String name;
    private List<Category> categories;

    @EJB
    private CategoryStatelessEjbRemote categoryStatelessEjbRemote;

    @PostConstruct
    public void init() {
        categories = categoryStatelessEjbRemote.getAll();
    }

    public void addCategory() {
        categoryStatelessEjbRemote.insert(new Category(name));
        categories = categoryStatelessEjbRemote.getAll();
    }

    public void deleteCategory(Long categoryId) {
        categoryStatelessEjbRemote.delete(categoryId);
        categories = categoryStatelessEjbRemote.getAll();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}