package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-20 15:32
 **/
@Data
public class ElButton {
    private String name = "按钮";
    private String size = "mini";
    private String type = "primary";
    private String icon = "el-icon-edit";
    private String click = "handleFunc()";
}
