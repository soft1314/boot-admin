package com.qiyuan.base2048.controller.sysmanage;

import com.qiyuan.base2048.mapper.mybatis.TrUserAdmdivMapper;
import com.qiyuan.base2048.pojo.vo.query.DataPermissionQueryVO;
import com.qiyuan.base2048.pojo.vo.query.TbEmpScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.save.UserDivVO;
import com.qiyuan.base2048.service.permission.UserAdmDivRelationGetter;
import com.qiyuan.base2048.service.permission.UserAdmDivRelationSaver;
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
@RequestMapping("/api/system/auth/tr-user-admdiv")
@Api("用户数据权限之区划权限")
public class TrUserAdmDivController {
    @Autowired
    private FormValidator formValidator;
    @Resource
    private UserAdmDivRelationGetter userAdmDivRelationGetter;
    @Resource
    private UserAdmDivRelationSaver userAdmDivRelationSaver;

    @PostMapping("/granted/page")
    @ApiOperation("分页获取已赋权的区划列表")
    public ResultDTO getGrantedTablePage(@Valid @RequestBody DataPermissionQueryVO queryByGuidDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return userAdmDivRelationGetter.fetchGrantedPage(queryByGuidDTO);
    }
    @PostMapping("/grantable/page")
    @ApiOperation("分页获取待赋权的区划列表")
    public ResultDTO getGrantableTablePage(@Valid @RequestBody DataPermissionQueryVO queryByGuidDTO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return userAdmDivRelationGetter.fetchGrantablePage(queryByGuidDTO);
    }
    @PostMapping("/grant/one")
    @ApiOperation("分页获取待赋权的区划列表")
    public ResultDTO grantOneDiv(@Valid @RequestBody UserDivVO userDivVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return userAdmDivRelationSaver.grantOneDiv(userDivVO);
    }
    @PostMapping("/revoke/one")
    @ApiOperation("从一个用户收回一个区划的数据权限")
    public ResultDTO revokeOneDiv(@Valid @RequestBody UserDivVO userDivVO, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            return formValidator.generateMessage(bindingResult);
        }
        return userAdmDivRelationSaver.revokeOneDiv(userDivVO);
    }
}
