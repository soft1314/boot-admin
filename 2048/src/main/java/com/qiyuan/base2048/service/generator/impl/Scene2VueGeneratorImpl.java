package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.dto.freemarker.VueMarker;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.TableColVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.service.Component.FreeMarkerUtils;
import com.qiyuan.base2048.service.generator.VueGenerator;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.DataTypeEnum;
import com.qiyuan.bautil.enums.TableColStyleEnum;
import com.qiyuan.bautil.util.StringTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("Scene2VueGeneratorImpl")
public class Scene2VueGeneratorImpl implements VueGenerator {
    @Value("${base2048.table-schema}")
    private String tableSchema;
    @Resource
    private FreeMarkerUtils freeMarkerUtils;
    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;

    @Override
    public ResultDTO generateCode(String tableName, TgProject tgProject) throws Exception {
        /** 生成封闭数据 **/
        VueMarker marker = this.createVueMarker(tableName,tgProject);
        /** 生成源码 **/
        Map<String, Object> root = new HashMap<>();
        root.put("marker", marker);
        String source = freeMarkerUtils.generateSource(root, "scene2Vue.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(source);
        String filePath = this.getFilePath(tgProject,tableName);
        sourceCodeVO.setFileName(filePath+File.separator+"index.vue");

        return ResultDTO.success(sourceCodeVO);
    }

    @Override
    public ResultDTO generateFile(String tableName, boolean overwrite ,TgProject tgProject) throws Exception {
        /** 检查文件是否存在 **/
        String filePath = this.getFilePath(tgProject,tableName);
        if(!overwrite) {
            if (isJsFileAlreadyExist("index.vue",filePath)) {
                return ResultDTO.failure(ResultStatus.FUNCTION_ALREADY_EXIST_ERROR);
            }
        }
        /** 生成封闭数据 **/
        VueMarker marker = this.createVueMarker(tableName,tgProject);
        /** 生成文件 **/
        Map<String, Object> root = new HashMap<>();
        root.put("marker", marker);
        freeMarkerUtils.generate(root, "scene2Vue.ftl", filePath, "index.vue");
        return ResultDTO.success();
    }
    private boolean isJsFileAlreadyExist(String fileName, String path) throws Exception {
        File file = new File(path+File.separator+fileName);
        return file.exists();
    }
    private VueMarker createVueMarker(String tableName, TgProject tgProject) throws Exception{
        String humpTableName = StringTool.lineToHump(tableName);
        String lineMinusSignTableName = StringTool.humpToLineMinusSign(humpTableName);

        VueMarker marker = new VueMarker();
        marker.setAuthor(tgProject.getAuthor());
        marker.setApiJsFileName(lineMinusSignTableName);
        marker.setEntityName(StringTool.toUpperCaseFirstOne(humpTableName));
        marker = this.setCols(marker,tableName);
        return marker;
    }
    private VueMarker setCols(VueMarker vueFile, String tableName) throws Exception{
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
        vueFile.setCols(tableColVOList);
        return vueFile;
    }
    private String getFilePath(TgProject tgProject,String tableName){
        return tgProject.getVueDir()  + File.separator + tableName.replace("_","-").toLowerCase()+"-scene2";
    }
}
