package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.service.generator.StoryGenerator;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("Scene4StoryGeneratorImpl")
public class Scene4StoryGeneratorImpl implements StoryGenerator {
    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;

    @Override
    public ResultDTO story(String tableName) throws Exception {
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setFileName("");
        sourceCodeVO.setSourceCode("针对一个简单数据表【"+tableName+"】的，包括增、删、改、查以及工作流等功能的源代码。");
        return ResultDTO.success(sourceCodeVO);
    }
}
