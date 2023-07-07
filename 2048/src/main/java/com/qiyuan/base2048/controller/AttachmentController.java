package com.qiyuan.base2048.controller;

import com.qiyuan.base2048.pojo.vo.query.AttachmentQueryDTO;
import com.qiyuan.base2048.pojo.vo.query.DeleteAttachmentVO;
import com.qiyuan.base2048.service.common.AttachmentGetter;
import com.qiyuan.base2048.service.common.AttachmentSaver;
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
@RequestMapping("/api/system/auth/attachment")
@ResponseBodyInController
@Slf4j
@Api("附件相关")
public class AttachmentController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private AttachmentGetter attachmentGetter;
    @Resource
    private AttachmentSaver attachmentSaver;

    @PostMapping("/list")
    @ApiOperation("按主数据获取附件")
    public ResultDTO fetchAttachmentList(@Valid @RequestBody AttachmentQueryDTO attachmentDTO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return attachmentGetter.fetchAttachmentList(attachmentDTO.getMainTableName(),attachmentDTO.getMainStyle(),attachmentDTO.getMainGuid());
    }
    @PostMapping("/remove")
    @ApiOperation("删除一个附件记录")
    public ResultDTO removeOneAttachment(@Valid @RequestBody DeleteAttachmentVO deleteAttachmentVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        ResultDTO resultDTO = attachmentSaver.removeAttachment(deleteAttachmentVO.getAttachmentGuid(),deleteAttachmentVO.getFileName());
        return resultDTO;
    }

}
