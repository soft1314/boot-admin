package com.qiyuan.base2048.pojo.bo;

import com.qiyuan.base2048.pojo.vo.tree.TreeNode;
import lombok.Data;

import java.util.Map;

@Data
public class TreeAndMapBO {
    private TreeNode root;
    private Map nodeMap;
}
