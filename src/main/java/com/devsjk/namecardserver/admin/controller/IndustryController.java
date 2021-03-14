package com.devsjk.namecardserver.admin.controller;

import com.devsjk.namecardserver.model.Industry;
import com.devsjk.namecardserver.admin.service.IndustryService;
import com.leshang.framework3.annotation.AutoReturn;
import com.leshang.framework3.annotation.LeshangController;
import org.springframework.beans.factory.annotation.Autowired;
import com.leshang.framework3.controller.BaseController;
import org.springframework.web.bind.annotation.PostMapping;

@LeshangController("adminIndustryController")
public class IndustryController extends BaseController {

	@Autowired
	private IndustryService industryService;

	@PostMapping(value = "/admin/v1/industry/insert")
	@AutoReturn
	public void insertIndustry(Industry industry) {
		industryService.insertIndustry(industry);
	}

	@PostMapping(value = "/admin/v1/industry/delete")
	@AutoReturn
	public void deleteIndustry(Industry industry) {
		industryService.deleteIndustry(industry);
	}

	@PostMapping(value = "/admin/v1/industry/find")
	@AutoReturn
	public void findIndustry(Industry industry) {
		this.setResult(industryService.findIndustry(industry));
	}

	@PostMapping(value = "/admin/v1/industry/update")
	@AutoReturn
	public void updateIndustry(Industry industry) {
		industryService.updateIndustry(industry);
	}

	@PostMapping(value = "/admin/v1/industry/list")
	@AutoReturn
	public void listIndustry(Industry industry) {
		this.setResult(industryService.listIndustry(industry));
		this.setTotal(industryService.listIndustryCount(industry));
	}
}