package com.qiyuan.base2048.controller.oa;

import com.qiyuan.base2048.pojo.vo.query.DataPermissionQueryVO;
import com.qiyuan.base2048.pojo.vo.query.NotifyOrgQueryVO;
import com.qiyuan.base2048.pojo.vo.save.NotifyOrgVO;
import com.qiyuan.base2048.pojo.vo.save.UserOrgVO;
import com.qiyuan.base2048.service.oa.NotifyOrgRelationGetter;
import com.qiyuan.base2048.service.oa.NotifyOrgRelationSaver;
import com.qiyuan.base2048.service.permission.UserOrgRelationGetter;
import com.qiyuan.base2048.service.permission.UserOrgRelationSaver;
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
@RequestMapping("/api/system/auth/tr-notify-org")
@Api("通知通告按机构通知")
public class TrNotifyOrgController {
    @Autowired
    private FormValidator formValidator;
    @Resource
    private NotifyOrgRelationGetter notifyOrgRelationGetter;
    @Resource
    private NotifyOrgRelationSaver notifyOrgRelationSaver;

    @PostMapping("/granted/page")
    @ApiOperation("分页获取已添加的机构列表")
    public ResultDTO getGrantedTablePage(@Valid @RequestBody NotifyOrgQueryVO queryByGuidDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return notifyOrgRelationGetter.fetchGrantedPage(queryByGuidDTO);
    }
    @PostMapping("/grantable/page")
    @ApiOperation("分页获取待添加的机构列表")
    public ResultDTO getGrantableTablePage(@Valid @RequestBody NotifyOrgQueryVO queryByGuidDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return notifyOrgRelationGetter.fetchGrantablePage(queryByGuidDTO);
    }
    @PostMapping("/grant/one")
    @ApiOperation("添加一个拟通知机构")
    public ResultDTO grantOneDiv(@Valid @RequestBody NotifyOrgVO notifyOrgVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return notifyOrgRelationSaver.grantOneOrg(notifyOrgVO);
    }
    @PostMapping("/revoke/one")
    @ApiOperation("从拟通知机构列表中删除一个机构")
    public ResultDTO revokeOneDiv(@Valid @RequestBody NotifyOrgVO notifyOrgVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return notifyOrgRelationSaver.revokeOneOrg(notifyOrgVO);
    }
}
