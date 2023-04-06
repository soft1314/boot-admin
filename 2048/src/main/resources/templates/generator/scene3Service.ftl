package ${marker.packageUrl};

import com.baomidou.mybatisplus.extension.service.IService;
import ${marker.entityPackageUrl}.${marker.entityName};
import ${marker.voPackageUrl}.${marker.entityName}Scene3CompleteTaskDataVO;
import ${marker.voPackageUrl}.${marker.entityName}Scene3StartProcessDataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* ${marker.tableComment} 剧情3服务类
* </p>
*
* @author ${marker.author}
* @since ${.now}
*/
public interface I${marker.entityName}Scene3Service extends IService<${marker.entityName}> {
  ResultDTO insertBizDataAndStartProcess(${marker.entityName}Scene3StartProcessDataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO updateBizDataAndCompleteTask(${marker.entityName}Scene3CompleteTaskDataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO getOne(String businessId) throws Exception;
}