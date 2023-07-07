package com.qiyuan.base2048.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.TbDroolsRule;
import com.qiyuan.base2048.pojo.vo.query.TbDroolsRuleScene1QueryVO;
import com.qiyuan.base2048.pojo.vo.save.TbDroolsRuleScene1DataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 系统规则表 剧情1服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
public interface ITbDroolsRuleScene1Service extends IService<TbDroolsRule> {
  ResultDTO getPage(TbDroolsRuleScene1QueryVO scene1QueryVO, boolean includeDeleted) throws Exception;
  ResultDTO insertOrUpdate(TbDroolsRuleScene1DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
}