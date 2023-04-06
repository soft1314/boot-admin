package com.qiyuan.base2048.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.TbEmp;
import com.qiyuan.base2048.pojo.vo.query.TbEmpScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.save.TbEmpScene1DataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 人员表 剧情1服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-12-5 16:40:32
*/
public interface ITbEmpScene1Service extends IService<TbEmp> {
  ResultDTO getPage(TbEmpScene1QueryVO scene1QueryVO, boolean includeDeleted) throws Exception;
  ResultDTO insertOrUpdate(TbEmpScene1DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
}