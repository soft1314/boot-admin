package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.service.generator.StoryGenerator;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("Scene3StoryGeneratorImpl")
public class Scene3StoryGeneratorImpl implements StoryGenerator {
    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;

    @Override
    public ResultDTO story(String tableName) throws Exception {
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setFileName("");
        sourceCodeVO.setSourceCode("生成一套符合本系统工作流程要求的，针对数据表【"+tableName+"】的，内容包括工作流增加、修改、阅览表单（组件）的前后端源代码。");
        return ResultDTO.success(sourceCodeVO);
    }
}
