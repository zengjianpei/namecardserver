package com.devsjk.namecardserver.applet.service.impl;

import com.devsjk.namecardserver.applet.service.ProductionCategoryService;
import com.devsjk.namecardserver.dao.ProductionCategoryDao;
import com.devsjk.namecardserver.dao.ProductionDao;
import com.devsjk.namecardserver.model.Production;
import com.devsjk.namecardserver.model.ProductionCategory;
import com.leshang.framework3.Exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: zjp
 * @Date: 2020/10/2 19:34
 * @Description:
 */
@Service("appletProductionCategoryService")
public class ProductionCategoryServiceImpl implements ProductionCategoryService {

    @Autowired
    private ProductionCategoryDao productionCategoryDao;

    @Autowired
    private ProductionDao productionDao;

    @Override
    public List<ProductionCategory> findByCompanyId(ProductionCategory productionCategory) {
        List<ProductionCategory> productionCategoryList=productionCategoryDao.listProductionCategoryList(productionCategory);
        /*productionCategoryList.stream().forEach(i->{
            i.setProductionList(productionDao.listProductionByCategoryId(new Production(i.getId())));
        });*/
        return productionCategoryList;
    }

    @Override
    @Transactional
    public void insertProductionCategory(ProductionCategory productionCategory) throws BusinessException {
        List<ProductionCategory> productionCategoryList=productionCategoryDao.listProductionCategoryList(productionCategory);
        if(null!=productionCategoryList&&productionCategoryList.size()>0){
            int count=productionCategoryList.get(productionCategoryList.size()-1).getClassifySort()+1;
            productionCategory.setClassifySort(count);
        }else{
            int count=1;
            productionCategory.setClassifySort(count);
        }

        productionCategoryDao.insertProductionCategory(productionCategory);
    }

    @Override
    public List<ProductionCategory> listByCompanyId(ProductionCategory productionCategory) {
        return productionCategoryDao.listProductionCategoryList(productionCategory);
    }

    @Override
    @Transactional
    public void deleteProductionCategory(ProductionCategory productionCategory) {
        productionCategoryDao.deleteProductionCategory(productionCategory);
    }

    @Override
    public void updateSort(List<ProductionCategory> productionCategoryList) {
        if(null!=productionCategoryList&&productionCategoryList.size()>0){
            int i=0;
            for(ProductionCategory productionCategory:productionCategoryList){
                productionCategory.setClassifySort(i++);
                productionCategoryDao.updateSort(productionCategory);
            }

        }
    }
}
