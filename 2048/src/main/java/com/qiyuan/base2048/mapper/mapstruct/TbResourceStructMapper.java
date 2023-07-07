package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbResource;
import com.qiyuan.base2048.pojo.vo.eltable.MenuVO;
import com.qiyuan.base2048.pojo.vo.save.ResourceVO;
import com.qiyuan.bautil.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface TbResourceStructMapper {
    TbResourceStructMapper INSTANCE= Mappers.getMapper(TbResourceStructMapper.class);

    @Mappings({
            @Mapping(source = "guid", target = "id"),
            @Mapping(source = "uri", target = "srvName"),
            @Mapping(target = "show", constant = "true")
    })
    MenuDTO map2MenuDTO(TbResource tbResource);
    List<MenuDTO> map2MenuDtoList(List<TbResource> tbResources);

    TbResource map2TbResource(ResourceVO resourceVO);

    MenuVO map2MenuVo(TbResource tbResource);
    List<MenuVO> map2MenuVoList(List<TbResource> tbResources);
}
