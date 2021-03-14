package com.devsjk.namecardserver.applet.controller;

import com.devsjk.namecardserver.applet.service.CompanyUserService;
import com.devsjk.namecardserver.aspect.UserTokenVerify;
import com.devsjk.namecardserver.model.CompanyUser;
import com.leshang.framework3.Exception.BusinessException;
import com.leshang.framework3.annotation.AutoReturn;
import com.leshang.framework3.annotation.LeshangController;
import com.leshang.framework3.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

/**
 * @Auther: zjp
 * @Date: 2020/9/29 17:09
 * @Description:
 */
@LeshangController("appletCompanyUserController")
public class CompanyUserController extends BaseController {

    @Autowired
    private CompanyUserService companyUserService;

    @PostMapping("/applet/v1/companyUser/find")
    @AutoReturn
    @UserTokenVerify
    public void findCompanyUser(CompanyUser companyUser){
        this.setResult(companyUserService.findCompanyUser(companyUser));
    }

    @PostMapping("/applet/v1/companyUser/findById")
    @AutoReturn
    @UserTokenVerify
    public void findCompanyUserById(CompanyUser companyUser){
        this.setResult(companyUserService.findCompanyUserById(companyUser));
    }

    @PostMapping("/applet/v1/companyUser/findAll")
    @AutoReturn
    @UserTokenVerify
    public void findCompanyUserAllById(CompanyUser companyUser){
        this.setResult(companyUserService.findCompanyUserAllById(companyUser));
    }

    @PostMapping("/applet/v1/companyUser/update")
    @AutoReturn
    @UserTokenVerify
    public void updateCompanyUser(CompanyUser companyUser) throws BusinessException {
        companyUserService.updateCompanyUser(companyUser);
    }

    @PostMapping("/applet/v1/companyUser/getQcode")
    @AutoReturn
    public void getQcode(CompanyUser companyUser) throws BusinessException, IOException {
        this.setResult(companyUserService.getQcode(companyUser));
    }

    //获取名片访客
    @PostMapping("/applet/v1/companyUser/getVisitors")
    @AutoReturn
    public void getVisitors(CompanyUser companyUser){
        this.setResult(companyUserService.getVisitors(companyUser));
        this.setTotal(companyUserService.getCountVisitors(companyUser));
    }

    //获取名片夹
    @PostMapping("/applet/v1/companyUser/getCardPocket")
    @AutoReturn
    @UserTokenVerify
    public void getCardPocket(CompanyUser companyUser){
        this.setResult(companyUserService.getCardPocket(companyUser));
        this.setTotal(companyUserService.getCountCardPocket(companyUser));
    }

    @PostMapping("/applet/v1/companyUser/getVisitorsStatistics")
    @AutoReturn
    @UserTokenVerify
    public void getVisitorsStatistics(CompanyUser companyUser){
        this.setResult(companyUserService.getVisitorsStatistics(companyUser));
    }

    /**
     * 邀请员工
     * @param companyUser
     * @throws BusinessException
     */
    @PostMapping("/applet/v1/companyUser/inviteStaff")
    @AutoReturn
    @UserTokenVerify
    public void inviteStaff(CompanyUser companyUser) throws BusinessException{
        companyUserService.inviteStaff(companyUser);
    }

    @PostMapping("/applet/v1/companyUser/staffManager")
    @AutoReturn
    @UserTokenVerify
    public void listStaffManager(CompanyUser companyUser){
        this.setResult(companyUserService.listStaffManager(companyUser));
        this.setTotal(companyUserService.listCountStaffManager(companyUser));
    }

}
