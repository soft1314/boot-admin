package com.qiyuan.base2048.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.TbLogAuth;
import com.qiyuan.base2048.pojo.vo.query.TbLogAuthScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.save.TbLogAuthScene1DataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 用户认证日志表 剧情1服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
public interface ITbLogAuthScene1Service extends IService<TbLogAuth> {
  ResultDTO getPage(TbLogAuthScene1QueryVO scene1QueryVO, boolean includeDeleted) throws Exception;
  ResultDTO insertOrUpdate(TbLogAuthScene1DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
}