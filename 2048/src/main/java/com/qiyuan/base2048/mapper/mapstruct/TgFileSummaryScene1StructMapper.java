package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TgFileSummary;
import com.qiyuan.base2048.pojo.vo.save.TgFileSummaryScene1DataVO;;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 生成文件的摘要记录表 Mapstruct 剧情1实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-23 14:20:00
*/
@Mapper
public interface TgFileSummaryScene1StructMapper {
  TgFileSummaryScene1StructMapper INSTANCE= Mappers.getMapper(TgFileSummaryScene1StructMapper.class);
  TgFileSummary toTgFileSummary(TgFileSummaryScene1DataVO vo);
}