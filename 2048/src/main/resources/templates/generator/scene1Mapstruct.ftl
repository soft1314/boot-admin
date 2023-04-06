package ${marker.packageUrl};

import ${marker.entityFullName};
import ${marker.dataVoFullName};;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * ${marker.tableComment} Mapstruct 剧情1实体类转换
    * </p>
*
* @author ${marker.author}
* @since ${.now}
*/
@Mapper
public interface ${marker.entityName}Scene1StructMapper {
  ${marker.entityName}Scene1StructMapper INSTANCE= Mappers.getMapper(${marker.entityName}Scene1StructMapper.class);
  ${marker.entityName} to${marker.entityName}(${marker.dataVoName} vo);
}