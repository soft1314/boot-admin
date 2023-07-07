package com.qiyuan.base2048.service.oa.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.ToContent;
import com.qiyuan.base2048.service.mybatis.IToContentService;
import com.qiyuan.base2048.service.oa.NotifyContentSaver;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotifyContentSaverImpl implements NotifyContentSaver {
    @Resource
    private IToContentService iToContentService;

    @Override
    public ResultDTO saveContent(String notifyGuid, String content) throws Exception {
        ToContent toContent = new ToContent();
        toContent.setGuid(notifyGuid);
        toContent.setContent(content);
        toContent.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        toContent.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        if(iToContentService.saveOrUpdate(toContent)){
            return ResultDTO.success();
        }
        return ResultDTO.failureCustom("保存失败.");
    }
}
