package com.qiyuan.bautil.dto;

import lombok.Data;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-09-07 11:48
 **/
@Data
public class MenuDTO {
    /**
     * id: 2, srvName: '/example/table', show: true
     */
    private String id;
    private String srvName;
    private Boolean show;
    private String accessControlStyle;
}
