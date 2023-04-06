package ${marker.packageUrl};

import com.baomidou.mybatisplus.extension.service.IService;
import ${marker.entityPackageUrl}.${marker.entityName};
import ${marker.voPackageUrl}.${marker.entityName}Scene1QueryVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene1DataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* ${marker.tableComment} 剧情1服务类
* </p>
*
* @author ${marker.author}
* @since ${.now}
*/
public interface I${marker.entityName}Scene1Service extends IService<${marker.entityName}> {
  ResultDTO getPage(${marker.entityName}Scene1QueryVO scene1QueryVO, boolean includeDeleted) throws Exception;
  ResultDTO insertOrUpdate(${marker.entityName}Scene1DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
}