package com.qiyuan.base2048.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.ToNotify;
import com.qiyuan.base2048.pojo.vo.generated.ToNotifyScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.generated.ToNotifyScene1DataVO;
import com.qiyuan.base2048.pojo.vo.oa.NotifyAdmDivRelationshipVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 通知通告主表 剧情1服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-5-8 10:38:33
*/
public interface IToNotifyScene1Service extends IService<ToNotify> {
  ResultDTO getPage(ToNotifyScene1QueryVO scene1QueryVO, boolean includeDeleted) throws Exception;
  ResultDTO insertOrUpdate(ToNotifyScene1DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;


}