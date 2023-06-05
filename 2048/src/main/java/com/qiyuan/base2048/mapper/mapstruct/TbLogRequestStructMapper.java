package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbLogRequest;
import com.qiyuan.bautil.dto.SysOperLog;
import com.qiyuan.bautil.service.component.BooleanToCharFormat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {BooleanToCharFormat.class})
public interface TbLogRequestStructMapper extends BaseStructMapper{
    TbLogRequestStructMapper INSTANCE= Mappers.getMapper(TbLogRequestStructMapper.class);
    /**
     * 对象转换
     */
    @Mappings({
            @Mapping(source = "operUrl", target = "uri")
    })
    TbLogRequest map2TbLogRequest(SysOperLog sysOperLog);
}
