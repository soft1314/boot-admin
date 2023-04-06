package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.mapper.mybatis.entity.VTablesMaster;
import com.qiyuan.base2048.pojo.dto.mybatis.EntityColumnDTO;
import com.qiyuan.base2048.pojo.dto.mybatis.EntityDTO;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.service.Component.FreeMarkerUtils;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.database.TableService;
import com.qiyuan.base2048.service.generator.VoGenerator;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("Scene2DataVoGeneratorImpl")
public class Scene2DataVoGeneratorImpl implements VoGenerator {
    @Value("${base2048.table-schema}")
    private String tableSchema;
    @Autowired
    private FreeMarkerUtils freeMarkerUtils;
    @Resource
    private TableService tableService;
    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;

    @Override
    public ResultDTO generateFile(String tableName, String packageUrl, String path, String author, boolean overwrite) throws Exception {
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
        freeMarkerUtils.generate(root, "scene2DataVo.ftl", path, this.getVoFileName(tableName));
        return ResultDTO.success();
    }

    @Override
    public ResultDTO generateCode(String tableName, String packageUrl, String path, String author) throws Exception {
        ResultDTO resultDTO = this.createVoDTO(tableName,packageUrl,author);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        EntityDTO entityDTO = (EntityDTO)resultDTO.getData();

        Map<String, Object> root = new HashMap<>();
        root.put("entity", entityDTO);
        String src = freeMarkerUtils.generateSource(root, "scene2DataVo.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(src);
        sourceCodeVO.setFileName(this.getVoFileFullName(tableName,path));
        return ResultDTO.success(sourceCodeVO);
    }

    private boolean isVoFileAlreadyExist(String tableName, String path) throws Exception {
        File file = new File(this.getVoFileFullName(tableName,path));
        return file.exists();
    }
    private String getVoClassName(String tableName){
        return StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName))+"Scene2DataVO";
    }
    private String getVoFileName(String tableName){
        return this.getVoClassName(tableName)+".java";
    }
    private String getVoFileFullName(String tableName, String path) throws Exception{
        return path+File.separator+this.getVoFileName(tableName);
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
                if("create_time".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
                    continue;
                }
                if("create_by".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
                    continue;
                }
                if("update_time".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
                    continue;
                }
                if("update_by".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
                    continue;
                }
                if("modify_time".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
                    continue;
                }
                if("modify_by".equalsIgnoreCase(userTableColumnResultDTO.getColumnName())){
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
}
