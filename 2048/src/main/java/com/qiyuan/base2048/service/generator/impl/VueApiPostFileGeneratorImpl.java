package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.pojo.vo.vuecomponent.ApiJsGeneratorParameterDTO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.VueControllerResultDTO;
import com.qiyuan.base2048.service.component.FreeMarkerUtils;
import com.qiyuan.base2048.service.generator.ControllerInfoGetter;
import com.qiyuan.base2048.service.generator.VueApiPostFileGenerator;
import com.qiyuan.bautil.annotate.response.enumer.ResultStatus;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.util.StringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VueApiPostFileGeneratorImpl implements VueApiPostFileGenerator {
    @Autowired
    private FreeMarkerUtils freeMarkerUtils;
    @Autowired
    private ControllerInfoGetter controllerInfoGetter;

    @Override
    public ResultDTO getSource(String author, String controllerName) throws Exception {
        String source = "";
        List<VueControllerResultDTO> controllerDTOList = controllerInfoGetter.getAllInfo();
        for (VueControllerResultDTO controllerDTO : controllerDTOList) {
            if (controllerName.equalsIgnoreCase(controllerDTO.getName())) {
                controllerDTO.setAuthor(author);
                Map<String, Object> root = new HashMap<>();
                root.put("api", controllerDTO);
                source = freeMarkerUtils.generateSource(root, "vueApi.ftl");
                break;
            }
        }
        return ResultDTO.success(source);
    }

    @Override
    public ResultDTO generate(String path,String author,String controllerName,boolean overwrite) throws Exception {
        String jsFileName;
        if (controllerName.endsWith("Controller")) {
            jsFileName = StringTool.replaceLast(controllerName, "Controller", "");
        }else{
            jsFileName="undefined";
        }
        jsFileName = StringTool.humpToLineMinusSign(jsFileName);

        if(!overwrite) {
            if (isJsFileAlreadyExist(jsFileName,path)) {
                return ResultDTO.failure(ResultStatus.FUNCTION_ALREADY_EXIST_ERROR);
            }
        }
        List<VueControllerResultDTO> controllerDTOList = controllerInfoGetter.getAllInfo();
        for (VueControllerResultDTO controllerDTO : controllerDTOList) {
            if (controllerName.equalsIgnoreCase(controllerDTO.getName())) {
                controllerDTO.setAuthor(author);
                Map<String, Object> root = new HashMap<>();
                root.put("api", controllerDTO);
                jsFileName = jsFileName + ".js";
                freeMarkerUtils.generate(root, "vueApi.ftl", path, jsFileName);
                break;
            }
        }
        return ResultDTO.success();
    }

    @Override
    public ResultDTO generateAll(ApiJsGeneratorParameterDTO apiJsGeneratorDTO) throws Exception {
        List<VueControllerResultDTO> controllerDTOList = controllerInfoGetter.getAllInfo();
        for (VueControllerResultDTO controllerDTO : controllerDTOList) {
            Map<String, Object> root = new HashMap<>();
            root.put("api", controllerDTO);
            String jsFileName = StringTool.toLowerCaseFirstOne(controllerDTO.getName());
            if (jsFileName.endsWith("Controller")) {
                jsFileName = StringTool.replaceLast(jsFileName, "Controller", "");
            }
            jsFileName = jsFileName + ".js";
            freeMarkerUtils.generate(root, "vueApi.ftl", apiJsGeneratorDTO.getPath(), jsFileName);
        }
        return ResultDTO.success();
    }
    private boolean isJsFileAlreadyExist(String fileName, String path) throws Exception {
        File file = new File(path+File.separator+fileName+".js");
        return file.exists();
    }
}

