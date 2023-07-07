package com.qiyuan.base2048.service.generator;

import com.qiyuan.base2048.pojo.vo.vuecomponent.ApiJsGeneratorParameterDTO;
import com.qiyuan.bautil.dto.ResultDTO;

public interface VueApiPostFileGenerator {
//    ResultDTO generate(ApiJsGeneratorParameterDTO apiJsGeneratorDTO) throws Exception;
    ResultDTO getSource(String author,String controllerName) throws Exception;
    ResultDTO generate(String path,String author,String controllerName,boolean overwrite) throws Exception;

    ResultDTO generateAll(ApiJsGeneratorParameterDTO apiJsGeneratorDTO) throws Exception;
}
