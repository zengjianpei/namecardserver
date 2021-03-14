package com.devsjk.namecardserver.applet.controller;

import com.devsjk.namecardserver.applet.service.WxUserService;
import com.devsjk.namecardserver.aspect.UserTokenVerify;
import com.devsjk.namecardserver.model.WxUser;
import com.leshang.framework3.Exception.BusinessException;
import com.leshang.framework3.annotation.AutoReturn;
import com.leshang.framework3.annotation.LeshangController;
import com.leshang.framework3.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: zjp
 * @Date: 2020/9/28 11:53
 * @Description:
 */
@LeshangController("appletWxUserController")
public class WxUserController extends BaseController {

    @Autowired
    private WxUserService wxUserService;

    @PostMapping("/applet/v1/wxUser/update")
    @UserTokenVerify
    @AutoReturn
    public void updateWxUser(WxUser wxUser) throws BusinessException {
        wxUserService.updateWxUser(wxUser);
    }

    @PostMapping("/applet/v1/wxUser/getWxUserMobile")
    @UserTokenVerify
    @AutoReturn
    public void getWxUserMobile(WxUser wxUser,String encryptedData,String iv){
        this.setResult(wxUserService.getWxUserMobile(wxUser,encryptedData,iv));
    }
}
