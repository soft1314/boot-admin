package com.qiyuan.base2048.service.generator;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.TgFileSummary;
import com.qiyuan.base2048.pojo.vo.query.TgFileSummaryScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.save.TgFileSummaryScene1DataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 生成文件的摘要记录表 剧情1服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2022-11-23 14:20:18
*/
public interface ITgFileSummaryScene1Service extends IService<TgFileSummary> {
  ResultDTO getPage(TgFileSummaryScene1QueryVO scene1QueryVO, boolean includeDeleted) throws Exception;
  ResultDTO insertOrUpdate(TgFileSummaryScene1DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
}