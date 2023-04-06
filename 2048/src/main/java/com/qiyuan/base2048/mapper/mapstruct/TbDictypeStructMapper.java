package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbDictype;
import com.qiyuan.base2048.pojo.vo.eltable.TbDictypeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TbDictypeStructMapper {
    TbDictypeStructMapper INSTANCE= Mappers.getMapper(TbDictypeStructMapper.class);
    TbDictypeVO toTbDictypeVO(TbDictype tbDictype);
    List<TbDictypeVO> toTbDictypeVOList(List<TbDictype> tbDictypeList);


    TbDictype toTbDictype(TbDictypeVO tbDictypeVO);
    List<TbDictype> toTbDictypeList(List<TbDictypeVO> tbDictypeVOList);
}
