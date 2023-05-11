package com.qiyuan.base2048.pojo.vo.tree;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "行政区划树节点类")
public class TreeNode {
//    private String guid;
//    //code_name 格式
//    private String name;
    /** 是否末级 **/
//    private boolean hasChildren;

    private String id;
    private String label;
    private String value;
    Boolean leaf;

    List<TreeNode> children;
}
