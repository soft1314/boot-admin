package ${marker.packageUrl};

import com.baomidou.mybatisplus.extension.service.IService;
import ${marker.entityPackageUrl}.${marker.entityName};
import ${marker.voPackageUrl}.${marker.entityName}Scene4QueryVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene4DataVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene4CompleteTaskDataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* ${marker.tableComment} 剧情4服务类
* </p>
*
* @author ${marker.author}
* @since ${.now}
*/
public interface I${marker.entityName}Scene4Service extends IService<${marker.entityName}> {
  ResultDTO getPage(${marker.entityName}Scene4QueryVO scene4QueryVO, boolean includeDeleted) throws Exception;
  ResultDTO insertOrUpdate(${marker.entityName}Scene4DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO saveAndStartProcess(${marker.entityName}Scene4DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;

  ResultDTO updateBizDataAndCompleteTask(${marker.entityName}Scene4CompleteTaskDataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO getOne(String businessId) throws Exception;
}