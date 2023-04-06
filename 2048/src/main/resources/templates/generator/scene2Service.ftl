package ${marker.packageUrl};

import com.baomidou.mybatisplus.extension.service.IService;
import ${marker.entityPackageUrl}.${marker.entityName};
import ${marker.voPackageUrl}.${marker.entityName}Scene2QueryVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene2DataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* ${marker.tableComment} 剧情2服务类
* </p>
*
* @author ${marker.author}
* @since ${.now}
*/
public interface I${marker.entityName}Scene2Service extends IService<${marker.entityName}> {
  ResultDTO getTop(${marker.entityName}Scene2QueryVO queryVO, boolean includeDeleted) throws Exception;
  ResultDTO getPage(${marker.entityName}Scene2QueryVO queryVO, boolean includeDeleted) throws Exception;
  ResultDTO getAllChilden(String guid) throws Exception;
  ResultDTO insertOrUpdate(${marker.entityName}Scene2DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
}