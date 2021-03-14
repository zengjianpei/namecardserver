package com.devsjk.namecardserver.applet.service;

import com.devsjk.namecardserver.model.WxUser;

public interface LoginService {

    WxUser login(String code);
}
