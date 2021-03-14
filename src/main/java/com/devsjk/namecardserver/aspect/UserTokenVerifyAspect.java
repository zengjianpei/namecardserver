package com.devsjk.namecardserver.aspect;

import com.devsjk.namecardserver.dao.WxUserDao;
import com.devsjk.namecardserver.dao.WxUserTokenDao;
import com.devsjk.namecardserver.model.WxUser;
import com.devsjk.namecardserver.model.WxUserToken;
import com.leshang.framework3.Exception.BusinessException;
import com.leshang.framework3.constant.StatusConstant;
import com.leshang.framework3.controller.BaseController;
import com.leshang.framework3.model.BaseModel;
import com.leshang.framework3.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@Aspect
@Order(1)
public class UserTokenVerifyAspect {

    private static final Logger logger= LoggerFactory.getLogger(UserTokenVerifyAspect.class);

    @Autowired
    private WxUserTokenDao wxUserTokenDao;

    @Autowired
    private WxUserDao wxUserDao;

    @Before("@annotation(userTokenVerify)")
    public void doBefore(JoinPoint jp, UserTokenVerify userTokenVerify) throws Throwable{

        //String accessToken =getAccessToken(jp);

        //获取accessToken,先从header获取，没有则从请求参数中获取，还没有就从数据流中获取
        String accessToken =((BaseController)jp.getTarget()).getHttpServletRequest().getHeader("accessToken");
        if(StringUtils.isBlank(accessToken)){
            accessToken=((BaseController)jp.getTarget()).getHttpServletRequest().getParameter("accessToken");
            if(StringUtils.isBlank(accessToken)){
                try{
                    if(null!=jp.getArgs()&&jp.getArgs().length!=0){
                        Field field=jp.getArgs()[0].getClass().getDeclaredField("accessToken");
                        field.setAccessible(true);
                        accessToken=String.valueOf(field.get(jp.getArgs()[0]));
                        field.setAccessible(false);
                    }
                }catch (NoSuchFieldException|IllegalAccessException e){
                    logger.error("获取accessToken失败");
                }
            }
        }
        if(StringUtils.isBlank(accessToken)){
            throw new BusinessException(StatusConstant.ACCESS_TOKEN_IS_BLANK);
        }
        //return accessToken;

        verify(jp,userTokenVerify,accessToken);

    }

    private void verify(JoinPoint jp, UserTokenVerify userTokenVerify, String accessToken) throws Throwable{
        //验证accessToken有效性
        WxUserToken wxUserToken=wxUserTokenDao.findWxUserTokenByAccessToken(accessToken);
        if(null==wxUserToken){
            throw new BusinessException(StatusConstant.ACCESS_TOKEN_INVALID);
        }
        if(DateUtils.dateTime1beforeDateTime2(wxUserToken.getValid(),wxUserToken.getCurTime())){
            throw new BusinessException(StatusConstant.ACCESS_TOKEN_EXPIRED);
        }
        Long wxUserId=wxUserToken.getWxUserId();

        wxUserTokenDao.updateUserTokenVaild(wxUserToken);

        WxUser wxUser=null;

        //将用户的userId存储到请求参数中
        for (Object param : jp.getArgs()) {
            if (null != param) {

                if (null == wxUser) {
                    //获取user的信息
                    wxUser = wxUserDao.findWxUser(new WxUser(wxUserId));
                }

                if (param instanceof BaseModel) {
                    BaseModel baseModel = (BaseModel) param;
                    baseModel.setAccessToken(accessToken);
                    baseModel.setVerifyUserId(String.valueOf(wxUserId));
                    baseModel.setVerifyUserName(wxUser.getNickname());
                    baseModel.setCreator(wxUser.getNickname());
                    baseModel.setCreatorId(String.valueOf(wxUserId));
                    baseModel.setUpdator(wxUser.getNickname());
                    baseModel.setUpdatorId(String.valueOf(wxUserId));
                } else {
                    try {
                        Field field = param.getClass().getDeclaredField("userId");
                        field.setAccessible(true);
                        field.set(param, String.valueOf(wxUserId));
                        field.setAccessible(false);

                        field = param.getClass().getDeclaredField("account");
                        field.setAccessible(true);
                        field.set(param, String.valueOf(wxUserId));
                        field.setAccessible(false);
                    } catch (NoSuchFieldException | IllegalAccessException e) {}
                }
            }
        }
    }
}
