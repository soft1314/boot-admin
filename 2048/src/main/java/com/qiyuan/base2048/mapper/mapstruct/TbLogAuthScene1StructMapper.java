package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbLogAuth;
import com.qiyuan.base2048.pojo.vo.save.TbLogAuthScene1DataVO;;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 用户认证日志表 Mapstruct 剧情1实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-6-1
*/
@Mapper
public interface TbLogAuthScene1StructMapper {
  TbLogAuthScene1StructMapper INSTANCE= Mappers.getMapper(TbLogAuthScene1StructMapper.class);
  TbLogAuth toTbLogAuth(TbLogAuthScene1DataVO vo);
}