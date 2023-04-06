package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbRole;
import com.qiyuan.base2048.pojo.vo.save.TbRoleScene1DataVO;;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 系统角色基础表 Mapstruct 剧情1实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-12-14 21:12:36
*/
@Mapper
public interface TbRoleScene1StructMapper {
  TbRoleScene1StructMapper INSTANCE= Mappers.getMapper(TbRoleScene1StructMapper.class);
  TbRole toTbRole(TbRoleScene1DataVO vo);
}