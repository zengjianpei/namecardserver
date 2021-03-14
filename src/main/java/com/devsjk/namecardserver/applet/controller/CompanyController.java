package com.devsjk.namecardserver.applet.controller;

import com.devsjk.namecardserver.applet.service.CompanyService;
import com.devsjk.namecardserver.aspect.UserTokenVerify;
import com.devsjk.namecardserver.model.Company;
import com.devsjk.namecardserver.model.Industry;
import com.leshang.framework3.Exception.BusinessException;
import com.leshang.framework3.annotation.AutoReturn;
import com.leshang.framework3.annotation.LeshangController;
import com.leshang.framework3.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: zjp
 * @Date: 2020/9/8 22:38
 * @Description:
 */
@LeshangController("appletCompanyController")
public class CompanyController extends BaseController {

    @Autowired
    private CompanyService companyService;

    @PostMapping(value = "/applet/v1/company/insert")
    @AutoReturn
    @UserTokenVerify
    public void insertCompany(Company company) throws BusinessException {
        companyService.insertCompany(company);
    }

    @PostMapping("/applet/v1/company/find")
    @AutoReturn
    @UserTokenVerify
    public void findCompany(Company company){
        this.setResult(companyService.findCompany(company));
    }

    @PostMapping("/applet/v1/company/update")
    @AutoReturn
    @UserTokenVerify
    public void updateCompany(Company company) throws BusinessException{
        companyService.saveCompany(company);
    }

}
