package com.qiyuan.base2048.service.permission;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.TbRole;
import com.qiyuan.base2048.pojo.vo.query.TbRoleScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.save.TbRoleScene1DataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 系统角色基础表 剧情1服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
public interface ITbRoleScene1Service extends IService<TbRole> {
  ResultDTO getPage(TbRoleScene1QueryVO scene1QueryVO, boolean includeDeleted) throws Exception;
  ResultDTO insertOrUpdate(TbRoleScene1DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
}