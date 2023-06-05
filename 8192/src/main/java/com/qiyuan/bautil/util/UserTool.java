package com.qiyuan.bautil.util;

import com.qiyuan.bautil.dto.BaseUser;

import javax.servlet.http.HttpServletRequest;

public class UserTool {
    public static BaseUser getBaseUser(){
        HttpServletRequest httpServletRequest= HttpTool.getHttpServletRequest();
        if(httpServletRequest == null){
            return null;
        }
        return (BaseUser) httpServletRequest.getAttribute(JwtUtil.AUTHORIZE_USER);
    }
    public static String getOperator(){
        String operator = "系统";
        BaseUser baseUser = UserTool.getBaseUser();
        if(baseUser != null){
            operator = baseUser.getUserName()+"["+baseUser.getLogonName()+"]";
        }
        return operator;
    }
}
