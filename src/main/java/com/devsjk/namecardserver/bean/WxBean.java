package com.devsjk.namecardserver.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Auther: zjp
 * @Date: 2020/9/25 23:13
 * @Description:
 */
@Service
public class WxBean {

    @Value("${app_id}")
    private String appId;

    @Value("${app_secret}")
    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
