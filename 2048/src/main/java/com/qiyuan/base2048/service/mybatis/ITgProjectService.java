package com.qiyuan.base2048.service.mybatis;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qiyuan.base2048.pojo.vo.query.ProjectQueryVO;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;

/**
* <p>
* 代码生成功能项目定义表 服务类
* </p>
*
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*/
public interface ITgProjectService extends IService<TgProject> {
    ResultDTO getPage(ProjectQueryVO projectQueryVO, boolean includeDeleted) throws Exception;
    ResultDTO insertOrUpdateTgProject(TgProject tgProject, BaseUser baseUser) throws Exception;
    ResultDTO deleteTgProject(String guid) throws Exception;
}