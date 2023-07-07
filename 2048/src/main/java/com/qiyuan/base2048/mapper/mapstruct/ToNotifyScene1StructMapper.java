package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.ToNotify;
import com.qiyuan.base2048.pojo.vo.save.ToNotifyScene1DataVO;;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 通知通告主表 Mapstruct 剧情1实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-5-8
*/
@Mapper
public interface ToNotifyScene1StructMapper {
  ToNotifyScene1StructMapper INSTANCE= Mappers.getMapper(ToNotifyScene1StructMapper.class);
  ToNotify toToNotify(ToNotifyScene1DataVO vo);
}