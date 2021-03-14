package com.devsjk.namecardserver.applet.controller;

import com.devsjk.namecardserver.applet.service.LoginService;
import com.leshang.framework3.annotation.AutoReturn;
import com.leshang.framework3.annotation.LeshangController;
import com.leshang.framework3.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: zjp
 * @Date: 2020/9/25 21:55
 * @Description:
 */
@LeshangController("appletLoginController")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @AutoReturn
    @PostMapping("/applet/v1/login")
    public void login(String code){
        this.setResult(loginService.login(code));
    }

    @AutoReturn
    @GetMapping("/applet/v1/test")
    public void test(){
        this.setResult("123");
    }

    @AutoReturn
    @PostMapping("/applet/v1/testPost")
    public void testPost(String s){
        this.setResult(s);
    }
}
