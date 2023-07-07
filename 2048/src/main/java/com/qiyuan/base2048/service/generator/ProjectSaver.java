package com.qiyuan.base2048.service.generator;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.bautil.dto.BaseUser;
import com.qiyuan.bautil.dto.ResultDTO;

public interface ProjectSaver {
    ResultDTO insertOrUpdateTgProject(TgProject tgProject, BaseUser baseUser) throws Exception;
    ResultDTO deleteTgProject(String guid) throws Exception;
    ResultDTO activeTgProject(String guid) throws Exception;
}
