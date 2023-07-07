package com.qiyuan.base2048.mapper.mapstruct;

import com.qiyuan.base2048.mapper.mybatis.entity.TbAttachment;
import com.qiyuan.bautil.dto.MinIoFileDTO;
import com.qiyuan.bautil.service.component.BooleanToCharFormat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {BooleanToCharFormat.class})
public interface TbAttachmentStructMapper {
    TbAttachmentStructMapper INSTANCE= Mappers.getMapper(TbAttachmentStructMapper.class);
    @Mapping(target ="name",source = "fileOriginName")
    MinIoFileDTO toMinIoFileDTO(TbAttachment entity);
    List<MinIoFileDTO> toMinIoFileDTOList(List<TbAttachment> entitys);
}
