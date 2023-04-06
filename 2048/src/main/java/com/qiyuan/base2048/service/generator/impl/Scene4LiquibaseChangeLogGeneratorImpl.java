package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.mapper.mybatis.entity.VTablesMaster;
import com.qiyuan.base2048.pojo.dto.freemarker.LiquibaseMarker;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.service.Component.FreeMarkerUtils;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.database.TableService;
import com.qiyuan.base2048.service.generator.LiquibaseChangeLogGenerator;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.util.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("Scene4LiquibaseChangeLogGeneratorImpl")
public class Scene4LiquibaseChangeLogGeneratorImpl implements LiquibaseChangeLogGenerator {
    @Autowired
    private FreeMarkerUtils freeMarkerUtils;
    @Resource
    private TableService tableService;

    @Override
    public ResultDTO generateFile(String tableName, TgProject tgProject) throws Exception {
        Date now = new Date();
        String fileName = this.createChangeLogFileName(now);
        if (isFileAlreadyExist(fileName, tgProject.getChangeLogDir())) {
            return ResultDTO.failure(ResultStatus.FUNCTION_ALREADY_EXIST_ERROR);
        }

        ResultDTO resultDTO = this.createMarker(tableName, now);
        if (resultDTO.isFailure()) {
            return resultDTO;
        }
        LiquibaseMarker liquibaseMarker = (LiquibaseMarker) resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("marker", liquibaseMarker);
        freeMarkerUtils.generate(root, "scene4Liquibase.ftl", tgProject.getChangeLogDir(), fileName);
        return ResultDTO.success();
    }

    @Override
    public ResultDTO generateCode(String tableName, TgProject tgProject) throws Exception {
        Date now = new Date();
        String fileName = this.createChangeLogFileName(now);

        ResultDTO resultDTO = this.createMarker(tableName, now);
        if (resultDTO.isFailure()) {
            return resultDTO;
        }
        LiquibaseMarker liquibaseMarker = (LiquibaseMarker) resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("marker", liquibaseMarker);
        String src = freeMarkerUtils.generateSource(root, "scene4Liquibase.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(src);
        sourceCodeVO.setFileName(tgProject.getChangeLogDir()+File.separator+fileName);
        return ResultDTO.success(sourceCodeVO);
    }
    private String createChangeSetId(Date now) throws Exception{
        /** yyyymmdd-hh24mmss **/
        return TimeTool.stringOfDateTime(now,"yyyyMMdd-HHmmss");
    }
    private String createChangeLogFileName(Date now) throws Exception{
        return "changelog-"+this.createChangeSetId(now)+".xml";
    }
    private boolean isFileAlreadyExist(String fileName, String path) throws Exception {
        File file = new File(path+File.separator+fileName);
        return file.exists();
    }

    private ResultDTO createMarker(String tableName, Date now) throws Exception {
        VTablesMaster vTablesMaster = tableService.getOneByTableName(tableName.toUpperCase());
        if (vTablesMaster == null) {
            return ResultDTO.failureCustom(MessageUtils.get("table.not.found.error"));
        }

        LiquibaseMarker liquibaseMarker = new LiquibaseMarker();

        liquibaseMarker.setTableComment(vTablesMaster.getComments());
        liquibaseMarker.setTableNameLowercase(tableName.toLowerCase());
        liquibaseMarker.setTableNameLowercaseMinusSign(liquibaseMarker.getTableNameLowercase().replaceAll("_","-"));
        liquibaseMarker.setChangeSetId(this.createChangeSetId(now));

        return ResultDTO.success(liquibaseMarker);
    }
}
