package com.qiyuan.bautil.dto;

import lombok.Data;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-23 14:20
 **/
@Data
public class PageQueryDTO {
    private Integer currentPage=1;
    private Integer pageSize=5;
    private Integer total;
}
