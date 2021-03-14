package com.devsjk.namecardserver.admin.service.impl;

import com.devsjk.namecardserver.dao.WxUserDao;
import com.devsjk.namecardserver.model.WxUser;
import com.devsjk.namecardserver.admin.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WxUserServiceImpl implements WxUserService {

	@Autowired
	private WxUserDao wxuserDao;

	@Override
	public void insertWxUser(WxUser wxuser) {
		wxuserDao.insertWxUser(wxuser);
	}

	@Override
	public void deleteWxUser(WxUser wxuser) {
		wxuserDao.deleteWxUser(wxuser);
	}

	@Override
	public WxUser findWxUser(WxUser wxuser) {
		return wxuserDao.findWxUser(wxuser);
	}

	@Override
	public void updateWxUser(WxUser wxuser) {
		wxuserDao.updateWxUser(wxuser);
	}

	@Override
	public List<WxUser> listWxUser(WxUser wxuser) {
		return wxuserDao.listWxUser(wxuser);
	}

	@Override
	public Long listWxUserCount(WxUser wxuser) {
		return wxuserDao.listWxUserCount(wxuser);
	}
}