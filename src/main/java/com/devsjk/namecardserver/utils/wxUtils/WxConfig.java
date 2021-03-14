package com.devsjk.namecardserver.utils.wxUtils;

/**
 * @Auther: zjp
 * @Date: 2020/9/25 23:08
 * @Description:
 */
public class WxConfig {

    public static String jscode2session="https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";

    //获取accessToken
    public static String FUN_GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";

    //小程序生成二维码
    public static String APPLET_QCODE_CREATE = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token={0}";

    //生成生成小程序码
    public static String APPLET_QCODE_CREATE_1 = "https://api.weixin.qq.com/wxa/getwxacode?access_token={0}";
}
