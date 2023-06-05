package com.qiyuan.base2048.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.qiyuan.base2048.log.AuthLogSaver;
import com.qiyuan.base2048.service.LoginService;
import com.qiyuan.base2048.service.permission.ResourceDataGetter;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.*;
import com.qiyuan.bautil.enums.ResultTypeEnum;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.bautil.util.UserTool;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-14 9:39
 **/
@RestController
@ResponseBodyInController
@RequestMapping("/api/system")
@Slf4j
//@CrossOrigin
@Api("登录")
public class LoginController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private LoginService loginService;
    @Resource
    private ResourceDataGetter resourceDataGetter;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private AuthLogSaver authLogSaver;

    /**
     * 验证登录，返回令牌
     * @param loginForm
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("/free/user/login")
    public ResultDTO login(@Valid @RequestBody LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) throws Exception{
        /** 表单对象 VO 验证 **/
        if (bindingResult.hasErrors()) {
            ResultDTO resultDTO = formValidator.generateMessage(bindingResult);
            authLogSaver.saveLog(loginForm.getUsername(), ResultTypeEnum.FAILURE,resultDTO.getMessage());
            return resultDTO;
        }
        /** 校验码二次核对 **/
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(loginForm.getCaptchaVerification());
        ResponseModel response = captchaService.verification(captchaVO);
        if(response.isSuccess() == false){
            authLogSaver.saveLog(loginForm.getUsername(), ResultTypeEnum.FAILURE,response.getRepMsg());
            return ResultDTO.failureCustom(response.getRepMsg());
        }

        ResultDTO resultDTO = loginService.login(loginForm.getUsername(),loginForm.getPassword(),loginForm.getTenantId(),request);
        return resultDTO;
    }
    @GetMapping("/auth/user/info")
    public ResultDTO getUserInfo(HttpServletRequest request) throws Exception{
        BaseUser baseUser = UserTool.getBaseUser();
        authLogSaver.saveLog(baseUser.getLogonName(), ResultTypeEnum.SUCCESS,"用户请求自身UserInfo信息。");
        return loginService.getUserInfo(request);
    }
    @PostMapping("/auth/user/logout")
    public ResultDTO logOut() throws Exception{
        ResultDTO r = loginService.logOut();
        return r;
    }
    @GetMapping("/auth/user/menu")
    public List<MenuDTO> getMenus() throws Exception{
        BaseUser baseUser = UserTool.getBaseUser();
        List<MenuDTO> menuDTOList = resourceDataGetter.getMyselfMenuList(baseUser);
        authLogSaver.saveLog(baseUser.getLogonName(), ResultTypeEnum.SUCCESS,"用户请求菜单。");
        return menuDTOList;
    }
    @PostMapping("/auth/token/refresh")
    public ResultDTO refreshToken(@Valid @RequestBody SingleTokenVO singleTokenVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            ResultDTO resultDTO = formValidator.generateMessage(bindingResult);
            BaseUser baseUser = UserTool.getBaseUser();
            authLogSaver.saveLog(baseUser.getLogonName(), ResultTypeEnum.FAILURE,resultDTO.getMessage());
            return resultDTO;
        }
        ResultDTO r = loginService.refreshToken(singleTokenVO);
        return r;
    }
}

