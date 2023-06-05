package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.mapper.mybatis.entity.VTablesMaster;
import com.qiyuan.base2048.pojo.dto.freemarker.VueRouterMarker;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.service.component.FreeMarkerUtils;
import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.database.TableService;
import com.qiyuan.base2048.service.generator.VueRouterGenerator;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("Scene1VueRouterGeneratorImpl")
public class Scene1VueRouterGeneratorImpl implements VueRouterGenerator {
    @Autowired
    private FreeMarkerUtils freeMarkerUtils;
    @Resource
    private TableService tableService;

    @Override
    public ResultDTO generateCode(String tableName, TgProject tgProject) throws Exception {
        Date now = new Date();

        ResultDTO resultDTO = this.createMarker(tableName, now);
        if (resultDTO.isFailure()) {
            return resultDTO;
        }
        VueRouterMarker marker = (VueRouterMarker) resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("marker", marker);
        String src = freeMarkerUtils.generateSource(root, "scene1Router.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(src);
        sourceCodeVO.setFileName("");
        return ResultDTO.success(sourceCodeVO);
    }
    private ResultDTO createMarker(String tableName, Date now) throws Exception {
        VTablesMaster vTablesMaster = tableService.getOneByTableName(tableName.toUpperCase());
        if (vTablesMaster == null) {
            return ResultDTO.failureCustom(MessageUtils.get("table.not.found.error"));
        }

        VueRouterMarker marker = new VueRouterMarker();

        marker.setTableComment(vTablesMaster.getComments());
        marker.setTableNameLowercase(tableName.toLowerCase());
        marker.setTableNameLowercaseMinusSign(marker.getTableNameLowercase().replaceAll("_","-"));


        return ResultDTO.success(marker);
    }
}
