package com.qiyuan.log6144.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.log6144.mapper.mybatis.entity.TbLogGeneral;
import com.qiyuan.log6144.vo.TbLogGeneralScene1DataVO;
import com.qiyuan.log6144.vo.TbLogGeneralScene1QueryVO;

/**
* <p>
* 本文件自动生成，严禁人工修改
* 一般日志表 剧情1服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
public interface ITbLogGeneralScene1Service extends IService<TbLogGeneral> {
  ResultDTO getPage(TbLogGeneralScene1QueryVO scene1QueryVO, boolean includeDeleted) throws Exception;
  ResultDTO insertOrUpdate(TbLogGeneralScene1DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
}