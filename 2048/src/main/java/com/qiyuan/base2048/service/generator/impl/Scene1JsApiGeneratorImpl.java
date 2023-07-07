package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.JsApiFile;
import com.qiyuan.base2048.service.component.FreeMarkerUtils;
import com.qiyuan.base2048.service.generator.JsApiGenerator;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.util.StringTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service("Scene1JsApiGeneratorImpl")
public class Scene1JsApiGeneratorImpl implements JsApiGenerator {
    @Resource
    private FreeMarkerUtils freeMarkerUtils;

    @Override
    public ResultDTO generateCode(String tableName, String path, String author) throws Exception {
        /** 生成封闭数据 **/
        JsApiFile jsApiFile = this.createJsApiFile(tableName,author);
        /** 生成源码 **/
        Map<String, Object> root = new HashMap<>();
        root.put("dataContainer", jsApiFile);
        String source = freeMarkerUtils.generateSource(root, "scene1JsApi.ftl");
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setSourceCode(source);
        String fileName = tableName.replace("_","-").toLowerCase()+"-scene1.js";
        sourceCodeVO.setFileName(path+File.separator+fileName);
        return ResultDTO.success(sourceCodeVO);
    }

    @Override
    public ResultDTO generateFile(String tableName, String path, String author, boolean overwrite) throws Exception {
        /** 检查文件是否存在 **/
        String fileName = tableName.replace("_","-").toLowerCase()+"-scene1.js";
        if(!overwrite) {
            if (isJsFileAlreadyExist(fileName,path)) {
                return ResultDTO.failure(ResultStatus.FUNCTION_ALREADY_EXIST_ERROR);
            }
        }
        /** 生成封闭数据 **/
        JsApiFile jsApiFile = this.createJsApiFile(tableName,author);
        /** 生成文件 **/
        Map<String, Object> root = new HashMap<>();
        root.put("dataContainer", jsApiFile);
        freeMarkerUtils.generate(root, "scene1JsApi.ftl", path, fileName);
        return ResultDTO.success();
    }
    private boolean isJsFileAlreadyExist(String fileName, String path) throws Exception {
        File file = new File(path+File.separator+fileName);
        return file.exists();
    }
    private JsApiFile createJsApiFile(String tableName, String author) throws Exception{
        String humpTableNameFirstUppercase = StringTool.lineToHumpUpperCase(tableName);
        String lineMinusSignTableName = tableName.replace("_","-").toLowerCase();

        JsApiFile jsApiFile = new JsApiFile();
        jsApiFile.setAuthor(author);
        jsApiFile.setUppercaseHump(humpTableNameFirstUppercase);
        jsApiFile.setLowercaseMinusSign(lineMinusSignTableName);

        return jsApiFile;
    }

}
