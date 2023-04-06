package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

import java.util.List;

@Data
public class VueControllerResultDTO {
    private String name;
    private String uri;
    private String apiMessage;
    List<MappingResultDTO> mappingDTOS;
    private String methods;
    private String author;
}
