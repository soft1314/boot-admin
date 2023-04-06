package com.qiyuan.bautil.util;

import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.JwtUserDTO;

import javax.servlet.http.HttpServletRequest;

public class UserTool {
    public static JwtUserDTO getUser(){
        HttpServletRequest httpServletRequest= HttpTool.getHttpServletRequest();
        if(httpServletRequest == null){
            return null;
        }
        return (JwtUserDTO) httpServletRequest.getAttribute(JwtUtil.AUTHORIZE_USER);
    }
    public static BaseUser getBaseUser(){
        HttpServletRequest httpServletRequest= HttpTool.getHttpServletRequest();
        if(httpServletRequest == null){
            return null;
        }
        return (BaseUser) httpServletRequest.getAttribute(JwtUtil.AUTHORIZE_USER);
    }
}
