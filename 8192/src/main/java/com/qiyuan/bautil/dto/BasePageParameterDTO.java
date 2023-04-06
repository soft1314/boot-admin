package com.qiyuan.bautil.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-12-16 10:04
 **/
@Data
public class BasePageParameterDTO {
    @NotNull(message = "当前页码是必填项。")
    private Integer currentPage;
    @NotNull(message = "每页数据行数是必填项。")
    private Integer pageSize;
}
