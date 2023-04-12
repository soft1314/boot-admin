package com.qiyuan.base2048.service.generated;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.DemoLeave;
import com.qiyuan.base2048.pojo.vo.generated.DemoLeaveScene3CompleteTaskDataVO;
import com.qiyuan.base2048.pojo.vo.generated.DemoLeaveScene3StartProcessDataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 示例表（基本工作流）请假 剧情3服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-4-12 22:27:24
*/
public interface IDemoLeaveScene3Service extends IService<DemoLeave> {
  ResultDTO insertBizDataAndStartProcess(DemoLeaveScene3StartProcessDataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO updateBizDataAndCompleteTask(DemoLeaveScene3CompleteTaskDataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO getOne(String businessId) throws Exception;
}