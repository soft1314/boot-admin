package com.qiyuan.base2048.service.generator;

import com.qiyuan.base2048.pojo.vo.vuecomponent.Vue;
import com.qiyuan.bautil.dto.ResultDTO;

public interface DictypeJsGenerator {
    ResultDTO generateJsCode(String author) throws Exception;
    ResultDTO generateJsFile(String path,String author,boolean overwrite) throws Exception;
}
