package com.qiyuan.base2048.service.generated.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiyuan.base2048.mapper.mapstruct.generated.DemoLeaveScene3StructMapper;
import com.qiyuan.base2048.mapper.mybatis.DemoLeaveMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.DemoLeave;
import com.qiyuan.base2048.pojo.vo.generated.DemoLeaveScene3CompleteTaskDataVO;
import com.qiyuan.base2048.pojo.vo.generated.DemoLeaveScene3StartProcessDataVO;
import com.qiyuan.base2048.service.generated.IDemoLeaveScene3Service;
import com.qiyuan.base2048.service.generated.IDemoLeaveScene3Transaction;
import com.qiyuan.bautil.constant.ConstantWorkflowVarKey;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.dto.WorkflowReviewResultVO;
import com.qiyuan.bautil.dto.WorkflowStartProcessDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import com.qiyuan.bautil.enums.WorkflowStatusEnum;
import com.qiyuan.bautil.util.WorkflowTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
/**
* <p>
    * 本文件自动生成，严禁人工修改
    * 示例表（基本工作流）请假 剧情3服务类
    * </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:13:43
*/
@Service
public class DemoLeaveScene3ServiceImpl extends ServiceImpl<DemoLeaveMapper, DemoLeave>  implements IDemoLeaveScene3Service {
  @Autowired
  private IDemoLeaveScene3Transaction globalTransaction;

  /**
  * 全局事务
  * @param dataVO
  * @param baseUser
  * @return
  * @throws Exception
  */
  @Override
  public ResultDTO insertBizDataAndStartProcess(DemoLeaveScene3StartProcessDataVO dataVO, BaseUser baseUser) throws Exception {
    DemoLeave entity = DemoLeaveScene3StructMapper.INSTANCE.toDemoLeave(dataVO);
    if (StringUtils.isBlank(entity.getGuid())) {
     entity = setInitialValueWhenInsert(entity, baseUser);
    }
    WorkflowStartProcessDTO startProcessDTO = this.getStartProcessDTO(entity, dataVO.getProcDefKey(),baseUser);
    ResultDTO resultDTO = globalTransaction.saveDemoLeaveAndStartProcess(entity,startProcessDTO);
    return resultDTO;
  }

  @Override
  public ResultDTO updateBizDataAndCompleteTask(DemoLeaveScene3CompleteTaskDataVO dataVO, BaseUser baseUser) throws Exception {
    DemoLeave entity = DemoLeaveScene3StructMapper.INSTANCE.toDemoLeave(dataVO.getData());
    WorkflowReviewResultVO workflowReviewResultVO = dataVO.getInfo();
    workflowReviewResultVO.setTitle(entity.getTitle());
    workflowReviewResultVO.setVars(this.genProcessVars(entity));
    ResultDTO resultDTO = globalTransaction.saveDemoLeaveAndCompleteTask(entity,workflowReviewResultVO);
    return resultDTO;
  }

  @Override
  public ResultDTO getOne(String businessId) throws Exception {
    DemoLeave entity = this.getById(businessId);
    DemoLeaveScene3StartProcessDataVO wfDemoScene3DataVO = DemoLeaveScene3StructMapper.INSTANCE.toDemoLeaveScene3StartProcessDataVO(entity);
    return ResultDTO.success(wfDemoScene3DataVO);
  }

  private DemoLeave setInitialValueWhenInsert(DemoLeave entity, BaseUser baseUser) throws Exception {
    entity.setDeleted(IsDeletedEnum.NOTDELETED.getStringValue());
    entity.setEnabled(IsEnabledEnum.ENABLED.getStringValue());
    entity.setVersion(1);
    entity.setFlowStatus(WorkflowStatusEnum.IN_PROGRESS.getValue());
    return entity;
  }

  private WorkflowStartProcessDTO getStartProcessDTO(DemoLeave entity, String procDefKey,BaseUser baseUser) {
    WorkflowStartProcessDTO startProcessDTO = new WorkflowStartProcessDTO();
    startProcessDTO.setProcDefKey(procDefKey);
    startProcessDTO.setBusinessTable(entity.getClass().getAnnotation(TableName.class).value());
    startProcessDTO.setBusinessId(entity.getGuid());
    startProcessDTO.setTitle(entity.getTitle());
    startProcessDTO.setVars(new HashMap<>());
    startProcessDTO.getVars().put(ConstantWorkflowVarKey.LAST_ASSIGNEE, WorkflowTool.genAssignee(baseUser));

    /** todo 视情放开下面注释行，或者修改，添加代码，添加流程变量，结合流程定义控制分支流程条件 **/
    //startProcessDTO.getVars().put(ConstantWorkflowVarKey.MONEY,entity.getMoney());
    startProcessDTO.getVars().put(ConstantWorkflowVarKey.DAYS,entity.getDays());
    return startProcessDTO;
  }
  /**
  * 生成审核任务时的变量，根据业务修改
  * @param entity
  * @return
  * @throws Exception
  */
  private Map genProcessVars(DemoLeave entity) throws Exception{
    Map map = new HashMap();
    /** todo 视情放开下面注释行，或者修改，添加代码，添加流程变量，结合流程定义控制分支流程条件 **/
    //    map.put(ConstantWorkflowVarKey.MONEY,entity.getMoney());
    map.put(ConstantWorkflowVarKey.DAYS,entity.getDays());
    return map;
  }
}
