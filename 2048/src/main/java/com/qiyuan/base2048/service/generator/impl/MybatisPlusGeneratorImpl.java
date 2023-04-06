package com.qiyuan.base2048.service.generator.impl;

import cn.hutool.Hutool;
import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.mapper.mybatis.VTablesMasterMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.VTablesMaster;
import com.qiyuan.base2048.pojo.dto.mybatis.EntityColumnDTO;
import com.qiyuan.base2048.pojo.dto.mybatis.EntityDTO;
import com.qiyuan.base2048.pojo.dto.mybatis.MapperDTO;
import com.qiyuan.base2048.pojo.dto.mybatis.ServiceDTO;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.VueControllerResultDTO;
import com.qiyuan.base2048.service.Component.FreeMarkerUtils;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.database.TableService;
import com.qiyuan.base2048.service.generator.MybatisPlusGenerator;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.util.OracleTool;
import com.qiyuan.bautil.util.StringTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MybatisPlusGeneratorImpl implements MybatisPlusGenerator {
    @Value("${base2048.table-schema}")
    private String tableSchema;
    @Autowired
    private FreeMarkerUtils freeMarkerUtils;
    @Resource
    private TableService tableService;
    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;

    @Override
    public ResultDTO generateEntitySource(String tableName,String packageUrl,String path,String author) throws Exception {
        ResultDTO resultDTO = this.createEntityDTO(tableName,packageUrl,author);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        EntityDTO entityDTO = (EntityDTO)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("entity", entityDTO);
        String src = freeMarkerUtils.generateSource(root, "entity.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(src);
        sourceCodeVO.setFileName(this.getEntityFileFullName(tableName,path));
        return ResultDTO.success(sourceCodeVO);
    }

    @Override
    public ResultDTO generateEntity(String tableName,String packageUrl,String path,String author,boolean overwrite) throws Exception {
        if(!overwrite) {
            if (isEntityFileAlreadyExist(tableName, path)) {
                return ResultDTO.failure(ResultStatus.FUNCTION_ALREADY_EXIST_ERROR);
            }
        }

        ResultDTO resultDTO = this.createEntityDTO(tableName,packageUrl,author);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        EntityDTO entityDTO = (EntityDTO)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("entity", entityDTO);
        /** Entity 直接覆盖 **/
        freeMarkerUtils.generate(root, "entity.ftl", path, entityDTO.getEntityName()+".java");
        return ResultDTO.success();
    }

    @Override
    public ResultDTO generateVo(String tableName, String packageUrl, String path, String author, boolean overwrite) throws Exception {
        if(!overwrite) {
            if (isVoFileAlreadyExist(tableName, path)) {
                return ResultDTO.failure(ResultStatus.FUNCTION_ALREADY_EXIST_ERROR);
            }
        }
        ResultDTO resultDTO = this.createVoDTO(tableName,packageUrl,author);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        EntityDTO entityDTO = (EntityDTO)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("entity", entityDTO);
        freeMarkerUtils.generate(root, "vo.ftl", path, entityDTO.getEntityName()+"VO.java");
        return ResultDTO.success();
    }

    @Override
    public ResultDTO generateVoSource(String tableName, String packageUrl, String path, String author) throws Exception {
        ResultDTO resultDTO = this.createVoDTO(tableName,packageUrl,author);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        EntityDTO entityDTO = (EntityDTO)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("entity", entityDTO);
        String src = freeMarkerUtils.generateSource(root, "vo.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(src);
        sourceCodeVO.setFileName(this.getVoFileFullName(tableName,path));
        return ResultDTO.success(sourceCodeVO);
    }

    @Override
    public ResultDTO generateMapper(String tableName,String packageUrl,String entityPackageUrl, String mapperClassFilePath,String mapperXmlFilePath,String author) throws Exception {
        ResultDTO resultDTO = this.createMapperDTO(tableName,packageUrl,entityPackageUrl,author);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        MapperDTO mapperDTO = (MapperDTO)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("mapper", mapperDTO);
        /** Mapper 不覆盖 **/
        StringBuilder stringBuilder = new StringBuilder();
        File mapperFile = new File(mapperClassFilePath+File.separator+mapperDTO.getEntityName()+"Mapper.java");
        if(mapperFile.exists()) {
            stringBuilder.append(mapperDTO.getEntityName() + "Mapper.java已存在；");
        }else{
            freeMarkerUtils.generate(root, "mapper.ftl", mapperClassFilePath, mapperDTO.getEntityName() + "Mapper.java");
            stringBuilder.append(mapperDTO.getEntityName() + "Mapper.java生成成功；");
        }
        File mapperXmlFile = new File(mapperXmlFilePath+File.separator+mapperDTO.getEntityName() + "Mapper.xml");
        if(mapperXmlFile.exists()) {
            stringBuilder.append(mapperDTO.getEntityName() + "Mapper.xml已存在；");
        }else{
            freeMarkerUtils.generate(root, "mapperxml.ftl", mapperXmlFilePath, mapperDTO.getEntityName() + "Mapper.xml");
            stringBuilder.append(mapperDTO.getEntityName() + "Mapper.xml生成成功；");
        }
        return ResultDTO.successCustom(stringBuilder.toString(),null);
    }

    @Override
    public ResultDTO generateMapperSource(String tableName, String packageUrl, String entityPackageUrl, String mapperClassFilePath, String mapperXmlFilePath, String author) throws Exception {
        ResultDTO resultDTO = this.createMapperDTO(tableName,packageUrl,entityPackageUrl,author);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        MapperDTO mapperDTO = (MapperDTO)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("mapper", mapperDTO);
        String srcJava = freeMarkerUtils.generateSource(root, "mapper.ftl");
        String srcXml = freeMarkerUtils.generateSource(root, "mapperxml.ftl");
        String javaFile = mapperClassFilePath+File.separator+mapperDTO.getEntityName()+"Mapper.java";
        String xmlFile = mapperXmlFilePath+File.separator+mapperDTO.getEntityName() + "Mapper.xml";

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/** --------------------------------------------------------------------------------------------- **/")
                .append("\r\n")
                .append("/** 文件名：")
                .append(javaFile)
                .append("  **/")
                .append("\r\n")
                .append("/** --------------------------------------------------------------------------------------------- **/")
                .append("\r\n")
                .append("\r\n")
                .append(srcJava)
                .append("\r\n")
                .append("\r\n")
                .append("<!--------------------------------------------------------------------------------------------------->")
                .append("\r\n")
                .append("<!-- 文件名：")
                .append(xmlFile)
                .append("  >")
                .append("\r\n")
                .append("<!--------------------------------------------------------------------------------------------------->")
                .append("\r\n")
                .append("\r\n")
                .append(srcXml);

        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(stringBuffer.toString());
        sourceCodeVO.setFileName("参见源代码。");
        return ResultDTO.success(sourceCodeVO);
    }

    @Override
    public ResultDTO generateService(String tableName,String packageUrl,String entityPackageUrl,String mapperPackageUrl, String path,String author) throws Exception {
        ResultDTO resultDTO = this.createServiceDTO(tableName,packageUrl,entityPackageUrl,mapperPackageUrl,author);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        ServiceDTO serviceDTO = (ServiceDTO)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("service", serviceDTO);
        /** Service 不覆盖 **/
        StringBuilder stringBuilder = new StringBuilder();
        File serviceFile = new File(path+File.separator+"I"+serviceDTO.getEntityName()+"Service.java");
        if(serviceFile.exists()) {
            stringBuilder.append("I" + serviceDTO.getEntityName() + "Service.java已存在；");
        }else{
            freeMarkerUtils.generate(root, "service.ftl", path, "I" + serviceDTO.getEntityName() + "Service.java");
            stringBuilder.append("I" + serviceDTO.getEntityName() + "Service.java生成成功；");
        }
        File serviceImplFile = new File(path+File.separator+"impl"+File.separator+serviceDTO.getEntityName()+"ServiceImpl.java");
        if(serviceImplFile.exists()) {
            stringBuilder.append(serviceDTO.getEntityName() + "ServiceImpl.java已存在；");
        }else{
            freeMarkerUtils.generate(root, "serviceimpl.ftl", path +File.separator+ "impl", serviceDTO.getEntityName() + "ServiceImpl.java");
            stringBuilder.append(serviceDTO.getEntityName() + "ServiceImpl.java生成成功；");
        }

        return ResultDTO.successCustom(stringBuilder.toString(),null);
    }

    @Override
    public ResultDTO generateServiceSource(String tableName, String packageUrl, String entityPackageUrl, String mapperPackageUrl, String path, String author) throws Exception {
        ResultDTO resultDTO = this.createServiceDTO(tableName,packageUrl,entityPackageUrl,mapperPackageUrl,author);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        ServiceDTO serviceDTO = (ServiceDTO)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("service", serviceDTO);
        String interfaceJava = freeMarkerUtils.generateSource(root, "service.ftl");
        String implJava = freeMarkerUtils.generateSource(root, "serviceimpl.ftl");
        String interfaceFile = path+File.separator+"I"+serviceDTO.getEntityName()+"Service.java";
        String implFile = path+File.separator+"impl"+File.separator+serviceDTO.getEntityName()+"ServiceImpl.java";

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("/** --------------------------------------------------------------------------------------------- **/")
                .append("\r\n")
                .append("/** 文件名：")
                .append(interfaceFile)
                .append("  **/")
                .append("\r\n")
                .append("/** --------------------------------------------------------------------------------------------- **/")
                .append("\r\n")
                .append("\r\n")
                .append(interfaceJava)
                .append("\r\n")
                .append("\r\n")
                .append("/** --------------------------------------------------------------------------------------------- **/")
                .append("\r\n")
                .append("/** 文件名：")
                .append(implFile)
                .append("  **/")
                .append("\r\n")
                .append("/** --------------------------------------------------------------------------------------------- **/")
                .append("\r\n")
                .append("\r\n")
                .append(implJava);

        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(stringBuffer.toString());
        sourceCodeVO.setFileName("参见源代码。");
        return ResultDTO.success(sourceCodeVO);
    }

    @Override
    public ResultDTO generateController(String tableName,String packageUrl, String path,String author) throws Exception {
        return ResultDTO.failureCustom("本功能没开发呢。");
    }

    @Override
    public ResultDTO generateControllerSource(String tableName, String packageUrl, String path, String author) throws Exception {
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode("哈哈哈哈");
        sourceCodeVO.setFileName("参见源代码。");
        return ResultDTO.success(sourceCodeVO);
    }

    /**
     * 检查实体类是否文件已存在
     * @param tableName
     * @param path
     * @return
     * @throws Exception
     */
    private boolean isEntityFileAlreadyExist(String tableName, String path) throws Exception {
        String fileName = StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName))+".java";
        File file = new File(path+File.separator+fileName);
        return file.exists();
    }
    private String getEntityFileFullName(String tableName, String path) throws Exception{
        String fileName = StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName))+".java";
        return path+File.separator+fileName;
    }
    private String getVoFileFullName(String tableName, String path) throws Exception{
        String fileName = StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName))+"VO.java";
        return path+File.separator+fileName;
    }
    private boolean isVoFileAlreadyExist(String tableName, String path) throws Exception {
        String fileName = StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName))+"VO.java";
        File file = new File(path+File.separator+fileName);
        return file.exists();
    }
    private ResultDTO createEntityDTO(String tableName,String packageUrl,String author) throws Exception{
        VTablesMaster vTablesMaster = tableService.getOneByTableName(tableName.toUpperCase());
        if(vTablesMaster == null){
            return ResultDTO.failureCustom(MessageUtils.get("table.not.found.error"));
        }

        EntityDTO entityDTO = new EntityDTO();
        entityDTO.setPackageUrl(packageUrl);
        entityDTO.setEntityName(StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName)));
        entityDTO.setAuthor(author);
        if(StringUtils.isBlank(vTablesMaster.getComments())){
            entityDTO.setTableComment(tableName);
        }else {
            entityDTO.setTableComment(vTablesMaster.getComments());
        }
        entityDTO.setTableName(tableName.toUpperCase());

        List<UserTableColumnResultDTO>  userTableColumnResultDTOList = userTableColumnMasterGetter.selectUserTableColumn(tableSchema,tableName.toUpperCase());
        List<EntityColumnDTO> entityNonPkColumnDTOS = new ArrayList<>();
        for(UserTableColumnResultDTO userTableColumnResultDTO:userTableColumnResultDTOList){
            if("Y".equalsIgnoreCase(userTableColumnResultDTO.getPrimaryKey())){
                EntityColumnDTO entityColumnDTO = new EntityColumnDTO();
                entityColumnDTO.setColumnName(userTableColumnResultDTO.getColumnName());
                entityColumnDTO.setComments(userTableColumnResultDTO.getComments());
                entityColumnDTO.setDataType(OracleTool.dataTypeToJavaType(userTableColumnResultDTO.getDataType()));
                entityColumnDTO.setClassAttribute(StringTool.lineToHump(userTableColumnResultDTO.getColumnName()));
                entityDTO.setPrimaryKey(entityColumnDTO);
            }else {
                EntityColumnDTO entityColumnDTO = new EntityColumnDTO();
                entityColumnDTO.setColumnName(userTableColumnResultDTO.getColumnName());
                entityColumnDTO.setComments(userTableColumnResultDTO.getComments());
                entityColumnDTO.setDataType(OracleTool.dataTypeToJavaType(userTableColumnResultDTO.getDataType()));
                entityColumnDTO.setClassAttribute(StringTool.lineToHump(userTableColumnResultDTO.getColumnName()));
                entityNonPkColumnDTOS.add(entityColumnDTO);
            }
        }
        entityDTO.setNonPkColumns(entityNonPkColumnDTOS);
        return ResultDTO.success(entityDTO);
    }
    private ResultDTO createVoDTO(String tableName,String packageUrl,String author) throws Exception{
        VTablesMaster vTablesMaster = tableService.getOneByTableName(tableName.toUpperCase());
        if(vTablesMaster == null){
            return ResultDTO.failureCustom(MessageUtils.get("table.not.found.error"));
        }

        EntityDTO entityDTO = new EntityDTO();
        entityDTO.setPackageUrl(packageUrl);
        entityDTO.setEntityName(StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName)));
        entityDTO.setAuthor(author);
        if(StringUtils.isBlank(vTablesMaster.getComments())){
            entityDTO.setTableComment(tableName);
        }else {
            entityDTO.setTableComment(vTablesMaster.getComments());
        }
        entityDTO.setTableName(tableName.toUpperCase());

        List<UserTableColumnResultDTO>  userTableColumnResultDTOList = userTableColumnMasterGetter.selectUserTableColumn(tableSchema,tableName.toUpperCase());
        List<EntityColumnDTO> entityNonPkColumnDTOS = new ArrayList<>();
        for(UserTableColumnResultDTO userTableColumnResultDTO:userTableColumnResultDTOList){
            if("Y".equalsIgnoreCase(userTableColumnResultDTO.getPrimaryKey())){
                EntityColumnDTO entityColumnDTO = new EntityColumnDTO();
                entityColumnDTO.setColumnName(userTableColumnResultDTO.getColumnName());
                entityColumnDTO.setComments(userTableColumnResultDTO.getComments());
                entityColumnDTO.setDataType(OracleTool.dataTypeToJavaType(userTableColumnResultDTO.getDataType()));
                entityColumnDTO.setClassAttribute(StringTool.lineToHump(userTableColumnResultDTO.getColumnName()));
                entityDTO.setPrimaryKey(entityColumnDTO);
            }else {
                if("datestamp".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
                    continue;
                }
                if("created_time".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
                    continue;
                }
                if("created_by".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
                    continue;
                }
                if("updated_time".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
                    continue;
                }
                if("updated_by".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
                    continue;
                }
                if("tenant_id_".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
                    continue;
                }
                EntityColumnDTO entityColumnDTO = new EntityColumnDTO();
                entityColumnDTO.setColumnName(userTableColumnResultDTO.getColumnName());
                entityColumnDTO.setComments(userTableColumnResultDTO.getComments());
                entityColumnDTO.setDataType(OracleTool.dataTypeToJavaType(userTableColumnResultDTO.getDataType()));
                entityColumnDTO.setClassAttribute(StringTool.lineToHump(userTableColumnResultDTO.getColumnName()));
                entityNonPkColumnDTOS.add(entityColumnDTO);
            }
        }
        entityDTO.setNonPkColumns(entityNonPkColumnDTOS);
        return ResultDTO.success(entityDTO);
    }
    private ResultDTO createMapperDTO(String tableName,String packageUrl,String entityPackageUrl, String author) throws Exception{
        VTablesMaster vTablesMaster = tableService.getOneByTableName(tableName.toUpperCase());
        if(vTablesMaster == null){
            return ResultDTO.failureCustom(MessageUtils.get("table.not.found.error"));
        }

        MapperDTO mapperDTO = new MapperDTO();
        mapperDTO.setPackageUrl(packageUrl);
        mapperDTO.setEntityPackageUrl(entityPackageUrl);
        mapperDTO.setEntityName(StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName)));
        mapperDTO.setAuthor(author);
        if(StringUtils.isBlank(vTablesMaster.getComments())){
            mapperDTO.setTableComment(tableName);
        }else {
            mapperDTO.setTableComment(vTablesMaster.getComments());
        }
        mapperDTO.setTableName(tableName.toUpperCase());
        return ResultDTO.success(mapperDTO);
    }
    private ResultDTO createServiceDTO(String tableName,String packageUrl,String entityPackageUrl,String mapperPackageUrl,String author) throws Exception{
        VTablesMaster vTablesMaster = tableService.getOneByTableName(tableName.toUpperCase());
        if(vTablesMaster == null){
            return ResultDTO.failureCustom(MessageUtils.get("table.not.found.error"));
        }

        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setPackageUrl(packageUrl);
        serviceDTO.setEntityPackageUrl(entityPackageUrl);
        serviceDTO.setMapperPackageUrl(mapperPackageUrl);
        serviceDTO.setEntityName(StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName)));
        serviceDTO.setAuthor(author);
        if(StringUtils.isBlank(vTablesMaster.getComments())){
            serviceDTO.setTableComment(tableName);
        }else {
            serviceDTO.setTableComment(vTablesMaster.getComments());
        }
        serviceDTO.setTableName(tableName.toUpperCase());
        return ResultDTO.success(serviceDTO);
    }
}
