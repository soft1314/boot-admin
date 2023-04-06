package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.vo.eltable.TgProjectVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TgProjectStructMapper {
    TgProjectStructMapper INSTANCE= Mappers.getMapper(TgProjectStructMapper.class);

    TgProject toTgProject(TgProjectVO tgProjectVO);
}
