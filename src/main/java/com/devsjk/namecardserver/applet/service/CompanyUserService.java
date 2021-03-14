package com.devsjk.namecardserver.applet.service;

import com.devsjk.namecardserver.model.CardVisitRecord;
import com.devsjk.namecardserver.model.CompanyUser;
import com.leshang.framework3.Exception.BusinessException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CompanyUserService {

    CompanyUser findCompanyUser(CompanyUser companyUser);

    CompanyUser findCompanyUserById(CompanyUser companyUser);

    CompanyUser findCompanyUserAllById(CompanyUser companyUser);

    void updateCompanyUser(CompanyUser companyUser) throws BusinessException;

    CompanyUser getQcode(CompanyUser companyUser) throws BusinessException, IOException;

    List<CardVisitRecord> getVisitors(CompanyUser companyUser);

    Long getCountVisitors(CompanyUser companyUser);

    List<CardVisitRecord> getCardPocket(CompanyUser companyUser);

    Long getCountCardPocket(CompanyUser companyUser);

    void inviteStaff(CompanyUser companyUser) throws BusinessException;

    List<CompanyUser> listStaffManager(CompanyUser companyUser);

    Long listCountStaffManager(CompanyUser companyUser);

    Map<String,Integer> getVisitorsStatistics(CompanyUser companyUser);
}
