package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

@Data
public class ApiJsGeneratorParameterDTO {
    private String path;
    private String controllerName;
    private Boolean overwrite;
}
