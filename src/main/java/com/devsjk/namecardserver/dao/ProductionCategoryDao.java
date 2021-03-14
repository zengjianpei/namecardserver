package com.devsjk.namecardserver.dao;

import com.devsjk.namecardserver.model.Production;
import com.devsjk.namecardserver.model.ProductionCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductionCategoryDao {

    List<ProductionCategory> listProductionCategoryList(ProductionCategory productionCategory);

    int insertProductionCategory(ProductionCategory productionCategory);

    int deleteProductionCategory(ProductionCategory productionCategory);

    int updateSort(ProductionCategory productionCategory);
}
