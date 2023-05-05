package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.pojo.dto.freemarker.VueMarker;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.service.Component.FreeMarkerUtils;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.generator.StoryGenerator;
import com.qiyuan.base2048.service.generator.TableChecker;
import com.qiyuan.bautil.dto.ResultDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("Scene1StoryGeneratorImpl")
public class Scene1StoryGeneratorImpl implements StoryGenerator {
    @Resource
    private FreeMarkerUtils freeMarkerUtils;

    @Override
    public ResultDTO story(String tableName) throws Exception {
        /** 生成源码 **/
        Map<String, Object> root = new HashMap<>();
        root.put("tableName", tableName);
        String source = freeMarkerUtils.generateSource(root, "story1.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setFileName("");
        sourceCodeVO.setSourceCode(source);
        return ResultDTO.success(sourceCodeVO);
    }
}
