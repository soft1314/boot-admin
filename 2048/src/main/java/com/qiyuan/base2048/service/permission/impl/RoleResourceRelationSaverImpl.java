package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.TrRoleResourceMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrRoleResource;
import com.qiyuan.base2048.pojo.vo.save.RoleResourceVO;
import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.permission.RoleResourceRelationSaver;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleResourceRelationSaverImpl implements RoleResourceRelationSaver {
    @Resource
    private TrRoleResourceMapper trRoleResourceMapper;

    @Override
    public ResultDTO grantOneResource(RoleResourceVO roleResourceVO) throws Exception {
        QueryWrapper<TrRoleResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("RESOURCE_GUID",roleResourceVO.getResourceGuid())
                .eq("ROLE_GUID",roleResourceVO.getRoleGuid())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        if (trRoleResourceMapper.selectCount(queryWrapper) > 0){
            return ResultDTO.failureCustom(MessageUtils.get("permission.granted.already.error"));
        }
        TrRoleResource trRoleResource = new TrRoleResource();
        trRoleResource.setRoleGuid(roleResourceVO.getRoleGuid());
        trRoleResource.setResourceGuid(roleResourceVO.getResourceGuid());
        trRoleResource.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        trRoleResource.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());

        int row = trRoleResourceMapper.insert(trRoleResource);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("power.save.error"));
        }
    }

    @Override
    public ResultDTO revokeOneResource(RoleResourceVO roleResourceVO) throws Exception {
        UpdateWrapper<TrRoleResource> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("ROLE_GUID",roleResourceVO.getRoleGuid())
                .eq("RESOURCE_GUID",roleResourceVO.getResourceGuid())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .set("ENABLED",IsEnabledEnum.NOTENABLED.getStringValue())
                .set("DELETED",IsDeletedEnum.DELETED.getStringValue());
        int row = trRoleResourceMapper.update(null,updateWrapper);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("power.save.error"));
        }
    }
}
