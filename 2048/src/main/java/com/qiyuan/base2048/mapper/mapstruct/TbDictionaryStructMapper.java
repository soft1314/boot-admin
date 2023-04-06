package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbAdmDiv;
import com.qiyuan.base2048.mapper.mybatis.entity.TbDictionary;

import com.qiyuan.base2048.pojo.vo.eltable.TbDictionaryVO;
import com.qiyuan.base2048.pojo.vo.result.TbDictionaryDataVO;
import com.qiyuan.bautil.dto.CascaderNode;
import com.qiyuan.bautil.dto.DictionaryNode;
import com.qiyuan.bautil.dto.OptionNode;
import com.qiyuan.bautil.service.component.BooleanToCharFormat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {BooleanToCharFormat.class})
public interface TbDictionaryStructMapper {
    TbDictionaryStructMapper INSTANCE= Mappers.getMapper(TbDictionaryStructMapper.class);

    @Mappings({
            @Mapping(source = "itemCode", target = "value"),
            @Mapping(source = "itemName", target = "label")
    })
    OptionNode map2OptionNode(TbDictionary tbDictionary);
    List<OptionNode> map2OptionNodeList(List<TbDictionary> tbDictionaries);
    @Mappings({
            @Mapping(source = "guid", target = "id"),
            @Mapping(source = "itemCode", target = "code"),
            @Mapping(source = "itemName", target = "name")
    })
    DictionaryNode map2DictionaryNode(TbDictionary tbDictionary);
    List<DictionaryNode> map2DictionaryNodeList(List<TbDictionary> tbDictionaries);


    TbDictionaryVO map2TbDictionaryVO(TbDictionary tbDictionary);
//    List<TbDictionary> map2TbDictionaryVOList(List<TbDictionary> tbDictionaries);

    TbDictionary map2TbDictionary(TbDictionaryVO tbDictionaryVO);

    TbDictionaryDataVO map2TbDictionaryDataVO(TbDictionary tbDictionary);
    List<TbDictionaryDataVO> map2TbDictionaryDataVOList(List<TbDictionary> tbDictionaries);

    @Mappings({
            @Mapping(source = "itemCode", target = "value"),
            @Mapping(source = "itemName", target = "label")
    })
    CascaderNode toCascaderNode(TbDictionary entity);
    List<CascaderNode> toCascaderNodeList(List<TbDictionary> entitys);
}
