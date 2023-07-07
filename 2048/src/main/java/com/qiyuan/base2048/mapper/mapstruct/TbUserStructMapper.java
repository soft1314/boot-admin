package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbUser;
import com.qiyuan.base2048.pojo.vo.save.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TbUserStructMapper {
    TbUserStructMapper INSTANCE= Mappers.getMapper(TbUserStructMapper.class);

    TbUser map2TbUser(UserVO userVO);

}
