package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.mapper.mybatis.entity.VTablesMaster;
import com.qiyuan.base2048.pojo.dto.freemarker.ServiceMarker;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.service.Component.FreeMarkerUtils;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.database.TableService;
import com.qiyuan.base2048.service.generator.GeneratorInfoAdapter;
import com.qiyuan.base2048.service.generator.TransactionGenerator;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.util.StringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service("Scene3TransactionGeneratorImpl")
public class Scene3TransactionGeneratorImpl implements TransactionGenerator {
    @Autowired
    private FreeMarkerUtils freeMarkerUtils;
    @Resource
    private TableService tableService;
    @Resource
    private GeneratorInfoAdapter generatorInfoAdapter;

    @Override
    public ResultDTO generateFile(String tableName, TgProject tgProject,boolean overwrite) throws Exception {
        String path = generatorInfoAdapter.getSceneServiceFilePath(tgProject);
        if(!overwrite){
            if (isInterfaceFileAlreadyExist(tableName, path)||isImplFileAlreadyExist(tableName, path)){
                return ResultDTO.failure(ResultStatus.FUNCTION_ALREADY_EXIST_ERROR);
            }
        }
        ResultDTO resultDTO = this.createMarker(tableName,tgProject);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        ServiceMarker serviceMarker = (ServiceMarker)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("marker", serviceMarker);
        freeMarkerUtils.generate(root, "scene3Transaction.ftl", path, this.getInterfaceFileName(tableName));
        freeMarkerUtils.generate(root, "scene3TransactionImpl.ftl", path + File.separator + "impl", this.getImplFileName(tableName));
        return ResultDTO.success();
    }

    @Override
    public ResultDTO generateCode(String tableName, TgProject tgProject) throws Exception {
        String path = generatorInfoAdapter.getSceneServiceFilePath(tgProject);
        ResultDTO resultDTO = this.createMarker(tableName,tgProject);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        ServiceMarker serviceMarker = (ServiceMarker)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("marker", serviceMarker);
        String srcInterface = freeMarkerUtils.generateSource(root, "scene3Transaction.ftl");
        String srcImpl = freeMarkerUtils.generateSource(root, "scene3TransactionImpl.ftl");

        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/** --------------------------------------------------------------------------------------------- **/")
                .append("\r\n")
                .append("/** 文件名：")
                .append(this.getInterfaceFileFullName(tableName,path))
                .append("  **/")
                .append("\r\n")
                .append("/** --------------------------------------------------------------------------------------------- **/")
                .append("\r\n")
                .append("\r\n")
                .append(srcInterface)
                .append("\r\n")
                .append("\r\n")
                .append("<!--------------------------------------------------------------------------------------------------->")
                .append("\r\n")
                .append("<!-- 文件名：")
                .append(path+File.separator+"impl"+File.separator+this.getImplFileName(tableName))
                .append("  >")
                .append("\r\n")
                .append("<!--------------------------------------------------------------------------------------------------->")
                .append("\r\n")
                .append("\r\n")
                .append(srcImpl);
        sourceCodeVO.setSourceCode(stringBuffer.toString());
        sourceCodeVO.setFileName("参见源代码。");
        return ResultDTO.success(sourceCodeVO);
    }

    private boolean isInterfaceFileAlreadyExist(String tableName, String path) throws Exception {
        File file = new File(this.getInterfaceFileFullName(tableName,path));
        return file.exists();
    }
    private boolean isImplFileAlreadyExist(String tableName, String path) throws Exception {
        File file = new File(this.getImplFileFullName(tableName,path));
        return file.exists();
    }
    private String getInterfaceClassName(String tableName){
        return "I"+StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName))+"Scene3Transaction";
    }
    private String getImplClassName(String tableName){
        return StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName))+"Scene3TransactionImpl";
    }
    private String getInterfaceFileName(String tableName){
        return this.getInterfaceClassName(tableName)+".java";
    }
    private String getImplFileName(String tableName){
        return this.getImplClassName(tableName)+".java";
    }
    private String getInterfaceFileFullName(String tableName, String path) throws Exception{
        return path+File.separator+this.getInterfaceFileName(tableName);
    }
    private String getImplFileFullName(String tableName, String path) throws Exception{
        return path+File.separator+"impl"+File.separator+this.getImplFileName(tableName);
    }
    private ResultDTO createMarker(String tableName, TgProject tgProject) throws Exception{
        VTablesMaster vTablesMaster = tableService.getOneByTableName(tableName.toUpperCase());
        if(vTablesMaster == null){
            return ResultDTO.failureCustom(MessageUtils.get("table.not.found.error"));
        }

        ServiceMarker serviceMarker = new ServiceMarker();

        serviceMarker.setAuthor(tgProject.getAuthor());
        serviceMarker.setEntityName(StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName)));
        serviceMarker.setEntityPackageUrl(tgProject.getPackageEntity());
        serviceMarker.setPackageUrl(tgProject.getPackageServiceSc());
        serviceMarker.setTableComment(vTablesMaster.getComments());
        serviceMarker.setVoPackageUrl(tgProject.getPackageVo());
        serviceMarker.setMapstructPackageUrl(tgProject.getPackageMapstruct());
        serviceMarker.setMapperPackageUrl(tgProject.getPackageMapper());
        return ResultDTO.success(serviceMarker);
    }
}
