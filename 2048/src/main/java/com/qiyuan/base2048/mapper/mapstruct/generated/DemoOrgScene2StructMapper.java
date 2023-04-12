package com.qiyuan.base2048.mapper.mapstruct.generated;

import com.qiyuan.base2048.mapper.mybatis.entity.DemoOrg;
import com.qiyuan.base2048.pojo.vo.generated.DemoOrgScene2DataVO;;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.qiyuan.bautil.service.component.BooleanToCharFormat;
import java.util.List;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（层次CRUD）单位表 Mapstruct 剧情2实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-4-12 22:26:50
*/
@Mapper(uses = {BooleanToCharFormat.class})
public interface DemoOrgScene2StructMapper {
  DemoOrgScene2StructMapper INSTANCE= Mappers.getMapper(DemoOrgScene2StructMapper.class);

  DemoOrg toDemoOrg(DemoOrgScene2DataVO vo);
  DemoOrgScene2DataVO toDataVO(DemoOrg entity);
  List<DemoOrgScene2DataVO> toDataVOList(List<DemoOrg> entitys);
}