package com.devsjk.namecardserver.applet.service;

import com.devsjk.namecardserver.model.ProductionCategory;
import com.leshang.framework3.Exception.BusinessException;

import java.util.List;

public interface ProductionCategoryService {

    List<ProductionCategory> findByCompanyId(ProductionCategory productionCategory);

    void insertProductionCategory(ProductionCategory productionCategory) throws BusinessException;

    List<ProductionCategory> listByCompanyId(ProductionCategory productionCategory);

    void deleteProductionCategory(ProductionCategory productionCategory);

    void updateSort(List<ProductionCategory> productionCategoryList);
}
