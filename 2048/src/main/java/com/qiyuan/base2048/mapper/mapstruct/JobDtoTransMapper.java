package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.quartz.dto.JobDTO;
import com.qiyuan.base2048.quartz.dto.JobUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobDtoTransMapper {
    JobDtoTransMapper INSTANCE = (JobDtoTransMapper) Mappers.getMapper(JobDtoTransMapper.class);

    JobUpdateDTO map(JobDTO var1);
}
