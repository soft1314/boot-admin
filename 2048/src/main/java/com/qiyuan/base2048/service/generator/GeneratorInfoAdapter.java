package com.qiyuan.base2048.service.generator;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;

public interface GeneratorInfoAdapter {
    String getMpEntityFilePath(TgProject tgProject) throws Exception;
    String getMpMapperFilePath(TgProject tgProject) throws Exception;
    String getMpServiceFilePath(TgProject tgProject) throws Exception;
    String getMpControllerFilePath(TgProject tgProject) throws Exception;
    String getSceneServiceFilePath(TgProject tgProject) throws Exception;
    String getSceneControllerFilePath(TgProject tgProject) throws Exception;
    String getVoFilePath(TgProject tgProject) throws Exception;
    String getMapstructFilePath(TgProject tgProject) throws Exception;

}
