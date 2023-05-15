package com.qiyuan.base2048.controller.oa;

import com.qiyuan.base2048.service.oa.NotifySender;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/notify")
@Api("按设置发送通知通告")
public class NotifySenderController {
    @Resource
    private FormValidator formValidator;

    @Resource
    private NotifySender notifySender;

    @PostMapping("/send")
    @ApiOperation("发布一条通知通告")
    public ResultDTO send(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return notifySender.send(guidContainerVO.getGuid());
    }
}
