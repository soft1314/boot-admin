package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.mapper.mybatis.entity.VTablesMaster;
import com.qiyuan.base2048.pojo.dto.freemarker.ServiceMarker;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.TableColVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.service.Component.FreeMarkerUtils;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.database.TableService;
import com.qiyuan.base2048.service.generator.GeneratorInfoAdapter;
import com.qiyuan.base2048.service.generator.ServiceGenerator;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.DataTypeEnum;
import com.qiyuan.bautil.enums.TableColStyleEnum;
import com.qiyuan.bautil.util.StringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("Scene4ServiceGeneratorImpl")
public class Scene4ServiceGeneratorImpl implements ServiceGenerator {
    @Value("${base2048.table-schema}")
    private String tableSchema;
    @Autowired
    private FreeMarkerUtils freeMarkerUtils;
    @Resource
    private TableService tableService;
    @Resource
    private GeneratorInfoAdapter generatorInfoAdapter;
    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;

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
        freeMarkerUtils.generate(root, "scene4Service.ftl", path, this.getInterfaceFileName(tableName));
        freeMarkerUtils.generate(root, "scene4ServiceImpl.ftl", path + File.separator + "impl", this.getImplFileName(tableName));
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
        String srcInterface = freeMarkerUtils.generateSource(root, "scene4Service.ftl");
        String srcImpl = freeMarkerUtils.generateSource(root, "scene4ServiceImpl.ftl");

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
        return "I"+StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName))+"Scene4Service";
    }
    private String getImplClassName(String tableName){
        return StringTool.toUpperCaseFirstOne(StringTool.lineToHump(tableName))+"Scene4ServiceImpl";
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
        /** 列信息 **/
        this.setCols(serviceMarker,tableName);
        return ResultDTO.success(serviceMarker);
    }
    private ServiceMarker setCols(ServiceMarker serviceMarker, String tableName) throws Exception{
        List<UserTableColumnResultDTO> userTableColumnResultDTOList = userTableColumnMasterGetter.selectUserTableColumn(tableSchema,tableName);
        List<TableColVO> tableColVOList = new ArrayList<>();
        for(UserTableColumnResultDTO userTableColumnResultDTO:userTableColumnResultDTOList){
            TableColVO tableColVO = new TableColVO();
            /** 字段名 转 驼峰 **/
            tableColVO.setProp(freeMarkerUtils.underlineToHump(userTableColumnResultDTO.getColumnName()));
            /** ; 分隔，前面是字段label,后面是对应的代码类型短语 **/
            String[] commentsArray = userTableColumnResultDTO.getComments().split(";");
            if(commentsArray.length == 1) {
                if("DATE".equals(userTableColumnResultDTO.getDataType())){
                    tableColVO.setType(TableColStyleEnum.DATE);
                }else {
                    tableColVO.setType(TableColStyleEnum.NORMAL);
                }
                tableColVO.setLabel(userTableColumnResultDTO.getComments());
            }else if(commentsArray.length == 2){
                tableColVO.setType(TableColStyleEnum.CODE);
                tableColVO.setLabel(commentsArray[0]);
                tableColVO.setDicType(commentsArray[1]);
            }else if(commentsArray.length == 3 && "LAZY".equalsIgnoreCase(commentsArray[2])){
                tableColVO.setType(TableColStyleEnum.LAZYCODE);
                tableColVO.setLabel(commentsArray[0]);
                tableColVO.setDicType(commentsArray[1]);
            }else{
                throw new Exception("tableName:"+tableName+";字段名："+userTableColumnResultDTO.getColumnName()+";字段描述："+userTableColumnResultDTO.getComments()+";字段描述格式不正确。");
            }
            /** 数据类型 **/
            if(userTableColumnResultDTO.getDataType().startsWith("NUMBER")){
                tableColVO.setDataType(DataTypeEnum.NUMBER);
            }else if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR")){
                tableColVO.setDataType(DataTypeEnum.STRING);
            }else if(userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                tableColVO.setDataType(DataTypeEnum.STRING);
            }else if("DATE".equals(userTableColumnResultDTO.getDataType())){
                tableColVO.setDataType(DataTypeEnum.DATE);
            }else if(userTableColumnResultDTO.getDataType().startsWith("TIME")){
                tableColVO.setDataType(DataTypeEnum.DATE);
            }else{
                tableColVO.setDataType(DataTypeEnum.STRING);
            }

            tableColVOList.add(tableColVO);
        }
        serviceMarker.setCols(tableColVOList);
        return serviceMarker;
    }
}
