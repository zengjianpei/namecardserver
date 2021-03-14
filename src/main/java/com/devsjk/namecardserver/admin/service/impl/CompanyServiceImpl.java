package com.devsjk.namecardserver.admin.service.impl;

import com.devsjk.namecardserver.dao.CompanyDao;
import com.devsjk.namecardserver.model.Company;
import com.devsjk.namecardserver.admin.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("adminCompanyService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyDao companyDao;

	@Override
	public void insertCompany(Company company) {
		companyDao.insertCompany(company);
	}

	@Override
	public void deleteCompany(Company company) {
		companyDao.deleteCompany(company);
	}

	@Override
	public Company findCompany(Company company) {
		return companyDao.findCompany(company);
	}

	@Override
	public void updateCompany(Company company) {
		companyDao.updateCompany(company);
	}

	@Override
	public List<Company> listCompany(Company company) {
		return companyDao.listCompany(company);
	}

	@Override
	public Long listCompanyCount(Company company) {
		return companyDao.listCompanyCount(company);
	}
}