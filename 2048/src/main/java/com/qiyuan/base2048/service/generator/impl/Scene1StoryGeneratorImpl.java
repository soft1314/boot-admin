package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.generator.StoryGenerator;
import com.qiyuan.base2048.service.generator.TableChecker;
import com.qiyuan.bautil.dto.ResultDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("Scene1StoryGeneratorImpl")
public class Scene1StoryGeneratorImpl implements StoryGenerator {
    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;

    @Override
    public ResultDTO story(String tableName) throws Exception {
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setFileName("");
        sourceCodeVO.setSourceCode("生成一套符合本系统技术路线的，针对一个简单数据表【"+tableName+"】的，包括增、删、改、查等功能的源代码。");
        return ResultDTO.success(sourceCodeVO);
    }
}
