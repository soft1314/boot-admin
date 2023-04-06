package com.qiyuan.bautil.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 虚领顶劲气沉丹田
 * @create 2020-09-18 11:39
 **/
@Data
public class VueTreeNode {
    private String id;
    private String label;
    private String code;
    private boolean isLeaf;
    private boolean disabled;
    private List<VueTreeNode> children;
}
