package com.qiyuan.base2048.service.generator;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.vo.eltable.ProjectVO;
import com.qiyuan.base2048.pojo.vo.query.ProjectQueryVO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface ProjectGetter {
    ResultDTO getPage(ProjectQueryVO projectQueryVO,boolean includeDeleted) throws Exception;
    TgProject getActiveProject() throws Exception;
}
