package com.qiyuan.log6144.service.impl;

import com.qiyuan.bautil.dto.KeyValuePair;
import com.qiyuan.bautil.dto.LogDTO;
import com.qiyuan.bautil.dto.LogInstant;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.log6144.mapper.mybatis.TbLogGeneralMapper;
import com.qiyuan.log6144.mapper.mybatis.entity.TbLogGeneral;
import com.qiyuan.log6144.service.LogSaver;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;

@Service
public class LogSaverImpl implements LogSaver {
    @Resource
    private TbLogGeneralMapper tbLogGeneralMapper;

    @Override
    public void saveLog(LogDTO logDTO) throws Exception {
        TbLogGeneral entity = this.genEntity(logDTO);
        tbLogGeneralMapper.insert(entity);
    }

    private TbLogGeneral genEntity(LogDTO logDTO) throws Exception{
        TbLogGeneral entity = new TbLogGeneral();
        entity.setLogLevel(logDTO.getLevel());
        entity.setLogContent(logDTO.getMessage());
        entity = genInstant(logDTO.getInstant(),entity);
        entity = genContextMap(logDTO.getContextMap(),entity);
        entity.setClassName(logDTO.getSource().getClassName()+":"+logDTO.getSource().getMethod());
        entity.setLineNo(logDTO.getSource().getLine().toString());
        entity.setHostName(logDTO.getHostName());
        entity.setCreateBy(entity.getOperator());
        entity.setModifyBy(entity.getOperator());
        entity.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
        entity.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
        entity.setVersion(0);

        return entity;
    }
    private TbLogGeneral genContextMap(KeyValuePair[] keyValuePairs,TbLogGeneral entity) throws Exception{
        for(KeyValuePair keyValuePair:keyValuePairs){
            if("Uid".equals(keyValuePair.getKey())){
                entity.setOperator(keyValuePair.getValue());
            }
            if("appName".equals(keyValuePair.getKey())){
                entity.setAppName(keyValuePair.getValue());
            }
            if("traceId".equals(keyValuePair.getKey())){
                entity.setTraceId(keyValuePair.getValue());
            }
            if("spanId".equals(keyValuePair.getKey())){
                entity.setSpanId(keyValuePair.getValue());
            }
            if("parentId".equals(keyValuePair.getKey())){
                entity.setParentId(keyValuePair.getValue());
            }
        }
        return entity;
    }
    private TbLogGeneral genInstant(LogInstant instant,TbLogGeneral entity) throws Exception{
        Instant nowInstant = Instant.ofEpochSecond(instant.getEpochSecond().longValue());
        Date now = Date.from(nowInstant);
        entity.setCreateTime(now);
        entity.setModifyTime(now);
        return entity;
    }
}
