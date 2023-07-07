package com.qiyuan.base2048.controller.oa;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiyuan.base2048.mapper.mybatis.ToNotifyRecordMapper;
import com.qiyuan.base2048.pojo.vo.query.MyNotifyQueryVO;
import com.qiyuan.base2048.pojo.vo.query.NotifyEmpQueryVO;
import com.qiyuan.base2048.pojo.vo.result.NotifyResultVO;
import com.qiyuan.base2048.pojo.vo.result.UserNotifyRecordVO;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.GuidContainerVO;
import com.qiyuan.bautil.dto.GuidPageQueryContainerVO;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import com.qiyuan.bautil.util.UserTool;
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
@RequestMapping("/api/system/auth/notify/record")
@Api("通知通告发送与阅读记录")
public class NotifyRecordController {
    @Resource
    private FormValidator formValidator;
    @Resource
    private ToNotifyRecordMapper toNotifyRecordMapper;

    @PostMapping("/bynotify")
    @ApiOperation("按通知跟踪")
    public ResultDTO fetchByNotifyGuid(@Valid @RequestBody NotifyEmpQueryVO notifyEmpQueryVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        Page<UserNotifyRecordVO> page = new Page<>(notifyEmpQueryVO.getCurrentPage(), notifyEmpQueryVO.getPageSize());
        IPage<UserNotifyRecordVO> userNotifyRecordVOIPage = toNotifyRecordMapper.selectTbUserPageByNotifyGuid(page, notifyEmpQueryVO);
        return ResultDTO.success(userNotifyRecordVOIPage);
    }
    @PostMapping("/byuser")
    @ApiOperation("按通知跟踪")
    public ResultDTO fetchByUserGuid(@Valid @RequestBody MyNotifyQueryVO myNotifyQueryVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        BaseUser baseUser = UserTool.getBaseUser();
        myNotifyQueryVO.setUserGuid(baseUser.getUserGuid());

        Page<NotifyResultVO> page = new Page<>(myNotifyQueryVO.getCurrentPage(), myNotifyQueryVO.getPageSize());
        IPage<NotifyResultVO> userNotifyRecordVOIPage = toNotifyRecordMapper.selectTbUserPageByUserGuid(page, myNotifyQueryVO);
        return ResultDTO.success(userNotifyRecordVOIPage);
    }
}
