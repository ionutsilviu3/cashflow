package com.boancionut.cashflow.ejbClient;

import com.boancionut.cashflow.ejb.model.Category;
import jakarta.ejb.Remote;

@Remote
public interface CategoryStatelessEjbRemote extends BaseStatelessEjbRemote<Category> {
}
