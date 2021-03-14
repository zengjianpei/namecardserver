package com.devsjk.namecardserver.applet.service.impl;

import com.devsjk.namecardserver.applet.service.CompanyService;
import com.devsjk.namecardserver.bean.WxBean;
import com.devsjk.namecardserver.dao.CompanyDao;
import com.devsjk.namecardserver.dao.CompanyUserDao;
import com.devsjk.namecardserver.dao.SysConfigDao;
import com.devsjk.namecardserver.dao.WxUserDao;
import com.devsjk.namecardserver.model.Company;
import com.devsjk.namecardserver.model.SysConfig;
import com.devsjk.namecardserver.model.WxUser;
import com.devsjk.namecardserver.utils.wxUtils.WxUtils;
import com.leshang.framework3.Exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Auther: zjp
 * @Date: 2020/9/8 22:39
 * @Description:
 */
@Service("appletCompanyService")
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private WxUserDao wxUserDao;

    @Autowired
    private SysConfigDao sysConfigDao;

    @Override
    @Transactional
    public void insertCompany(Company company) throws BusinessException {

        SysConfig sysConfig=sysConfigDao.findByKey("tryDays");
        SysConfig sysConfig1=sysConfigDao.findByKey("staffCount");

        company.setStatus(1);
        company.setSignUpTime(LocalDateTime.now());
        company.setTryDeadline(LocalDateTime.now().plusDays(Long.valueOf(sysConfig.getValue())));
        company.setDeadline(LocalDateTime.now().plusDays(Long.valueOf(sysConfig.getValue())));
        company.setPeopleNum(Integer.valueOf(sysConfig1.getValue()));
        company.setComeInNum(0);
        companyDao.insertCompany(company);
        company.getAdminCompanyUser().setCompanyId(company.getId());
        company.getAdminCompanyUser().setCompanyOwner(1);
        company.getAdminCompanyUser().setWxUserId(Long.valueOf(company.getVerifyUserId()));

        companyUserDao.insertCompanyUser(company.getAdminCompanyUser());
        Company c=new Company();
        c.setId(company.getId());
        c.setAdminId(company.getAdminCompanyUser().getId());
        companyDao.updateCompany(c);
        WxUser wxUser=new WxUser();
        wxUser.setId(Long.valueOf(company.getVerifyUserId()));
        wxUser.setUserRegister(1);
        wxUserDao.updateWxUser(wxUser);


    }

    @Override
    public Company findCompany(Company company) {
        return companyDao.findCompany(company);
    }

    @Override
    @Transactional
    public void saveCompany(Company company) throws BusinessException {
        companyDao.updateCompany(company);
    }
}
