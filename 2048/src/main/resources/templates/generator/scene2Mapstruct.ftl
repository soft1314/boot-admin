package ${marker.packageUrl};

import ${marker.entityFullName};

import ${marker.dataSaveVoFullName};
import ${marker.dataRespVoFullName};
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.qiyuan.bautil.service.component.BooleanToCharFormat;
import java.util.List;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * ${marker.tableComment} Mapstruct 剧情2实体类转换
    * </p>
*
* @author ${marker.author}
* @since ${.now}
*/
@Mapper(uses = {BooleanToCharFormat.class})
public interface ${marker.entityName}Scene2StructMapper {
  ${marker.entityName}Scene2StructMapper INSTANCE= Mappers.getMapper(${marker.entityName}Scene2StructMapper.class);

  ${marker.entityName} to${marker.entityName}(${marker.entityName}Scene2SaveDataVO vo);

  ${marker.entityName}Scene2SaveDataVO toSaveDataVO(${marker.entityName} entity);
  List<${marker.entityName}Scene2SaveDataVO> toSaveDataVOList(List<${marker.entityName}> entitys);

  ${marker.entityName}Scene2RespDataVO toRespDataVO(${marker.entityName} entity);
  List<${marker.entityName}Scene2RespDataVO> toRespDataVOList(List<${marker.entityName}> entitys);
}