package com.qiyuan.base2048.controller.oa;

import com.qiyuan.base2048.pojo.vo.oa.NotifyAdmDivRelationshipVO;
import com.qiyuan.base2048.service.oa.NotifyDivRelationSaver;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
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
@RequestMapping("/api/system/auth/tr-notify-div")
@Api("通知通告按区划通知")
public class TrNotifyDivController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private NotifyDivRelationSaver notifyDivRelationSaver;

    @PostMapping("/save")
    @ApiOperation("保存某条通知需要通知到哪些区划")
    public ResultDTO saveTrNotifyDiv(@Valid @RequestBody NotifyAdmDivRelationshipVO notifyAdmDivRelationshipVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return notifyDivRelationSaver.saveTrNotifyAdmDiv(notifyAdmDivRelationshipVO);
    }
}
