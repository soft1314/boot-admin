package com.qiyuan.base2048.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiyuan.base2048.mapper.mapstruct.TbAdmDivScene2StructMapper;
import com.qiyuan.base2048.mapper.mybatis.entity.TbAdmDiv;
import com.qiyuan.base2048.mapper.mybatis.entity.TrNotifyDiv;
import com.qiyuan.base2048.pojo.bo.TreeAndMapBO;
import com.qiyuan.base2048.pojo.vo.tree.CheckedLazyTree;
import com.qiyuan.base2048.pojo.vo.tree.TreeNode;
import com.qiyuan.base2048.service.manage.AdmDivDataGetter;
import com.qiyuan.base2048.service.mybatis.ITbAdmDivService;
import com.qiyuan.base2048.service.mybatis.ITrNotifyDivService;
import com.qiyuan.bautil.constant.ConstantCommon;
import com.qiyuan.bautil.dto.ResultDTO;
import com.qiyuan.bautil.enums.IsDeletedEnum;
import com.qiyuan.bautil.enums.IsEnabledEnum;
import io.seata.common.util.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdmDivDataGetterImpl implements AdmDivDataGetter {
    @Resource
    private ITbAdmDivService iTbAdmDivService;
    @Resource
    private ITrNotifyDivService iTrNotifyDivService;
    @Override
    public ResultDTO getOneLevelAdmDivNode(String guid) throws Exception {
        QueryWrapper<TbAdmDiv> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("PARENT_GUID", guid);
        queryWrapper.eq("DELETED", IsDeletedEnum.NOTDELETED.getStringValue())
                .eq("ENABLED", IsEnabledEnum.ENABLED.getStringValue())
                .orderByAsc("SORT");
        List<TbAdmDiv> tbAdmDivList = iTbAdmDivService.list(queryWrapper);
        List<TreeNode> treeNodeList = TbAdmDivScene2StructMapper.INSTANCE.toTreeNodeList(tbAdmDivList);
        return ResultDTO.success(treeNodeList);
    }

    @Override
    public ResultDTO getNotifyDefaultCheckedKeys(String notifyGuid) throws Exception {
        List<TrNotifyDiv> trNotifyDivs = iTrNotifyDivService.selectByNotifyGuid(notifyGuid);
        List<String> checkedKeys = this.fetchCheckedCodes(trNotifyDivs);
        return ResultDTO.success(checkedKeys);
    }

    @Override
    public ResultDTO getNotifyDefaultExpandedKeys(String notifyGuid) throws Exception {
        ResultDTO resultDTO = this.getNotifyDefaultCheckedKeys(notifyGuid);
        if(resultDTO.isFailure()){
            return resultDTO;
        }
        Map unDupMap = new HashMap();
        List<String> checkedKeys = (List<String>)resultDTO.getData();
        List<String> expandedKeys = new ArrayList<>();
        for(String code:checkedKeys){
            expandedKeys = this.genExpendedKeys(expandedKeys,code,unDupMap);
        }
        return ResultDTO.success(expandedKeys);
    }

    /**
     * 将一个编码的所有上级树枝都加进来
     * @param expandedKeys
     * @param admDivCode
     * @param unDupMap
     * @return
     * @throws Exception
     */
    private List<String> genExpendedKeys(List<String> expandedKeys,String admDivCode,Map unDupMap) throws Exception{
        List<String> family = this.getFamilyCodesExceptSelf(admDivCode);
        for(String code:family) {
            if (!unDupMap.containsKey(code)) {
                unDupMap.put(code, code);
                expandedKeys.add(code);
            }
        }
        return expandedKeys;
    }

    /**
     * 获取上级编码，除了自己
     * @param admDivCode
     * @return
     */
    private List<String> getFamilyCodesExceptSelf(String admDivCode){
        List<String> family = new ArrayList<>();
        switch (admDivCode.length()){
            case 2:
                family.add(ConstantCommon.ADM_DIV_ROOT_CODE);
                break;
            case 4:
                family.add(ConstantCommon.ADM_DIV_ROOT_CODE);
                family.add(admDivCode.substring(0,2));
                break;
            case 6:
                family.add(ConstantCommon.ADM_DIV_ROOT_CODE);
                family.add(admDivCode.substring(0,2));
                family.add(admDivCode.substring(0,4));
                break;
            case 9:
                family.add(ConstantCommon.ADM_DIV_ROOT_CODE);
                family.add(admDivCode.substring(0,2));
                family.add(admDivCode.substring(0,4));
                family.add(admDivCode.substring(0,6));
                break;
            case 12:
                family.add(ConstantCommon.ADM_DIV_ROOT_CODE);
                family.add(admDivCode.substring(0,2));
                family.add(admDivCode.substring(0,4));
                family.add(admDivCode.substring(0,6));
                family.add(admDivCode.substring(0,9));
                break;
            default:
                break;
        }
        return family;
    }
    @Deprecated
    public ResultDTO getInitCheckedTree(String notifyGuid) throws Exception {
        CheckedLazyTree checkedLazyTree = new CheckedLazyTree();
        List<TrNotifyDiv> trNotifyDivs = iTrNotifyDivService.selectByNotifyGuid(notifyGuid);
        checkedLazyTree.setCheckCodes(this.fetchCheckedCodes(trNotifyDivs));
        /**
         * 如果无已选择的节点，则只需要返回首层节点数据,就不查库了，静态生成吧
         */
        if(CollectionUtils.isEmpty(trNotifyDivs)){
            checkedLazyTree.setTree(this.initTopNode());
            return ResultDTO.success(checkedLazyTree);
        }
        /**
         *  如果第一个已选择节点是 00_全部区划 ,就不查库了，静态生成吧
         * 因为 trNotifyDivs 有序，如果存在 00 ，肯定是第1个
         * **/
        if("00".equals(trNotifyDivs.get(0).getDivCode())){
            checkedLazyTree.setTree(this.initTopNode());
        }
        /**
         * 非全部区划，就需要根据已选择的区划，把相关树枝都查出来
         */
        checkedLazyTree.setTree(this.fetchinitTree(checkedLazyTree.getCheckCodes()));

        return ResultDTO.success(checkedLazyTree);
    }
    @Deprecated
    private List<TreeNode> initTopNode(){
        TreeNode treeNode = new TreeNode();
        treeNode.setId(ConstantCommon.TOP_GUID);
        treeNode.setLabel("全部区划");
        treeNode.setValue(ConstantCommon.ADM_DIV_ROOT_CODE);
        treeNode.setLeaf(false);
        List<TreeNode> treeNodes = new ArrayList<>();
        treeNodes.add(treeNode);
        return treeNodes;
    }

    /**
     * 转为 List<区划编码>
     * @param trNotifyDivs
     * @return
     * @throws Exception
     */
    private List<String> fetchCheckedCodes(List<TrNotifyDiv> trNotifyDivs) throws Exception{
        List<String> codes = new ArrayList<>();
        if(CollectionUtils.isEmpty(trNotifyDivs)){
            return codes;
        }
        for(TrNotifyDiv trNotifyDiv:trNotifyDivs){
            codes.add(trNotifyDiv.getDivCode());
        }
        return codes;
    }

    /**
     * 根据已选择的区划编码集合，生成相关必要树枝
     * @param checkedCodes
     * @return
     */
    @Deprecated
    private List<TreeNode> fetchinitTree(List<String> checkedCodes) throws Exception{
        List<TreeNode> tree = this.initTopNode();
        if(CollectionUtils.isEmpty(checkedCodes)){
            return tree;
        }
        if(ConstantCommon.ADM_DIV_ROOT_CODE.equals(checkedCodes.get(0))){
            return tree;
        }
        TreeAndMapBO treeAndMapBO = new TreeAndMapBO();
        treeAndMapBO.setRoot(tree.get(0));
        Map map = new HashMap();
        map.put(tree.get(0).getValue(),tree.get(0));
        treeAndMapBO.setNodeMap(map);

        for(String admDivCode:checkedCodes){
            treeAndMapBO = this.loopFetchinitTree(treeAndMapBO,admDivCode);
        }
        return tree;
    }

    /**
     * 递归方法，补充一个NODE，所占树枝
     * @param
     * @param admDivCode
     * @return
     */
    @Deprecated
    private TreeAndMapBO loopFetchinitTree(TreeAndMapBO treeAndMapBO,String admDivCode) throws Exception{
        Map map = treeAndMapBO.getNodeMap();
        /**
         * 如果编码已在MAP，该树枝已完整，退回
         */
        if(map.containsKey(admDivCode)){
            return treeAndMapBO;
        }
        String fatherCode = this.getFatherCode(admDivCode);
        if (!map.containsKey(fatherCode)) {
            treeAndMapBO = this.loopFetchinitTree(treeAndMapBO, fatherCode);
        }
        /** 如果上级已存在，需要装入本层级 **/
        TreeNode fatherNode = (TreeNode) map.get(fatherCode);
        if (fatherNode == null) {
            throw new Exception("行政区划数据不规范。");
        }
        ResultDTO resultDTO = this.getOneLevelAdmDivNode(fatherNode.getId());
        if (resultDTO.isFailure()) {
            treeAndMapBO.getRoot().setChildren(new ArrayList<>());
        }
        fatherNode.setChildren((List<TreeNode>) resultDTO.getData());
        for (TreeNode node : fatherNode.getChildren()) {
            map.put(node.getValue(), node);
        }
        return treeAndMapBO;
    }

    /**
     * 根据一个编码，得到他上级编码
     * 区划编码层次 22233 省市县乡村
     * @param admDivCode
     * @return
     */
    @Override
    public String getFatherCode(String admDivCode) throws Exception{
        switch (admDivCode.length()){
            case 2:
                return ConstantCommon.ADM_DIV_ROOT_CODE;
            case 4:
                return admDivCode.substring(0,2);
            case 6:
                return admDivCode.substring(0,4);
            case 9:
                return admDivCode.substring(0,6);
            case 12:
                return admDivCode.substring(0,9);
            default:
                break;
        }
        throw new Exception("行政区划编码长度不符合规范。");
    }
}
