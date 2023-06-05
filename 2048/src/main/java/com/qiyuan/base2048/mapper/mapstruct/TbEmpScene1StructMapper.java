package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbEmp;
import com.qiyuan.base2048.pojo.vo.save.TbEmpScene1DataVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 人员表 Mapstruct 剧情1实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-12-5
*/
@Mapper
public interface TbEmpScene1StructMapper {
  TbEmpScene1StructMapper INSTANCE= Mappers.getMapper(TbEmpScene1StructMapper.class);
  TbEmp toTbEmp(TbEmpScene1DataVO vo);
}