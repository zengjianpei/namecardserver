package com.devsjk.namecardserver.applet.controller;

import com.devsjk.namecardserver.applet.service.ProductionCategoryService;
import com.devsjk.namecardserver.aspect.UserTokenVerify;
import com.devsjk.namecardserver.model.ProductionCategory;
import com.devsjk.namecardserver.model.WxUser;
import com.leshang.framework3.Exception.BusinessException;
import com.leshang.framework3.annotation.AutoReturn;
import com.leshang.framework3.annotation.LeshangController;
import com.leshang.framework3.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Auther: zjp
 * @Date: 2020/10/2 19:33
 * @Description:
 */
@LeshangController("appletProductionCategoryController")
public class ProductionCategoryController extends BaseController {

    @Autowired
    private ProductionCategoryService productionCategoryService;

    @PostMapping("/applet/v1/productionCategory/findByCompanyId")
    @UserTokenVerify
    @AutoReturn
    public void findByCompanyId(ProductionCategory productionCategory) throws BusinessException {
        this.setResult(productionCategoryService.findByCompanyId(productionCategory));
    }

    @PostMapping("/applet/v1/productionCategory/insert")
    @UserTokenVerify
    @AutoReturn
    public void insertProductionCategory(ProductionCategory productionCategory) throws BusinessException{
        productionCategoryService.insertProductionCategory(productionCategory);
    }

    @PostMapping("/applet/v1/productionCategory/listByCompanyId")
    @UserTokenVerify
    @AutoReturn
    public void listByCompanyId(ProductionCategory productionCategory) throws BusinessException {
        this.setResult(productionCategoryService.listByCompanyId(productionCategory));
    }

    @PostMapping("/applet/v1/productionCategory/delete")
    @UserTokenVerify
    @AutoReturn
    public void deleteProductionCategory(ProductionCategory productionCategory) throws BusinessException{
        productionCategoryService.deleteProductionCategory(productionCategory);
    }

    @PostMapping("/applet/v1/productionCategory/updateSort")
    @UserTokenVerify
    @AutoReturn
    public void updateSort(@RequestBody List<ProductionCategory> productionCategoryList){
        productionCategoryService.updateSort(productionCategoryList);
    }
}
