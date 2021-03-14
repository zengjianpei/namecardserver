package com.devsjk.namecardserver.applet.service.impl;

import com.devsjk.namecardserver.applet.service.WxUserService;
import com.devsjk.namecardserver.dao.WxUserDao;
import com.devsjk.namecardserver.model.WxUser;
import com.devsjk.namecardserver.utils.AESUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: zjp
 * @Date: 2020/9/28 11:55
 * @Description:
 */
@Service("appletWxUserService")
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private WxUserDao wxUserDao;

    @Override
    @Transactional
    public void updateWxUser(WxUser wxUser) {
        wxUser.setId(Long.valueOf(wxUser.getVerifyUserId()));
        wxUserDao.updateWxUser(wxUser);
    }

    @Override
    @Transactional
    public String getWxUserMobile(WxUser wxUser,String encryptedData, String iv) {
        wxUser.setId(Long.valueOf(wxUser.getVerifyUserId()));
        WxUser wu=wxUserDao.findWxUser(wxUser);
        try{
            String encryptedResult= AESUtils.getUserInfo(encryptedData,wu.getSessionKey(),iv);
            System.out.println(encryptedResult);
            if(StringUtils.isNotBlank(encryptedResult)){
                JSONObject jsonObject=new JSONObject(encryptedResult);
                if(jsonObject.has("phoneNumber")){
                    /*user.setUserCode(user.getVerifyUserId());;
                    user.setMobile(jsonObject.getString("phoneNumber"));
                    userDao.updateUserByUserCode(user);*/
                    String phoneNumber=jsonObject.getString("phoneNumber");
                    wu.setMobile(phoneNumber);
                    wxUserDao.updateWxUser(wu);
                    return jsonObject.getString("phoneNumber");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
