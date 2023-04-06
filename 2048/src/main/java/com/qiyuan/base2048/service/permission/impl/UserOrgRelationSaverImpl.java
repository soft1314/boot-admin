package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.TrUserAdmdivMapper;
import com.qiyuan.base2048.mapper.mybatis.TrUserOrgMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrUserAdmdiv;
import com.qiyuan.base2048.mapper.mybatis.entity.TrUserOrg;
import com.qiyuan.base2048.pojo.vo.save.UserDivVO;
import com.qiyuan.base2048.pojo.vo.save.UserOrgVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.permission.UserOrgRelationSaver;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserOrgRelationSaverImpl implements UserOrgRelationSaver {
    @Resource
    private TrUserOrgMapper trUserOrgMapper;

    @Override
    public ResultDTO grantOneOrg(UserOrgVO userOrgVO) throws Exception {
        QueryWrapper<TrUserOrg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_GUID",userOrgVO.getUserGuid())
                .eq("ORG_CODE",userOrgVO.getOrgCode())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        if (trUserOrgMapper.selectCount(queryWrapper) > 0){
            return ResultDTO.failureCustom(MessageUtils.get("permission.granted.already.error"));
        }
        TrUserOrg trUserOrg = new TrUserOrg();
        trUserOrg.setOrgCode(userOrgVO.getOrgCode());
        trUserOrg.setUserGuid(userOrgVO.getUserGuid());
        trUserOrg.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        trUserOrg.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());

        int row = trUserOrgMapper.insert(trUserOrg);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("power.save.error"));
        }
    }

    @Override
    public ResultDTO revokeOneOrg(UserOrgVO userOrgVO) throws Exception {
        UpdateWrapper<TrUserOrg> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("USER_GUID",userOrgVO.getUserGuid())
                .eq("ORG_CODE",userOrgVO.getOrgCode())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .set("ENABLED",IsEnabledEnum.NOTENABLED.getStringValue())
                .set("DELETED",IsDeletedEnum.DELETED.getStringValue());
        int row = trUserOrgMapper.update(null,updateWrapper);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("power.save.error"));
        }
    }
}
