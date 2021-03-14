package com.devsjk.namecardserver.applet.service;

import com.devsjk.namecardserver.model.WxUser;

public interface WxUserService {

    void updateWxUser(WxUser wxUser);

    String getWxUserMobile(WxUser wxUser,String encryptedData,String iv);
}
