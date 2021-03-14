package com.devsjk.namecardserver.admin.service;

import com.devsjk.namecardserver.model.WxUser;
import java.util.List;

public interface WxUserService {

	void insertWxUser(WxUser wxuser);

	void deleteWxUser(WxUser wxuser);

	WxUser findWxUser(WxUser wxuser);

	void updateWxUser(WxUser wxuser);

	List<WxUser> listWxUser(WxUser wxuser);

	Long listWxUserCount(WxUser wxuser);
}