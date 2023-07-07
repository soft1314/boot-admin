package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-16 16:10
 **/
@Data
public class ElDrawer {
    private String name = "filterDrawer";
    private String title = "请输入查询条件";
    private String direction = "rtl";
    private String withHeader = "false";
    private String size = "30%";
    private String formLabelWidth = "100px";
    /** 支持 ElInput ElInputWithButton ElSelect **/
    private List formItemList;
}
