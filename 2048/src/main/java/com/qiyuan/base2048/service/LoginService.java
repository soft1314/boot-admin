package com.qiyuan.base2048.service;


import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.SingleTokenVO;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    /**
     * 用户登录处理
     */
    ResultDTO login(String userName, String password, String tenantId, HttpServletRequest request) throws Exception;
    ResultDTO getUserInfo(HttpServletRequest request) throws Exception;
    ResultDTO logOut() throws Exception;
    ResultDTO refreshToken(SingleTokenVO singleTokenVO) throws Exception;
}
