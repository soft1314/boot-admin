package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.entity.TgProject;
import com.qiyuan.base2048.pojo.dto.freemarker.VueMarker;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.service.Component.FreeMarkerUtils;
import com.qiyuan.base2048.service.database.ColumnService;
import com.qiyuan.base2048.service.generator.VueGenerator;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.util.StringTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service("Scene3AddVueGeneratorImpl")
public class Scene3AddVueGeneratorImpl implements VueGenerator {
    @Resource
    private FreeMarkerUtils freeMarkerUtils;
    @Resource
    private ColumnService columnService;

    @Override
    public ResultDTO generateCode(String tableName, TgProject tgProject) throws Exception {
        /** 生成封闭数据 **/
        VueMarker marker = this.createVueMarker(tableName,tgProject);
        /** 生成源码 **/
        Map<String, Object> root = new HashMap<>();
        root.put("marker", marker);
        String source = freeMarkerUtils.generateSource(root, "scene3AddVue.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(source);
        String filePath = this.getFilePath(tgProject,tableName);
        String humpTableName = StringTool.lineToHump(tableName);
        sourceCodeVO.setFileName(filePath+File.separator+humpTableName+"Scene3Add.vue");

        return ResultDTO.success(sourceCodeVO);
    }

    @Override
    public ResultDTO generateFile(String tableName, boolean overwrite ,TgProject tgProject) throws Exception {
        /** 检查文件是否存在 **/
        String filePath = this.getFilePath(tgProject,tableName);
        String humpTableName = StringTool.lineToHump(tableName);
        if(!overwrite) {
            if (isJsFileAlreadyExist(humpTableName+"Scene3Add.vue",filePath)) {
                return ResultDTO.failure(ResultStatus.FUNCTION_ALREADY_EXIST_ERROR);
            }
        }
        /** 生成封闭数据 **/
        VueMarker marker = this.createVueMarker(tableName,tgProject);
        /** 生成文件 **/
        Map<String, Object> root = new HashMap<>();
        root.put("marker", marker);
        freeMarkerUtils.generate(root, "scene3AddVue.ftl", filePath, humpTableName+"Scene3Add.vue");
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
        marker.setCols(columnService.getTableCols(tableName));
        return marker;
    }
    private String getFilePath(TgProject tgProject,String tableName){
        return tgProject.getViewsDir()  + File.separator + "components" + File.separator +"add" ;
    }
}
