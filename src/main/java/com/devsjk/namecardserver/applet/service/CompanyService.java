package com.devsjk.namecardserver.applet.service;

import com.devsjk.namecardserver.model.Company;
import com.leshang.framework3.Exception.BusinessException;

public interface CompanyService {

    void insertCompany(Company company) throws BusinessException;

    Company findCompany(Company company);

    void saveCompany(Company company) throws BusinessException;

}
