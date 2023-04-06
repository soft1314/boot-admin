package com.qiyuan.base2048.mapper.mapstruct;


import com.qiyuan.bautil.dto.DictionaryNode;
import com.qiyuan.bautil.dto.OptionNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OptionNodeStructMapper {
    OptionNodeStructMapper INSTANCE= Mappers.getMapper(OptionNodeStructMapper.class);

    /**
     * 对象转换
     * @param dictionaryNode
     * @return
     */
    @Mappings({
            @Mapping(source = "code", target = "value"),
            @Mapping(source = "name", target = "label")
    })
    OptionNode map2OptionNode(DictionaryNode dictionaryNode);
    List<OptionNode> map2OptionNodeList(List<DictionaryNode> dictionaryNodeList);
}
