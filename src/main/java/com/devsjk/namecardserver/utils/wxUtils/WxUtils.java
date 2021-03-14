package com.devsjk.namecardserver.utils.wxUtils;

import com.devsjk.namecardserver.bean.WxBean;
import com.fasterxml.jackson.databind.JsonNode;
import com.leshang.framework3.Exception.BusinessException;
import com.leshang.framework3.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * @Auther: zjp
 * @Date: 2020/11/18 22:48
 * @Description:
 */
public class WxUtils {

    //管理后台获取accessToken
    public static String getServerAccessToken(WxBean wxBean) throws BusinessException {
        String accessToken="";
        String url = MessageFormat.format(WxConfig.FUN_GET_ACCESS_TOKEN_URL,wxBean.getAppId(),wxBean.getAppSecret());
        String requestResult = WxRequestUtil.makeGetRequest(url);

        JsonNode object = null;
        try {
            object = JsonUtil.toJsonNode(requestResult);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (null!=object && null==object.get("errcode")){
            accessToken = object.get("access_token").textValue();
        }
        else throw new BusinessException(888,"微信api获取accessToken失败");

        return accessToken;
    }

    public static String getAppletQcode(String accessToken,String pageUrl){
        String url = MessageFormat.format(WxConfig.APPLET_QCODE_CREATE,accessToken);
        String requestResult = WxRequestUtil.makeXMLPostRequest(url,"{\"path\": \""+pageUrl+"\",\"width\": 430}");
        System.out.println(requestResult);
        return  requestResult;
    }

    public static byte[] getAppletQcodebyte(String accessToken,String pageUrl){
        String url = MessageFormat.format(WxConfig.APPLET_QCODE_CREATE_1,accessToken);
        byte[] qcodebyte = WxRequestUtil.makeXMLPostRequestByte(url,"{\"path\": \""+pageUrl+"\",\"width\": 430}");
        return  qcodebyte;
    }
}
