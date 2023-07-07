package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-16 17:23
 **/
@Data
public class ElSelect extends ElInput {
    private String type = "SELECT";
    private String options = "";
    public ElSelect(String label,String vmodel,String options){
        super(label,vmodel);
        this.options = options;
        super.setPlaceholder("请使用下拉框选择"+label);
    }
}
