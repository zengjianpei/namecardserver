package com.devsjk.namecardserver.admin.service;

import com.devsjk.namecardserver.model.Company;
import java.util.List;

public interface CompanyService {

	void insertCompany(Company company);

	void deleteCompany(Company company);

	Company findCompany(Company company);

	void updateCompany(Company company);

	List<Company> listCompany(Company company);

	Long listCompanyCount(Company company);
}