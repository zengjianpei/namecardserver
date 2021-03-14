package com.devsjk.namecardserver.dao;

import com.devsjk.namecardserver.model.Company;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CompanyDao {

	int insertCompany(Company company);

	int deleteCompany(Company company);

	Company findCompany(Company company);

	int updateCompany(Company company);

	List<Company> listCompany(Company company);

	Long listCompanyCount(Company company);
}