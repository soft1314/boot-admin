package com.qiyuan.base2048.service.generator;

import com.qiyuan.bautil.dto.ResultDTO;

public interface StoryGenerator {
    ResultDTO story(String tableName) throws Exception;
}
