package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.mapper.mybatis.entity.VTablesMaster;
import com.qiyuan.base2048.pojo.dto.freemarker.MapstructMarker;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.service.Component.FreeMarkerUtils;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.database.TableService;
import com.qiyuan.base2048.service.generator.GeneratorInfoAdapter;
import com.qiyuan.base2048.service.generator.MapstructGenerator;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.util.StringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
@Service("Scene1MapstructGeneratorImpl")
public class Scene1MapstructGeneratorImpl implements MapstructGenerator {
    @Autowired
    private FreeMarkerUtils freeMarkerUtils;
    @Resource
    private TableService tableService;
    @Resource
    private GeneratorInfoAdapter generatorInfoAdapter;

    @Override
    public ResultDTO generateFile(String tableName, boolean overwrite, TgProject tgProject) throws Exception {
        String path = generatorInfoAdapter.getMapstructFilePath(tgProject);
        if(!overwrite) {
            if (isFileAlreadyExist(tableName, path)) {
                return ResultDTO.failure(ResultStatus.FUNCTION_ALREADY_EXIST_ERROR);
            }
        }
        ResultDTO resultDTO = this.createMarker(tableName,tgProject);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        MapstructMarker mapstructMarker = (MapstructMarker)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("marker", mapstructMarker);
        freeMarkerUtils.generate(root, "scene1Mapstruct.ftl", path, this.getFileName(tableName));
        return ResultDTO.success();
    }

    @Override
    public ResultDTO generateCode(String tableName, TgProject tgProject) throws Exception {
        String path = generatorInfoAdapter.getMapstructFilePath(tgProject);
        ResultDTO resultDTO = this.createMarker(tableName,tgProject);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        MapstructMarker mapstructMarker = (MapstructMarker)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("marker", mapstructMarker);
        String src = freeMarkerUtils.generateSource(root, "scene1Mapstruct.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(src);
        sourceCodeVO.setFileName(this.getFileFullName(tableName,path));
        return ResultDTO.success(sourceCodeVO);
    }

    private boolean isFileAlreadyExist(String tableName, String path) throws Exception {
        File file = new File(this.getFileFullName(tableName,path));
        return file.exists();
    }
    private String getClassName(String tableName){
        return StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName))+"Scene1StructMapper";
    }
    private String getFileName(String tableName){
        return this.getClassName(tableName)+".java";
    }
    private String getFileFullName(String tableName, String path) throws Exception{
        return path+File.separator+this.getFileName(tableName);
    }
    private ResultDTO createMarker(String tableName,TgProject tgProject) throws Exception{
        VTablesMaster vTablesMaster = tableService.getOneByTableName(tableName.toUpperCase());
        if(vTablesMaster == null){
            return ResultDTO.failureCustom(MessageUtils.get("table.not.found.error"));
        }

        MapstructMarker mapstructMarker = new MapstructMarker();
        mapstructMarker.setPackageUrl(tgProject.getPackageMapstruct());
        mapstructMarker.setEntityName(StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName)));
        mapstructMarker.setAuthor(tgProject.getAuthor());
        mapstructMarker.setTableComment(vTablesMaster.getComments());
        mapstructMarker.setEntityFullName(tgProject.getPackageEntity()+"."+mapstructMarker.getEntityName());
        mapstructMarker.setDataVoName(mapstructMarker.getEntityName()+"Scene1DataVO");
        mapstructMarker.setDataVoFullName(tgProject.getPackageVo()+"."+mapstructMarker.getDataVoName());
        return ResultDTO.success(mapstructMarker);
    }
}
