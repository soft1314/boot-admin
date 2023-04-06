package com.qiyuan.base2048.controller.sysmanage;

import com.qiyuan.base2048.pojo.vo.query.DataPermissionQueryVO;
import com.qiyuan.base2048.pojo.vo.save.UserDivVO;
import com.qiyuan.base2048.pojo.vo.save.UserOrgVO;
import com.qiyuan.base2048.service.permission.UserAdmDivRelationGetter;
import com.qiyuan.base2048.service.permission.UserOrgRelationGetter;
import com.qiyuan.base2048.service.permission.UserOrgRelationSaver;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.QueryByGuidDTO;
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
@RequestMapping("/api/system/auth/tr-user-org")
@Api("用户数据权限之机构权限")
public class TrUserOrgController {
    @Autowired
    private FormValidator formValidator;
    @Resource
    private UserOrgRelationGetter userOrgRelationGetter;
    @Resource
    private UserOrgRelationSaver userOrgRelationSaver;

    @PostMapping("/granted/page")
    @ApiOperation("分页获取已赋权的区划列表")
    public ResultDTO getGrantedTablePage(@Valid @RequestBody DataPermissionQueryVO queryByGuidDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return userOrgRelationGetter.fetchGrantedPage(queryByGuidDTO);
    }
    @PostMapping("/grantable/page")
    @ApiOperation("分页获取待赋权的区划列表")
    public ResultDTO getGrantableTablePage(@Valid @RequestBody DataPermissionQueryVO queryByGuidDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return userOrgRelationGetter.fetchGrantablePage(queryByGuidDTO);
    }
    @PostMapping("/grant/one")
    @ApiOperation("分页获取待赋权的区划列表")
    public ResultDTO grantOneDiv(@Valid @RequestBody UserOrgVO userOrgVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return userOrgRelationSaver.grantOneOrg(userOrgVO);
    }
    @PostMapping("/revoke/one")
    @ApiOperation("从一个用户收回一个区划的数据权限")
    public ResultDTO revokeOneDiv(@Valid @RequestBody UserOrgVO userOrgVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return userOrgRelationSaver.revokeOneOrg(userOrgVO);
    }
}
