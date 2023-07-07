package com.qiyuan.base2048.controller.sysmanage;

import com.qiyuan.bautil.dto.BasePageQueryVO;
import com.qiyuan.base2048.service.manage.OnlineUserService;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/system/auth/online/user")
@Slf4j
@ResponseBodyInController
@Api("系统在线用户相关的api")
public class OnlineUserController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private OnlineUserService onlineUserService;


    @PostMapping("/page")
    @ApiOperation("分页获取在线用户列表")
    public ResultDTO getTablePage(@Valid @RequestBody BasePageQueryVO basePageQueryVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return onlineUserService.pageOnlineUserVO(basePageQueryVO);
    }
}
