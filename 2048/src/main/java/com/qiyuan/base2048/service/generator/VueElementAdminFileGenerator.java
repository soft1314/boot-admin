package com.qiyuan.base2048.service.generator;

import com.qiyuan.base2048.pojo.vo.vuecomponent.Vue;
import com.qiyuan.bautil.dto.ResultDTO;

public interface VueElementAdminFileGenerator {
    ResultDTO generate(Vue vue) throws Exception;
}
