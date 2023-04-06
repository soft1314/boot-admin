package com.qiyuan.base2048.controller.sysmanage;

import com.qiyuan.base2048.pojo.vo.query.RolePermissionQueryVO;
import com.qiyuan.base2048.pojo.vo.save.RoleUserVO;
import com.qiyuan.base2048.service.permission.RoleUserRelationGetter;
import com.qiyuan.base2048.service.permission.RoleUserRelationSaver;
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
@RequestMapping("/api/system/auth/tr-role-user")
@Api("角色权限之用户权限")
public class TrRoleUserController {
    @Autowired
    private FormValidator formValidator;
    @Resource
    private RoleUserRelationGetter roleUserRelationGetter;
    @Resource
    private RoleUserRelationSaver roleUserRelationSaver;

    @PostMapping("/granted/page")
    @ApiOperation("分页获取已赋权的用户列表")
    public ResultDTO getGrantedTablePage(@Valid @RequestBody RolePermissionQueryVO rolePermissionQueryVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return roleUserRelationGetter.fetchGrantedPage(rolePermissionQueryVO);
    }
    @PostMapping("/grantable/page")
    @ApiOperation("分页获取待赋权的用户列表")
    public ResultDTO getGrantableTablePage(@Valid @RequestBody RolePermissionQueryVO rolePermissionQueryVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return roleUserRelationGetter.fetchGrantablePage(rolePermissionQueryVO);
    }
    @PostMapping("/grant/one")
    @ApiOperation("分页获取待赋权的用户列表")
    public ResultDTO grantOneUser(@Valid @RequestBody RoleUserVO roleUserVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return roleUserRelationSaver.grantOneUser(roleUserVO);
    }
    @PostMapping("/revoke/one")
    @ApiOperation("从一个用户收回一个用户的权限")
    public ResultDTO revokeOneUser(@Valid @RequestBody RoleUserVO roleUserVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return roleUserRelationSaver.revokeOneUser(roleUserVO);
    }
}
