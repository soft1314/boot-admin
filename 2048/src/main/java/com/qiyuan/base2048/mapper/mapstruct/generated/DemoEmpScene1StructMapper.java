package com.qiyuan.base2048.mapper.mapstruct.generated;

import com.qiyuan.base2048.mapper.mybatis.entity.DemoEmp;
import com.qiyuan.base2048.pojo.vo.generated.DemoEmpScene1DataVO;;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（基本CRUD）人员表 Mapstruct 剧情1实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
@Mapper
public interface DemoEmpScene1StructMapper {
  DemoEmpScene1StructMapper INSTANCE= Mappers.getMapper(DemoEmpScene1StructMapper.class);
  DemoEmp toDemoEmp(DemoEmpScene1DataVO vo);
}