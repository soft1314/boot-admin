package com.qiyuan.base2048.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qiyuan.base2048.mapper.mybatis.TrNotifyOrgMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TrNotifyOrg;
import com.qiyuan.base2048.mapper.mybatis.entity.TrUserOrg;
import com.qiyuan.base2048.pojo.vo.save.NotifyOrgVO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.oa.NotifyOrgRelationSaver;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotifyOrgRelationSaverImpl implements NotifyOrgRelationSaver {
    @Resource
    private TrNotifyOrgMapper trNotifyOrgMapper;

    @Override
    public ResultDTO grantOneOrg(NotifyOrgVO notifyOrgVO) throws Exception {
        QueryWrapper<TrNotifyOrg> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("NOTIFY_GUID",notifyOrgVO.getNotifyGuid())
                .eq("ORG_GUID",notifyOrgVO.getOrgGuid())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue());
        if (trNotifyOrgMapper.selectCount(queryWrapper) > 0){
            return ResultDTO.failureCustom(MessageUtils.get("data.already.exist"));
        }
        TrNotifyOrg trNotifyOrg = new TrNotifyOrg();
        trNotifyOrg.setNotifyGuid(notifyOrgVO.getNotifyGuid());
        trNotifyOrg.setOrgGuid(notifyOrgVO.getOrgGuid());
        trNotifyOrg.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        trNotifyOrg.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());

        int row = trNotifyOrgMapper.insert(trNotifyOrg);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("dao.save.error"));
        }
    }

    @Override
    public ResultDTO revokeOneOrg(NotifyOrgVO notifyOrgVO) throws Exception {
        UpdateWrapper<TrNotifyOrg> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("NOTIFY_GUID",notifyOrgVO.getNotifyGuid())
                .eq("ORG_GUID",notifyOrgVO.getOrgGuid())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .set("ENABLED",IsEnabledEnum.NOTENABLED.getStringValue())
                .set("DELETED",IsDeletedEnum.DELETED.getStringValue());
        int row = trNotifyOrgMapper.update(new TrNotifyOrg(),updateWrapper);
        if(row == 1){
            return ResultDTO.success();
        }else{
            return ResultDTO.failureCustom(MessageUtils.get("dao.save.error"));
        }
    }
}
