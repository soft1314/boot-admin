package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.TrRoleUserMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleResource;
import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleUser;
import com.qiyuan.base2048.pojo.vo.save.RoleUserVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.permission.RoleUserRelationSaver;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleUserRelationSaverImpl implements RoleUserRelationSaver {
    @Resource
    private TrRoleUserMapper trRoleUserMapper;

    @Override
    public ResultDTO grantOneUser(RoleUserVO roleUserVO) throws Exception {
        QueryWrapper<TrRoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_GUID",roleUserVO.getUserGuid())
                .eq("ROLE_GUID",roleUserVO.getRoleGuid())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        if (trRoleUserMapper.selectCount(queryWrapper) > 0){
            return ResultDTO.failureCustom(MessageUtils.get("permission.granted.already.error"));
        }
        TrRoleUser trRoleUser = new TrRoleUser();
        trRoleUser.setRoleGuid(roleUserVO.getRoleGuid());
        trRoleUser.setUserGuid(roleUserVO.getUserGuid());
        trRoleUser.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        trRoleUser.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());

        int row = trRoleUserMapper.insert(trRoleUser);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("power.save.error"));
        }
    }

    @Override
    public ResultDTO revokeOneUser(RoleUserVO roleUserVO) throws Exception {
        UpdateWrapper<TrRoleUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ROLE_GUID",roleUserVO.getRoleGuid())
                .eq("USER_GUID",roleUserVO.getUserGuid())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .set("ENABLED",IsEnabledEnum.NOTENABLED.getStringValue())
                .set("DELETED",IsDeletedEnum.DELETED.getStringValue());
        int row = trRoleUserMapper.update(null,updateWrapper);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("power.save.error"));
        }
    }
}
