package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-16 17:14
 **/
@Data
public class ElInput {
    private String type = "INPUT";
    private String label = "";
    private String placeholder = "请输入";
    private String size = "mini";
    private String prefixIcon = "el-icon-search";
    private String vmodel = "name";
    private String defaultValue = "";

    public ElInput(String label, String vmodel) {
        this.label = label;
        this.vmodel = vmodel;
        this.placeholder = "请输入"+label;
    }
}
