package com.qiyuan.base2048.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.TrUserAdmdivMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrUserAdmdiv;
import com.qiyuan.base2048.pojo.vo.save.UserDivVO;
import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.permission.UserAdmDivRelationSaver;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserAdmDivRelationSaverImpl implements UserAdmDivRelationSaver {
    @Resource
    private TrUserAdmdivMapper trUserAdmdivMapper;

    @Override
    public ResultDTO grantOneDiv(UserDivVO userDivVO) throws Exception {
        QueryWrapper<TrUserAdmdiv> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USER_GUID",userDivVO.getUserGuid())
                .eq("DIV_CODE",userDivVO.getDivCode())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        if (trUserAdmdivMapper.selectCount(queryWrapper) > 0){
            return ResultDTO.failureCustom(MessageUtils.get("permission.granted.already.error"));
        }
        TrUserAdmdiv trUserAdmdiv = new TrUserAdmdiv();
        trUserAdmdiv.setDivCode(userDivVO.getDivCode());
        trUserAdmdiv.setUserGuid(userDivVO.getUserGuid());
        trUserAdmdiv.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        trUserAdmdiv.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());

        int row = trUserAdmdivMapper.insert(trUserAdmdiv);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("power.save.error"));
        }
    }

    @Override
    public ResultDTO revokeOneDiv(UserDivVO userDivVO) throws Exception {
        UpdateWrapper<TrUserAdmdiv> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("USER_GUID",userDivVO.getUserGuid())
                .eq("DIV_CODE",userDivVO.getDivCode())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .set("ENABLED",IsEnabledEnum.NOTENABLED.getStringValue())
                .set("DELETED",IsDeletedEnum.DELETED.getStringValue());
        int row = trUserAdmdivMapper.update(null,updateWrapper);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("power.save.error"));
        }
    }
}
