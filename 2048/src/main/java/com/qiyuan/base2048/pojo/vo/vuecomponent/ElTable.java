package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-21 10:28
 **/
@Data
public class ElTable {
    private String vmodel = "tableData";
    private boolean showIndexColumn;
    private boolean showCheckedColumn;
    private boolean showOperateColumn;
    private boolean showExpandColumn;
    private List<ElTableColumn> elTableColumnList;
    private List<ElTableColumn> expandColumnList;
}
