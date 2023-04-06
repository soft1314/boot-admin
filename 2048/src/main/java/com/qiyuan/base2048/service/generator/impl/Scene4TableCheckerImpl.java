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
import java.util.List;

@Service("Scene4TableCheckerImpl")
public class Scene4TableCheckerImpl implements TableChecker {
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
        boolean isProcessInstanceIdColumnCheckPassed = false;
        boolean isProcessDefinitionIdColumnCheckPassed = false;

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
            if(!isProcessDefinitionIdColumnCheckPassed){
                if(checkProcessDefinitionIdColumn(userTableColumnResultDTO)){
                    isProcessDefinitionIdColumnCheckPassed = true;
                }
            }
            if(!isProcessInstanceIdColumnCheckPassed){
                if(checkProcessInstanceIdKeyColumn(userTableColumnResultDTO)){
                    isProcessInstanceIdColumnCheckPassed = true;
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
            stringBuffer.append(MessageUtils.get("scene4.check.table.col.flowstatus.failed")).append("\r\n");
        }
        if(!isTitleColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene4.check.table.col.title.failed")).append("\r\n");
        }
        if(!isProcessDefinitionIdColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene4.check.table.col.processDefinitionId.failed")).append("\r\n");
        }
        if(!isProcessInstanceIdColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene4.check.table.col.processInstanceId.failed")).append("\r\n");
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
            if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength() == 2){
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
            if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength() >= 50){
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

    /**
     * processInstanceId 字段，允许空，varchar 38
     * 启动流程时生成，并存入业务表
     * @param userTableColumnResultDTO
     * @return
     */
    private boolean checkProcessInstanceIdKeyColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("PROCESS_INSTANCE_ID".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength() == 38){
                    if("Y".equals(userTableColumnResultDTO.getNullable())) {
                        if("流程实例ID".equals(userTableColumnResultDTO.getComments())) {
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
     * PROCESS_DEFINITION_ID 启动流程时生成，并存入业务表
     * 格式：process1:9:8b3f74e0-c7f5-11ed-8841-4c77cb6b51e3
     * @param userTableColumnResultDTO
     * @return
     */
    private boolean checkProcessDefinitionIdColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("PROCESS_DEFINITION_ID".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength() == 100){
                    if("Y".equals(userTableColumnResultDTO.getNullable())) {
                        if("流程定义ID".equals(userTableColumnResultDTO.getComments())) {
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
