package com.qiyuan.base2048.pojo.vo.vuecomponent;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-04-29 15:21
 **/
@Data
public class ApiPost {
    @NotBlank(message = "方法名称不允许为空。")
    private String functionName;
    @NotBlank(message = "远程URI不允许为空。")
    private String uri;
    @NotBlank(message = "文件保存路径不允许为空。")
    private String path;
}
