package com.devsjk.namecardserver.applet.controller;

import com.devsjk.namecardserver.applet.service.ProductionService;
import com.devsjk.namecardserver.aspect.UserTokenVerify;
import com.devsjk.namecardserver.model.Production;
import com.leshang.framework3.Exception.BusinessException;
import com.leshang.framework3.annotation.AutoReturn;
import com.leshang.framework3.annotation.LeshangController;
import com.leshang.framework3.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: zjp
 * @Date: 2020/10/9 14:09
 * @Description:
 */
@LeshangController("appletProductionController")
public class ProductionController extends BaseController {

    @Autowired
    private ProductionService productionService;

    @PostMapping("/applet/v1/production/listByCompanyId")
    @AutoReturn
    @UserTokenVerify
    public void listByCompanyId(Production production){
        this.setResult(productionService.listByCompanyId(production));
        this.setTotal(productionService.listCountByCompanyId(production));
    }

    @PostMapping("/applet/v1/production/insert")
    @AutoReturn
    @UserTokenVerify
    public void insertProduction(Production production) throws BusinessException {
        productionService.insertProduction(production);
    }

    @PostMapping("/applet/v1/production/update")
    @AutoReturn
    @UserTokenVerify
    public void updateProduction(Production production) throws BusinessException {
        productionService.updateProduction(production);
    }

    @PostMapping("/applet/v1/production/find")
    @AutoReturn
    @UserTokenVerify
    public void findProduction(Production production){
        this.setResult(productionService.findProduction(production));
    }

    @PostMapping("/applet/v1/production/delete")
    @AutoReturn
    @UserTokenVerify
    public void deleteProduction(Production production) throws BusinessException {
        productionService.deleteProduction(production);
    }
}
