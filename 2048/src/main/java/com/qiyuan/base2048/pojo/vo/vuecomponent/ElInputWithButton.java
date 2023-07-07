package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-16 17:36
 **/
@Data
public class ElInputWithButton extends ElInput {
    private String type = "INPUTWITHBUTTON";
    private String icon = "el-icon-more";
    private String click = "handleTree('mof-div')";

    public ElInputWithButton(String label, String vmodel, String click) {
        super(label, vmodel);
        this.click = click;
        super.setPlaceholder("请点击按钮并选择"+label);
    }
}
