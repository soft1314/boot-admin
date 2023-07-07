package com.qiyuan.base2048.log;

import com.qiyuan.base2048.mapper.mapstruct.TbLogRequestStructMapper;
import com.qiyuan.base2048.mapper.mybatis.TbLogRequestMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbLogRequest;
import com.qiyuan.bautil.dto.SysOperLog;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.service.SysOperLogAsyncSaver;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysOperLogAsyncSaverImpl implements SysOperLogAsyncSaver {
    @Resource
    private TbLogRequestMapper tbLogRequestMapper;

    @Override
    @Async
    public void asyncSave(SysOperLog operLog) {
        TbLogRequest tbLogRequest = TbLogRequestStructMapper.INSTANCE.map2TbLogRequest(operLog);
        tbLogRequest.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        tbLogRequest.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        tbLogRequestMapper.insert(tbLogRequest);
    }
}
