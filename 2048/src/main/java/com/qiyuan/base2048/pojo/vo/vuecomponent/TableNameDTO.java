package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TableNameDTO {
    @NotBlank(message = "表名是必填项。")
    private String tableName;
    @NotBlank(message = "数据源是必填项。")
    private String dataSource;
}
