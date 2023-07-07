package com.qiyuan.base2048.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-10-29 16:36
 **/
@RestController
@RequestMapping({"/api/system/free/captcha"})
@Api("校验码")
public class CaptchaPublicController {
    @Autowired
    private CaptchaService captchaService;

    @PostMapping({"/get"})
    public ResponseModel get(@RequestBody CaptchaVO data, HttpServletRequest request) {
        assert request.getRemoteHost() != null;

        data.setBrowserInfo(getRemoteId(request));
        return this.captchaService.get(data);
    }

    @PostMapping({"/check"})
    public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
        data.setBrowserInfo(getRemoteId(request));
        return this.captchaService.check(data);
    }

    private static final String getRemoteId(HttpServletRequest request) {
        String xfwd = request.getHeader("X-Forwarded-For");
        String ip = getRemoteIpFromXfwd(xfwd);
        String ua = request.getHeader("user-agent");
        return StringUtils.isNotBlank(ip) ? ip + ua : request.getRemoteAddr() + ua;
    }
    private static String getRemoteIpFromXfwd(String xfwd) {
        if (StringUtils.isNotBlank(xfwd)) {
            String[] ipList = xfwd.split(",");
            return StringUtils.trim(ipList[0]);
        } else {
            return null;
        }
    }
}
