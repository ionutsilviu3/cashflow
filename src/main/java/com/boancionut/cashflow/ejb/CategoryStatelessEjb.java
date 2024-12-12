package com.boancionut.cashflow.ejb;

import com.boancionut.cashflow.ejb.model.Category;
import com.boancionut.cashflow.ejb.model.User;
import com.boancionut.cashflow.ejbClient.CategoryStatelessEjbRemote;
import com.boancionut.cashflow.ejbClient.UserStatelessEjbRemote;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@Stateless
@LocalBean
public class CategoryStatelessEjb extends BaseStatelessEjb<Category> implements CategoryStatelessEjbRemote {

    public CategoryStatelessEjb() {
        super(Category.class);
    }
}