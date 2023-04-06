package com.qiyuan.base2048.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.TbAdmDiv;
import com.qiyuan.base2048.pojo.vo.query.TbAdmDivScene2QueryVO;
import com.qiyuan.base2048.pojo.vo.save.TbAdmDivScene2DataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 行政区划表 剧情2服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-24 8:56:35
*/
public interface ITbAdmDivScene2Service extends IService<TbAdmDiv> {
  ResultDTO getTop(TbAdmDivScene2QueryVO queryVO, boolean includeDeleted) throws Exception;
  ResultDTO getPage(TbAdmDivScene2QueryVO queryVO, boolean includeDeleted) throws Exception;
  ResultDTO getAllChilden(String guid) throws Exception;
  ResultDTO insertOrUpdate(TbAdmDivScene2DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
  ResultDTO getOneLevelNode(String code) throws Exception;
}