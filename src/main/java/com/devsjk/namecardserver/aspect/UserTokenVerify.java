package com.devsjk.namecardserver.aspect;

import java.lang.annotation.*;

/**
 * @Author:zjp
 * @Date:2018/10/09 0015
 * @Project:chargeBox
 * @Description:用户权限校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserTokenVerify {
    //uri 访问权限校验控制，默认为必需校验
    boolean uriCheck() default true;
}
