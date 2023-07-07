package com.qiyuan.base2048.controller.sysmanage;

import com.qiyuan.base2048.pojo.vo.query.RolePermissionQueryVO;
import com.qiyuan.base2048.pojo.vo.save.RoleResourceVO;
import com.qiyuan.base2048.service.permission.RoleResourceRelationGetter;
import com.qiyuan.base2048.service.permission.RoleResourceRelationSaver;
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
@RequestMapping("/api/system/auth/tr-role-resource")
@Api("角色权限之资源权限")
public class TrRoleResourceController {
    @Autowired
    private FormValidator formValidator;
    @Resource
    private RoleResourceRelationGetter roleResourceRelationGetter;
    @Resource
    private RoleResourceRelationSaver roleResourceRelationSaver;

    @PostMapping("/granted/page")
    @ApiOperation("分页获取已赋权的资源列表")
    public ResultDTO getGrantedTablePage(@Valid @RequestBody RolePermissionQueryVO rolePermissionQueryVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return roleResourceRelationGetter.fetchGrantedPage(rolePermissionQueryVO);
    }
    @PostMapping("/grantable/page")
    @ApiOperation("分页获取待赋权的资源列表")
    public ResultDTO getGrantableTablePage(@Valid @RequestBody RolePermissionQueryVO rolePermissionQueryVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return roleResourceRelationGetter.fetchGrantablePage(rolePermissionQueryVO);
    }
    @PostMapping("/grant/one")
    @ApiOperation("分页获取待赋权的区划列表")
    public ResultDTO grantOneUser(@Valid @RequestBody RoleResourceVO roleResourceVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return roleResourceRelationSaver.grantOneResource(roleResourceVO);
    }
    @PostMapping("/revoke/one")
    @ApiOperation("从一个用户收回一个区划的数据权限")
    public ResultDTO revokeOneUser(@Valid @RequestBody RoleResourceVO roleResourceVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return roleResourceRelationSaver.revokeOneResource(roleResourceVO);
    }
}
