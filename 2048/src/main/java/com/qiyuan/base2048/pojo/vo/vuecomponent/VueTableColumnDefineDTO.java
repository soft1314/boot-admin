package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-28 16:36
 **/
@Data
public class VueTableColumnDefineDTO {
    private String comments;
    private String columnName;
    private String dataType;
    private String dataDefault;
    private boolean inDrawer;
    private boolean inElTable;
    private boolean inElTableExpand;
    private boolean inAddOrEditForm;
    private String optionsName;
    private String methodName;

    private String vmodel;
}
