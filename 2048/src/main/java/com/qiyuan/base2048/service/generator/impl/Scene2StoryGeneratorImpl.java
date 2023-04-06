package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.service.generator.StoryGenerator;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("Scene2StoryGeneratorImpl")
public class Scene2StoryGeneratorImpl implements StoryGenerator {
    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;

    @Override
    public ResultDTO story(String tableName) throws Exception {
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setFileName("");
        sourceCodeVO.setSourceCode("生成一套符合本系统技术路线的，针对一个树形结构数据表【"+tableName+"】的，包括增、删、改、查等功能的源代码。数据展示使用EL-TABLE树形数据与懒加载模式。");
        return ResultDTO.success(sourceCodeVO);
    }
}
