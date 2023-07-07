package com.qiyuan.base2048.pojo.dto.freemarker;

import lombok.Data;

@Data
public class VueRouterMarker {
    /** 小写字母 表名 **/
    private String tableNameLowercase;
    /** 表描述 **/
    private String tableComment;
    /** 表名小写 减号连接**/
    private String tableNameLowercaseMinusSign;
}
