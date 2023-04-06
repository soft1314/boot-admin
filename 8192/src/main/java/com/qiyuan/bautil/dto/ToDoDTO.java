package com.qiyuan.bautil.dto;

import lombok.Data;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2021-01-05 13:30
 **/
@Data
public class ToDoDTO {
    /** 标题 **/
    private String title;
    /** 分类 **/
    private String item;
    /** 个数 **/
    private Integer count;
    /** 入口 **/
    private String url;
}
