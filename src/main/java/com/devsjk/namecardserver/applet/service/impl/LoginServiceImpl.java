package com.devsjk.namecardserver.applet.service.impl;

import com.devsjk.namecardserver.applet.service.LoginService;
import com.devsjk.namecardserver.bean.WxBean;
import com.devsjk.namecardserver.dao.WxUserDao;
import com.devsjk.namecardserver.dao.WxUserTokenDao;
import com.devsjk.namecardserver.model.WxUser;
import com.devsjk.namecardserver.model.WxUserToken;
import com.devsjk.namecardserver.utils.TokenUtil;
import com.devsjk.namecardserver.utils.wxUtils.WxConfig;
import com.devsjk.namecardserver.utils.wxUtils.WxRequestUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

/**
 * @Auther: zjp
 * @Date: 2020/9/25 21:59
 * @Description:
 */
@Service("appletLoginService")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private WxBean wxBean;

    @Autowired
    private WxUserDao wxUserDao;

    @Autowired
    private WxUserTokenDao wxUserTokenDao;

    @Override
    @Transactional
    public WxUser login(String code) {
        String loginStr= WxRequestUtil.makeGetRequest(MessageFormat.format(WxConfig.jscode2session,wxBean.getAppId(),wxBean.getAppSecret(),code));
        JSONObject loginJson=new JSONObject(loginStr);
        //WxUser wxUser=new WxUser(loginJson.getString("openid"),loginJson.getString("session_key"),loginJson.has("unionid")?loginJson.getString("unionid"):"");
        WxUser w=wxUserDao.findWxUserByOpenId(loginJson.getString("openid"));
        if(null==w){
            WxUser wxUser=new WxUser(loginJson.getString("openid"),loginJson.getString("session_key"),loginJson.has("unionid")?loginJson.getString("unionid"):"",0);
            wxUserDao.insertWxUser(wxUser);
            String accessToken= TokenUtil.makeToken();
            wxUserTokenDao.insertWxUserToken(new WxUserToken(wxUser.getId(),accessToken));
            wxUser.setAccessToken(accessToken);
            return wxUser;
        }else{
            w.setSessionKey(loginJson.getString("session_key"));
            wxUserDao.updateWxUser(w);
            String accessToken= TokenUtil.makeToken();
            wxUserTokenDao.insertWxUserToken(new WxUserToken(w.getId(),accessToken));
            w.setAccessToken(accessToken);
            return w;
        }
    }
}
