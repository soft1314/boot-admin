package com.qiyuan.base2048.service.oa.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.ToContent;
import com.qiyuan.base2048.service.mybatis.IToContentService;
import com.qiyuan.base2048.service.oa.NotifyContentGetter;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotifyContentGetterImpl implements NotifyContentGetter {
    @Resource
    private IToContentService iToContentService;

    @Override
    public ResultDTO loadContent(String notifyGuid) throws Exception {
        ToContent toContent = iToContentService.getById(notifyGuid);
        if(toContent != null){
            return ResultDTO.success(toContent.getContent());
        }
        return ResultDTO.success("无正文。");
    }
}
