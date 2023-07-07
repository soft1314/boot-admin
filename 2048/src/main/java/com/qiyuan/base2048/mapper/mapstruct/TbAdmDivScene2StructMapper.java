package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbAdmDiv;
import com.qiyuan.base2048.pojo.vo.save.TbAdmDivScene2DataVO;
import com.qiyuan.base2048.pojo.vo.tree.TreeNode;
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
    * 行政区划表 Mapstruct 剧情2实体类转换
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-24
*/
@Mapper(uses = {BooleanToCharFormat.class})
public interface TbAdmDivScene2StructMapper {
  TbAdmDivScene2StructMapper INSTANCE= Mappers.getMapper(TbAdmDivScene2StructMapper.class);

  TbAdmDiv toTbAdmDiv(TbAdmDivScene2DataVO vo);
  TbAdmDivScene2DataVO toDataVO(TbAdmDiv entity);
  List<TbAdmDivScene2DataVO> toDataVOList(List<TbAdmDiv> entitys);

//  @Mappings({
//          @Mapping(source = "guid", target = "value"),
//          @Mapping(source = "admDivName", target = "label")
//  })
//  OptionNode toOptionNode(TbAdmDiv entity);
//  List<OptionNode> toOptionNodeList(List<TbAdmDiv> entitys);

  @Mappings({
          @Mapping(source = "admDivCode", target = "value"),
          @Mapping(source = "admDivName", target = "label")
  })
  CascaderNode toCascaderNode(TbAdmDiv entity);
  List<CascaderNode> toCascaderNodeList(List<TbAdmDiv> entitys);
  @Mappings({
    @Mapping(target = "label", expression = "java(entity.getAdmDivCode() + '_' + entity.getAdmDivName())"),
    @Mapping(target = "id", source = "guid"),
    @Mapping(target = "value", source = "admDivCode"),
    @Mapping(target = "leaf", source = "leaf")
  })
  TreeNode toTreeNode(TbAdmDiv entity);
  List<TreeNode> toTreeNodeList(List<TbAdmDiv> tbAdmDivs);
}