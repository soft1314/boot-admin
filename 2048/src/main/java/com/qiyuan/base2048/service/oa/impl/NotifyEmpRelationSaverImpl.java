package com.qiyuan.base2048.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.TrNotifyEmpMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrNotifyEmp;
import com.qiyuan.base2048.pojo.vo.save.NotifyEmpVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.oa.NotifyEmpRelationSaver;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotifyEmpRelationSaverImpl implements NotifyEmpRelationSaver {
    @Resource
    private TrNotifyEmpMapper trNotifyEmpMapper;

    @Override
    public ResultDTO grantOneEmp(NotifyEmpVO notifyEmpVO) throws Exception {
        QueryWrapper<TrNotifyEmp> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("NOTIFY_GUID",notifyEmpVO.getNotifyGuid())
                .eq("EMP_GUID",notifyEmpVO.getEmpGuid())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        if (trNotifyEmpMapper.selectCount(queryWrapper) > 0){
            return ResultDTO.failureCustom(MessageUtils.get("data.already.exist"));
        }
        TrNotifyEmp trNotifyEmp = new TrNotifyEmp();
        trNotifyEmp.setNotifyGuid(notifyEmpVO.getNotifyGuid());
        trNotifyEmp.setEmpGuid(notifyEmpVO.getEmpGuid());
        trNotifyEmp.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        trNotifyEmp.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());

        int row = trNotifyEmpMapper.insert(trNotifyEmp);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("dao.save.error"));
        }
    }

    @Override
    public ResultDTO revokeOneEmp(NotifyEmpVO notifyEmpVO) throws Exception {
        UpdateWrapper<TrNotifyEmp> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("NOTIFY_GUID",notifyEmpVO.getNotifyGuid())
                .eq("EMP_GUID",notifyEmpVO.getEmpGuid())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .set("ENABLED",IsEnabledEnum.NOTENABLED.getStringValue())
                .set("DELETED",IsDeletedEnum.DELETED.getStringValue());
        int row = trNotifyEmpMapper.update(new TrNotifyEmp(),updateWrapper);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("dao.save.error"));
        }
    }
}
