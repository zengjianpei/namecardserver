package com.devsjk.namecardserver.admin.controller;

import com.devsjk.namecardserver.model.Company;
import com.devsjk.namecardserver.admin.service.CompanyService;
import com.leshang.framework3.annotation.AutoReturn;
import com.leshang.framework3.annotation.LeshangController;
import org.springframework.beans.factory.annotation.Autowired;
import com.leshang.framework3.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@LeshangController("adminCompanyController")
public class CompanyController extends BaseController {

	@Autowired
	private CompanyService companyService;

	@PostMapping(value = "/admin/v1/company/insert")
	@AutoReturn
	public void insertCompany(Company company) {
		companyService.insertCompany(company);
	}

	@PostMapping(value = "/admin/v1/company/delete")
	@AutoReturn
	public void deleteCompany(Company company) {
		companyService.deleteCompany(company);
	}

	@PostMapping(value = "/admin/v1/company/find")
	@AutoReturn
	public void findCompany(Company company) {
		this.setResult(companyService.findCompany(company));
	}

	@PostMapping(value = "/admin/v1/company/update")
	@AutoReturn
	public void updateCompany(Company company) {
		companyService.updateCompany(company);
	}

	@PostMapping(value = "/admin/v1/company/list")
	@AutoReturn
	public void listCompany(Company company) {
		this.setResult(companyService.listCompany(company));
		this.setTotal(companyService.listCompanyCount(company));
	}

	@GetMapping(value = "/admin/v1/test")
    @AutoReturn
    public void vueTest(){
	    this.setResult("ok");
    }
}