package com.qiyuan.base2048.controller.oa;

import com.qiyuan.base2048.pojo.vo.oa.MainContentVO;
import com.qiyuan.base2048.service.oa.NotifyContentGetter;
import com.qiyuan.base2048.service.oa.NotifyContentSaver;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/notify/content")
@Api("正文")
public class NotifyContentController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private NotifyContentSaver notifyContentSaver;
    @Resource
    private NotifyContentGetter notifyContentGetter;

    @PostMapping("/save")
    @ApiOperation("保存一条通知通告的正文")
    public ResultDTO send(@Valid @RequestBody MainContentVO mainContentVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return notifyContentSaver.saveContent(mainContentVO.getNotifyGuid(),mainContentVO.getContent());
    }

    @GetMapping("/load")
    @ApiOperation("拉取一条通知通告的正文")
    public ResultDTO load(@RequestParam("notifyGuid") String notifyGuid) throws Exception {
        return notifyContentGetter.loadContent(notifyGuid);
    }
}
