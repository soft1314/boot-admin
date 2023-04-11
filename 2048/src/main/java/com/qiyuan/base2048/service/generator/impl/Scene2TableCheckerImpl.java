package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.generator.BasicTableChecker;
import com.qiyuan.base2048.service.generator.TableChecker;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service("Scene2TableCheckerImpl")
public class Scene2TableCheckerImpl implements TableChecker {
    @Value("${base2048.table-schema}")
    private String tableSchema;
    @Resource
    private UserTableColumnMasterGetter userTableColumnMasterGetter;
    @Resource
    private BasicTableChecker basicTableChecker;

    @Override
    public ResultDTO checkTable(String tableName) throws Exception {
        List<UserTableColumnResultDTO> userTableColumnResultDTOList = userTableColumnMasterGetter.selectUserTableColumn(tableSchema,tableName);
        ResultDTO resultDTO = basicTableChecker.checkCols(userTableColumnResultDTOList);
        SourceCodeVO sourceCodeVO = (SourceCodeVO)resultDTO.getData();

        boolean isParentGuidColumnCheckPassed = false;
        boolean isLeafColumnCheckPassed = false;
        boolean isSortColumnCheckPassed = false;

        for(UserTableColumnResultDTO userTableColumnResultDTO:userTableColumnResultDTOList){
            /** 有一个通过，即全部通过 **/
            if(!isParentGuidColumnCheckPassed) {
                if(checkParentGuidColumn(userTableColumnResultDTO)){
                    isParentGuidColumnCheckPassed = true;
                }
            }
            if(!isLeafColumnCheckPassed) {
                if(checkLeafColumn(userTableColumnResultDTO)){
                    isLeafColumnCheckPassed = true;
                }
            }
            if(!isSortColumnCheckPassed) {
                if(checkSortColumn(userTableColumnResultDTO)){
                    isSortColumnCheckPassed = true;
                }
            }
        }
        Boolean passed = true;
        StringBuffer stringBuffer = new StringBuffer();
        if(!sourceCodeVO.getPassed().booleanValue()) {
            stringBuffer.append(sourceCodeVO.getSourceCode());
        }
        if(!isParentGuidColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene2.check.table.col.parentguid.failed")).append("\r\n");
        }
        if(!isLeafColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene2.check.table.col.leaf.failed")).append("\r\n");
        }
        if(!isSortColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene2.check.table.col.sort.failed")).append("\r\n");
        }
        /** 利用 SourceCodeVO 返回结果 **/
        sourceCodeVO.setFileName("");
        if(passed && sourceCodeVO.getPassed().booleanValue()){
            sourceCodeVO.setSourceCode("检查通过。");
        }else{
            sourceCodeVO.setSourceCode(stringBuffer.toString());
        }
        return ResultDTO.success(sourceCodeVO);
    }

    /**
     * 检查 记录父数据GUID 字段
     * 表需包括 记录父数据GUID 字段
     * 名为 PARENT_GUID，串类型，长度38,非空 父级GUID
     * @return
     */
    private boolean checkParentGuidColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("PARENT_GUID".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().toUpperCase().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().toUpperCase().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength().compareTo(new BigDecimal("38")) == 0){
                    if("N".equals(userTableColumnResultDTO.getNullable())) {
                        if("父级GUID".equals(userTableColumnResultDTO.getComments())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
    /**
     * 检查 叶子 字段
     * 表需包括 叶子 字段
     * 名为 LEAF，串类型，长度1,非空 是否叶子;YESNO
     * @return
     */
    private boolean checkLeafColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("LEAF".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().toUpperCase().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().toUpperCase().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength().compareTo(new BigDecimal("1")) == 0){
                    if("N".equals(userTableColumnResultDTO.getNullable())) {
                        if("是否末级;YESNO".equals(userTableColumnResultDTO.getComments())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
    /**
     * 检查 排序 字段
     * 表需包括 顺序号 字段
     * 名为 SORT，整数NUMBER，长度22,非空 是否叶顺序号
     * @return
     */
    private boolean checkSortColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("SORT".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().toUpperCase().equals("NUMBER")){
                if(userTableColumnResultDTO.getDataLength().equals("22")){
                    if("N".equals(userTableColumnResultDTO.getNullable())) {
                        if("顺序号".equals(userTableColumnResultDTO.getComments())) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
}
