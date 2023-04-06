package com.qiyuan.base2048.service.generated;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.DemoExpense;
import com.qiyuan.base2048.pojo.vo.generated.DemoExpenseScene4QueryVO;
import com.qiyuan.base2048.pojo.vo.generated.DemoExpenseScene4DataVO;
import com.qiyuan.base2048.pojo.vo.generated.DemoExpenseScene4CompleteTaskDataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 示例表（业务工作流）报销 剧情4服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:16:27
*/
public interface IDemoExpenseScene4Service extends IService<DemoExpense> {
  ResultDTO getPage(DemoExpenseScene4QueryVO scene4QueryVO, boolean includeDeleted) throws Exception;
  ResultDTO insertOrUpdate(DemoExpenseScene4DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO saveAndStartProcess(DemoExpenseScene4DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;

  ResultDTO updateBizDataAndCompleteTask(DemoExpenseScene4CompleteTaskDataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO getOne(String businessId) throws Exception;
}