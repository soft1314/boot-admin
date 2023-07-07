package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.mapper.mybatis.UserTableColumnMasterGetter;
import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.service.component.MessageUtils;
import com.qiyuan.base2048.service.generator.BasicTableChecker;
import com.qiyuan.base2048.service.generator.TableChecker;
import com.qiyuan.bautil.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service("Scene3TableCheckerImpl")
public class Scene3TableCheckerImpl implements TableChecker {
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

        boolean isFlowStatusColumnCheckPassed = false;
        boolean isTitleColumnCheckPassed = false;

        for(UserTableColumnResultDTO userTableColumnResultDTO:userTableColumnResultDTOList){
            /** 有一个通过，即全部通过 **/
            if(!isFlowStatusColumnCheckPassed) {
                if(checkFlowStatusColumn(userTableColumnResultDTO)){
                    isFlowStatusColumnCheckPassed = true;
                }
            }
            if(!isTitleColumnCheckPassed) {
                if(checkTitleColumn(userTableColumnResultDTO)){
                    isTitleColumnCheckPassed = true;
                }
            }
        }
        Boolean passed = true;
        StringBuffer stringBuffer = new StringBuffer();
        if(!sourceCodeVO.getPassed().booleanValue()) {
            stringBuffer.append(sourceCodeVO.getSourceCode());
        }
        if(!isFlowStatusColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene3.check.table.col.flowstatus.failed")).append("\r\n");
        }
        if(!isTitleColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene3.check.table.col.title.failed")).append("\r\n");
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
     * 检查 流转状态 字段
     * 表需包括 FLOW_STATUS 字段
     * 名为 FLOW_STATUS，串类型，长度2,非空 流转状态;FLOW_STATUS
     * @return
     */
    private boolean checkFlowStatusColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("FLOW_STATUS".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().toUpperCase().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().toUpperCase().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength().compareTo(new BigDecimal("2")) == 0){
                    if("N".equals(userTableColumnResultDTO.getNullable())) {
                        if("流转状态;FLOW_STATUS".equals(userTableColumnResultDTO.getComments())) {
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
     * 检查 标题 字段
     * 表需包括 标题 字段
     * 名为 TITLE，串类型，长度大于50,非空 标题
     * @return
     */
    private boolean checkTitleColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("TITLE".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().toUpperCase().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().toUpperCase().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength().compareTo(new BigDecimal("50")) >= 0){
                    if("N".equals(userTableColumnResultDTO.getNullable())) {
                        if("标题".equals(userTableColumnResultDTO.getComments())) {
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
