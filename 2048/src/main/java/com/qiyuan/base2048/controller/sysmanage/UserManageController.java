package com.qiyuan.base2048.controller.sysmanage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiyuan.base2048.mapper.mapstruct.TbUserStructMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.pojo.vo.query.UserPageQueryVO;
import com.qiyuan.base2048.pojo.vo.save.*;
import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.permission.RoleDataGetter;
import com.qiyuan.base2048.service.permission.RoleDataSaver;
import com.qiyuan.base2048.service.permission.UserDataGetter;
import com.qiyuan.base2048.service.permission.UserDataSaver;
import com.qiyuan.bautil.annotate.response.ResponseBodyInController;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.TransferDataDTO;
import com.qiyuan.bautil.service.FormValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.qiyuan.bautil.dto.GuidContainerVO;
import javax.validation.Valid;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-11-09 21:37
 **/
@RestController
@RequestMapping("/api/system/auth/manage/user")
@Slf4j
@ResponseBodyInController
@Api("系统用户管理相关的api")
public class UserManageController {
    @Autowired
    private FormValidator formValidator;
    @Autowired
    private UserDataGetter userDataGetter;
    @Autowired
    private UserDataSaver userDataSaver;
    @Autowired
    private RoleDataGetter roleDataGetter;
    @Autowired
    private RoleDataSaver roleDataSaver;

    @PostMapping("/page")
    @ApiOperation("分页查询用户")
    public ResultDTO getSysUserPage(@Valid @RequestBody(required = false) UserPageQueryVO userPageQueryVO, BindingResult bindingResult) throws Exception{
        if(userPageQueryVO == null){
            return ResultDTO.failureCustom("请点击【查询】按钮请求数据。");
        }
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        IPage<TbUser> tbUserIPage = userDataGetter.getPage(userPageQueryVO);
        return ResultDTO.success(tbUserIPage);
    }
    @ApiOperation("保存(添加或修改)用户")
    @PostMapping("/save")
    ResultDTO saveUser(@Valid @RequestBody UserVO userVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        TbUser tbUser = TbUserStructMapper.INSTANCE.map2TbUser(userVO);
        return userDataSaver.insertOrUpdateUser(tbUser);
    }
    @ApiOperation("通过GUID逻辑删除用户")
    @PostMapping("/del")
    ResultDTO delUser(@Valid @RequestBody GuidContainerVO guidContainerVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        userDataSaver.deleteUserByGuid(guidContainerVO.getGuid());
        return ResultDTO.successCustom(MessageUtils.get("user.save.success"),null);
    }
    @ApiOperation("按用户GUID查询角色穿梭框数据")
    @GetMapping("/role/transfer")
    ResultDTO loadUserRoleTransferData(@RequestParam("userGuid") String userGuid) throws Exception{
        TransferDataDTO transferDataDTO = roleDataGetter.getUserRoleTransferData(userGuid);
        return ResultDTO.success(transferDataDTO);
    }
    @ApiOperation("保存用户的角色权限数据")
    @PostMapping("/role/save")
    ResultDTO saveUserEmpInfo(@Valid @RequestBody UserRoleVO userRoleVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        roleDataSaver.saveUserPower(userRoleVO.getUserGuid(),userRoleVO.getUserRoleGuids());
        return ResultDTO.successCustom(MessageUtils.get("power.save.success"),null);
    }
    @ApiOperation("保存用户的隶属人员数据")
    @PostMapping("/emp/save")
    ResultDTO saveUserPower(@Valid @RequestBody UserEmpVO userEmpVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return userDataSaver.saveUserEmp(userEmpVO);
    }
    @ApiOperation("保存用户的区划数据权限类型")
    @PostMapping("/datapermission/admdiv/type/save")
    ResultDTO saveUserAdmDivPermissionType(@Valid @RequestBody UserDataPermissionTypeVO userDataPermissionTypeVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return userDataSaver.saveUserAdmDivDataPermissionType(userDataPermissionTypeVO);
    }
    @ApiOperation("保存用户的组织机构数据权限类型")
    @PostMapping("/datapermission/org/type/save")
    ResultDTO saveUserOrgPermissionType(@Valid @RequestBody UserDataPermissionTypeVO userDataPermissionTypeVO, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return userDataSaver.saveUserOrgDataPermissionType(userDataPermissionTypeVO);
    }
}
