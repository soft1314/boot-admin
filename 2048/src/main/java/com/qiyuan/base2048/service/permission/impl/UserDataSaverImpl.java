package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.pojo.vo.save.UserDataPermissionTypeVO;
import com.qiyuan.base2048.pojo.vo.save.UserEmpVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.mybatis.ITbUserService;
import com.qiyuan.base2048.service.permission.UserDataSaver;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.DataRangeEnum;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDataSaverImpl implements UserDataSaver {
    @Value("${base2048.user.password.default}")
    private String defaultPassword;
    @Value("${base2048.user.password.crypt}")
    private String pwdType;

    @Resource
    private ITbUserService iTbUserService;

    @Override
    public boolean updateUser(TbUser tbUser) throws Exception {
        return iTbUserService.updateById(tbUser);
    }

    @Override
    public boolean insertUser(TbUser tbUser) throws Exception {
        return iTbUserService.save(tbUser);
    }

    @Override
    public ResultDTO insertOrUpdateUser(TbUser tbUser) throws Exception {
        if(tbUser.getVersion() == null){
            tbUser.setVersion(0);
        }
        if(StringUtils.isBlank(tbUser.getGuid())){
            tbUser.setPwd(defaultPassword);
            tbUser.setPwdType(pwdType);
            tbUser.setDivRangeType(DataRangeEnum.SELF.getValue());
            tbUser.setOrgRangeType(DataRangeEnum.SELF.getValue());
            tbUser.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
            if(iTbUserService.save(tbUser)){
                return ResultDTO.success();
            }else {
                return ResultDTO.failureCustom(MessageUtils.get("user.save.error"));
            }
        }else{
            if(iTbUserService.updateById(tbUser)){
                return ResultDTO.success();
            }else {
                return ResultDTO.failureCustom(MessageUtils.get("user.save.error"));
            }
        }
    }

    @Override
    public boolean deleteUserByGuid(String guid) throws Exception {
        UpdateWrapper<TbUser> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("guid",guid).set("deleted", IsDeletedEnum.DELETED.getValue());
        return iTbUserService.update(updateWrapper);
    }

    @Override
    public ResultDTO saveUserEmp(UserEmpVO userEmpVO) throws Exception {
        UpdateWrapper<TbUser> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("GUID", userEmpVO.getUserGuid())
                .eq("VERSION", userEmpVO.getVersion())
                .set("EMP_GUID", userEmpVO.getEmpGuid())
                .set("FIRST_NAME", userEmpVO.getEmpName())
                .set("ADM_DIV_CODE", userEmpVO.getAdmDivCode())
                .set("ADM_DIV_NAME", userEmpVO.getAdmDivName())
                .set("ORG_CODE", userEmpVO.getOrgCode())
                .set("ORG_NAME", userEmpVO.getOrgName())
                .set("VERSION", userEmpVO.getVersion() + 1);
        if(iTbUserService.update(updateWrapper)){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("data.save.error"));
        }
    }

    @Override
    public ResultDTO saveUserAdmDivDataPermissionType(UserDataPermissionTypeVO userDataPermissionTypeVO) throws Exception {
        UpdateWrapper<TbUser> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("GUID", userDataPermissionTypeVO.getUserGuid())
                .set("DIV_RANGE_TYPE", userDataPermissionTypeVO.getPermissionType());

        if(iTbUserService.update(updateWrapper)){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("data.save.error"));
        }
    }

    @Override
    public ResultDTO saveUserOrgDataPermissionType(UserDataPermissionTypeVO userDataPermissionTypeVO) throws Exception {
        UpdateWrapper<TbUser> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("GUID", userDataPermissionTypeVO.getUserGuid())
                .set("ORG_RANGE_TYPE", userDataPermissionTypeVO.getPermissionType());

        if(iTbUserService.update(updateWrapper)){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("data.save.error"));
        }
    }
}
