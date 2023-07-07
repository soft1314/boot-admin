package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.service.component.FreeMarkerUtils;
import com.qiyuan.base2048.service.generator.StoryGenerator;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("Scene3StoryGeneratorImpl")
public class Scene3StoryGeneratorImpl implements StoryGenerator {
    @Resource
    private FreeMarkerUtils freeMarkerUtils;

    @Override
    public ResultDTO story(String tableName) throws Exception {
        /** 生成源码 **/
        Map<String, Object> root = new HashMap<>();
        root.put("tableName", tableName);
        String source = freeMarkerUtils.generateSource(root, "story3.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setFileName("");
        sourceCodeVO.setSourceCode(source);
        return ResultDTO.success(sourceCodeVO);
    }
}
