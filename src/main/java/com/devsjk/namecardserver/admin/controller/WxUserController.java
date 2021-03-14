package com.devsjk.namecardserver.admin.controller;

import com.devsjk.namecardserver.model.WxUser;
import com.devsjk.namecardserver.admin.service.WxUserService;
import com.leshang.framework3.annotation.AutoReturn;
import com.leshang.framework3.annotation.LeshangController;
import org.springframework.beans.factory.annotation.Autowired;
import com.leshang.framework3.controller.BaseController;
import org.springframework.web.bind.annotation.PostMapping;

@LeshangController
public class WxUserController extends BaseController {

	@Autowired
	private WxUserService wxuserService;

	@PostMapping(value = "/admin/v1/wxuser/insert")
	@AutoReturn
	public void insertWxUser(WxUser wxuser) {
		wxuserService.insertWxUser(wxuser);
	}

	@PostMapping(value = "/admin/v1/wxuser/delete")
	@AutoReturn
	public void deleteWxUser(WxUser wxuser) {
		wxuserService.deleteWxUser(wxuser);
	}

	@PostMapping(value = "/admin/v1/wxuser/find")
	@AutoReturn
	public void findWxUser(WxUser wxuser) {
		this.setResult(wxuserService.findWxUser(wxuser));
	}

	@PostMapping(value = "/admin/v1/wxuser/update")
	@AutoReturn
	public void updateWxUser(WxUser wxuser) {
		wxuserService.updateWxUser(wxuser);
	}

	@PostMapping(value = "/admin/v1/wxuser/list")
	@AutoReturn
	public void listWxUser(WxUser wxuser) {
		this.setResult(wxuserService.listWxUser(wxuser));
		this.setTotal(wxuserService.listWxUserCount(wxuser));
	}
}