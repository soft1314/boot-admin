package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbOrganization;
import com.qiyuan.base2048.pojo.vo.save.TbOrganizationScene2DataVO;
import com.qiyuan.bautil.dto.CascaderNode;
import com.qiyuan.bautil.service.component.BooleanToCharFormat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 组织机构基础表 Mapstruct 剧情2实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-29 12:19:59
*/
@Mapper(uses = {BooleanToCharFormat.class})
public interface TbOrganizationScene2StructMapper {
  TbOrganizationScene2StructMapper INSTANCE= Mappers.getMapper(TbOrganizationScene2StructMapper.class);

  TbOrganization toTbOrganization(TbOrganizationScene2DataVO vo);
  TbOrganizationScene2DataVO toDataVO(TbOrganization entity);
  List<TbOrganizationScene2DataVO> toDataVOList(List<TbOrganization> entitys);

  @Mappings({
          @Mapping(source = "code", target = "value"),
          @Mapping(source = "name", target = "label")
  })
  CascaderNode toCascaderNode(TbOrganization entity);
  List<CascaderNode> toCascaderNodeList(List<TbOrganization> entitys);
}