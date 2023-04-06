package com.qiyuan.base2048.service.generated;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.mapper.mybatis.entity.DemoOrg;
import com.qiyuan.base2048.pojo.vo.generated.DemoOrgScene2QueryVO;
import com.qiyuan.base2048.pojo.vo.generated.DemoOrgScene2DataVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;
/**
* <p>
* 本文件自动生成，严禁人工修改
* 示例表（层次CRUD）单位表 剧情2服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:11:31
*/
public interface IDemoOrgScene2Service extends IService<DemoOrg> {
  ResultDTO getTop(DemoOrgScene2QueryVO queryVO, boolean includeDeleted) throws Exception;
  ResultDTO getPage(DemoOrgScene2QueryVO queryVO, boolean includeDeleted) throws Exception;
  ResultDTO getAllChilden(String guid) throws Exception;
  ResultDTO insertOrUpdate(DemoOrgScene2DataVO dataVO, BaseUser baseUser) throws Exception;
  ResultDTO delete(String guid) throws Exception;
}