package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.mapper.mybatis.entity.VTablesMaster;
import com.qiyuan.base2048.pojo.dto.freemarker.ControllerMarker;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.service.Component.FreeMarkerUtils;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.database.TableService;
import com.qiyuan.base2048.service.generator.ControllerGenerator;
import com.qiyuan.base2048.service.generator.GeneratorInfoAdapter;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.util.StringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


@Service("Scene2ControllerGeneratorImpl")
public class Scene2ControllerGeneratorImpl implements ControllerGenerator {
    @Autowired
    private FreeMarkerUtils freeMarkerUtils;
    @Resource
    private TableService tableService;
    @Resource
    private GeneratorInfoAdapter generatorInfoAdapter;

    @Override
    public ResultDTO generateFile(String tableName, TgProject tgProject,boolean overwrite) throws Exception {
        String path = generatorInfoAdapter.getSceneControllerFilePath(tgProject);
        if(!overwrite) {
            if (isFileAlreadyExist(tableName, path)) {
                return ResultDTO.failure(ResultStatus.FUNCTION_ALREADY_EXIST_ERROR);
            }
        }
        ResultDTO resultDTO = this.createMarker(tableName, tgProject);
        if (resultDTO.isFailure()) {
            return resultDTO;
        }
        ControllerMarker controllerMarker = (ControllerMarker) resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("marker", controllerMarker);
        freeMarkerUtils.generate(root, "scene2Controller.ftl", path, this.getFileName(tableName));
        return ResultDTO.success();
    }

    @Override
    public ResultDTO generateCode(String tableName, TgProject tgProject) throws Exception {
        String path = generatorInfoAdapter.getSceneControllerFilePath(tgProject);
        ResultDTO resultDTO = this.createMarker(tableName, tgProject);
        if (resultDTO.isFailure()) {
            return resultDTO;
        }
        ControllerMarker controllerMarker = (ControllerMarker) resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("marker", controllerMarker);
        String src = freeMarkerUtils.generateSource(root, "scene2Controller.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(src);
        sourceCodeVO.setFileName(this.getFileFullName(tableName, path));
        return ResultDTO.success(sourceCodeVO);
    }

    private boolean isFileAlreadyExist(String tableName, String path) throws Exception {
        File file = new File(this.getFileFullName(tableName, path));
        return file.exists();
    }

    private String getClassName(String tableName) {
        return StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName)) + "Scene2Controller";
    }

    private String getFileName(String tableName) {
        return this.getClassName(tableName) + ".java";
    }

    private String getFileFullName(String tableName, String path) throws Exception {
        return path + File.separator + this.getFileName(tableName);
    }

    private ResultDTO createMarker(String tableName, TgProject tgProject) throws Exception {
        VTablesMaster vTablesMaster = tableService.getOneByTableName(tableName.toUpperCase());
        if (vTablesMaster == null) {
            return ResultDTO.failureCustom(MessageUtils.get("table.not.found.error"));
        }

        ControllerMarker controllerMarker = new ControllerMarker();
        controllerMarker.setAuthor(tgProject.getAuthor());
        controllerMarker.setTableComment(vTablesMaster.getComments());
        controllerMarker.setPackageUrl(tgProject.getPackageControllerSc());
        controllerMarker.setEntityName(StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName)));
        controllerMarker.setVoPackageUrl(tgProject.getPackageVo());
        controllerMarker.setServicePackageUrl(tgProject.getPackageServiceSc());
        controllerMarker.setTableNameLowercase(tableName.toLowerCase());
        controllerMarker.setTableNameLowercaseMinusSign(tableName.toLowerCase().replaceAll("_","-"));

        return ResultDTO.success(controllerMarker);
    }
}
