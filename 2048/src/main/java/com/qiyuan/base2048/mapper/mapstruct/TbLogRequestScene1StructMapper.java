package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbLogRequest;
import com.qiyuan.base2048.pojo.vo.save.TbLogRequestScene1DataVO;;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 访问日志表 Mapstruct 剧情1实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
@Mapper
public interface TbLogRequestScene1StructMapper {
  TbLogRequestScene1StructMapper INSTANCE= Mappers.getMapper(TbLogRequestScene1StructMapper.class);
  TbLogRequest toTbLogRequest(TbLogRequestScene1DataVO vo);
}