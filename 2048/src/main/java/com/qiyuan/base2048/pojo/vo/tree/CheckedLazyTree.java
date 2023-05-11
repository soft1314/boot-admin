package com.qiyuan.base2048.pojo.vo.tree;

import lombok.Data;

import java.util.List;

/**
 * 树的节点数据以及已选择的节点数据
 */
@Data
public class CheckedLazyTree {
    //树的数据，包括所有已选择的节点，无关的未选择节点可能不包括，可以通过懒加载后续装入
    private List<TreeNode> tree;
    private List<String> checkCodes;
}
