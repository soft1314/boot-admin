package com.qiyuan.base2048.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.TbOrganization;
import com.qiyuan.base2048.pojo.vo.query.TbOrganizationScene2QueryVO;
import com.qiyuan.base2048.pojo.vo.save.TbOrganizationScene2DataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 组织机构基础表 剧情2服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-29 12:20:06
*/
public interface ITbOrganizationScene2Service extends IService<TbOrganization> {
  ResultDTO getTop(TbOrganizationScene2QueryVO queryVO, boolean includeDeleted) throws Exception;
  ResultDTO getPage(TbOrganizationScene2QueryVO queryVO, boolean includeDeleted) throws Exception;
  ResultDTO getAllChilden(String guid) throws Exception;
  ResultDTO insertOrUpdate(TbOrganizationScene2DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
  ResultDTO getOneLevelNode(String code,String admDivCode) throws Exception;
}