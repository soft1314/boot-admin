package com.qiyuan.base2048.controller.oa;

import com.qiyuan.base2048.pojo.vo.query.NotifyEmpQueryVO;
import com.qiyuan.base2048.pojo.vo.save.NotifyEmpVO;
import com.qiyuan.base2048.service.oa.NotifyEmpRelationGetter;
import com.qiyuan.base2048.service.oa.NotifyEmpRelationSaver;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@ResponseBodyInController
@RequestMapping("/api/system/auth/tr-notify-emp")
@Api("通知通告按人员通知")
public class TrNotifyEmpController {
    @Autowired
    private FormValidator formValidator;
    @Resource
    private NotifyEmpRelationGetter notifyEmpRelationGetter;
    @Resource
    private NotifyEmpRelationSaver notifyEmpRelationSaver;

    @PostMapping("/granted/page")
    @ApiOperation("分页获取已添加的人员列表")
    public ResultDTO getGrantedTablePage(@Valid @RequestBody NotifyEmpQueryVO queryByGuidDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return notifyEmpRelationGetter.fetchGrantedPage(queryByGuidDTO);
    }
    @PostMapping("/grantable/page")
    @ApiOperation("分页获取待添加的人员列表")
    public ResultDTO getGrantableTablePage(@Valid @RequestBody NotifyEmpQueryVO queryByGuidDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return notifyEmpRelationGetter.fetchGrantablePage(queryByGuidDTO);
    }
    @PostMapping("/grant/one")
    @ApiOperation("添加一个拟通知人员")
    public ResultDTO grantOneDiv(@Valid @RequestBody NotifyEmpVO notifyEmpVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return notifyEmpRelationSaver.grantOneEmp(notifyEmpVO);
    }
    @PostMapping("/revoke/one")
    @ApiOperation("从拟通知人员列表中删除一个人员")
    public ResultDTO revokeOneDiv(@Valid @RequestBody NotifyEmpVO notifyEmpVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return notifyEmpRelationSaver.revokeOneEmp(notifyEmpVO);
    }
}
