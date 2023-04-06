package com.qiyuan.base2048.service.generator.impl;

import com.qiyuan.base2048.pojo.vo.result.SourceCodeVO;
import com.qiyuan.base2048.pojo.vo.vuecomponent.UserTableColumnResultDTO;
import com.qiyuan.base2048.service.Component.MessageUtils;
import com.qiyuan.base2048.service.generator.BasicTableChecker;
import com.qiyuan.bautil.dto.ResultDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicTableCheckerImpl implements BasicTableChecker {


    @Override
    public ResultDTO checkCols(List<UserTableColumnResultDTO> userTableColumnResultDTOList) throws Exception {
        boolean isColumnNameCheckFailed = false;

        boolean isPrimaryKeyCheckPassed = false;
        boolean isCreateByColumnCheckPassed = false;
        boolean isModifyByColumnCheckPassed = false;
        boolean isCreateTimeColumnCheckPassed = false;
        boolean isModifyTimeColumnCheckPassed = false;
        boolean isDatestampColumnCheckPassed = false;
        boolean isEnabledColumnCheckPassed = false;
        boolean isDeletedColumnCheckPassed = false;
        boolean isVersionColumnCheckPassed = false;
        boolean isRemarksColumnCheckPassed = false;
        boolean isTenantIdColumnCheckPassed = false;

        for(UserTableColumnResultDTO userTableColumnResultDTO:userTableColumnResultDTOList){
            /** 有一个不能过，就全部不通过 **/
            if(!isColumnNameCheckFailed) {
                if(!checkColumnName(userTableColumnResultDTO)){
                    isColumnNameCheckFailed = true;
                }
            }

            /** 有一个通过，即全部通过 **/
            if(!isPrimaryKeyCheckPassed) {
                if(checkPrimaryKey(userTableColumnResultDTO)){
                    isPrimaryKeyCheckPassed = true;
                }
            }
            if(!isCreateByColumnCheckPassed){
                if(checkCreateByColumn(userTableColumnResultDTO)){
                    isCreateByColumnCheckPassed = true;
                }
            }
            if(!isModifyByColumnCheckPassed){
                if(checkModifyByColumn(userTableColumnResultDTO)){
                    isModifyByColumnCheckPassed = true;
                }
            }
            if(!isCreateTimeColumnCheckPassed){
                if(checkCreateTimeColumn(userTableColumnResultDTO)){
                    isCreateTimeColumnCheckPassed = true;
                }
            }
            if(!isModifyTimeColumnCheckPassed){
                if(checkModifyTimeColumn(userTableColumnResultDTO)){
                    isModifyTimeColumnCheckPassed = true;
                }
            }
            if(!isDatestampColumnCheckPassed){
                if(checkDatestampColumn(userTableColumnResultDTO)){
                    isDatestampColumnCheckPassed = true;
                }
            }
            if(!isEnabledColumnCheckPassed){
                if(checkEnabledColumn(userTableColumnResultDTO)){
                    isEnabledColumnCheckPassed = true;
                }
            }
            if(!isDeletedColumnCheckPassed){
                if(checkDeletedColumn(userTableColumnResultDTO)){
                    isDeletedColumnCheckPassed = true;
                }
            }
            if(!isVersionColumnCheckPassed){
                if(checkVersionColumn(userTableColumnResultDTO)){
                    isVersionColumnCheckPassed = true;
                }
            }
            if(!isRemarksColumnCheckPassed){
                if(checkRemarksColumn(userTableColumnResultDTO)){
                    isRemarksColumnCheckPassed = true;
                }
            }
            if(!isTenantIdColumnCheckPassed){
                if(checkTenantIdColumn(userTableColumnResultDTO)){
                    isTenantIdColumnCheckPassed = true;
                }
            }
        }
        Boolean passed = true;
        StringBuffer stringBuffer = new StringBuffer();
        if(isColumnNameCheckFailed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.failed")).append("\r\n");
        }
        if(!isPrimaryKeyCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.key.failed")).append("\r\n");
        }
        if(!isCreateByColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.createby.failed")).append("\r\n");
        }
        if(!isModifyByColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.modifyby.failed")).append("\r\n");
        }
        if(!isCreateTimeColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.createtime.failed")).append("\r\n");
        }
        if(!isModifyTimeColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.modifytime.failed")).append("\r\n");
        }
        if(!isDatestampColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.datestamp.failed")).append("\r\n");
        }
        if(!isEnabledColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.enabled.failed")).append("\r\n");
        }
        if(!isDeletedColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.deleted.failed")).append("\r\n");
        }
        if(!isVersionColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.version.failed")).append("\r\n");
        }
        if(!isRemarksColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.remarks.failed")).append("\r\n");
        }
        if(!isTenantIdColumnCheckPassed){
            passed = false;
            stringBuffer.append(MessageUtils.get("scene1.check.table.col.tenantid.failed")).append("\r\n");
        }
        /** 利用 SourceCodeVO 返回结果 **/
        SourceCodeVO sourceCodeVO = new SourceCodeVO();
        sourceCodeVO.setFileName("");
        if(passed){
            sourceCodeVO.setPassed(true);
            sourceCodeVO.setSourceCode("检查通过。");
        }else{
            sourceCodeVO.setPassed(false);
            sourceCodeVO.setSourceCode(stringBuffer.toString());
        }
        return ResultDTO.success(sourceCodeVO);
    }
    /**
     * 字段名称不允许以 IS_ SET_ GET_ 等开头
     * 因为实体类生成时，IS_XXX 会转成 isXxx,而 阿里规约中“【强制】POJO 类中布尔类型的变量，都不要加 is，否则部分框架解析会引起序列化错误。”
     * 虽然其建表规约  要求使用 is_xxx 形式，这是也不行，不理解为什么前后冲突
     * 字段描述非空
     * @return
     */
    private boolean checkColumnName(UserTableColumnResultDTO userTableColumnResultDTO){
        if(userTableColumnResultDTO.getColumnName().startsWith("IS_") || userTableColumnResultDTO.getColumnName().startsWith("SET_") || userTableColumnResultDTO.getColumnName().startsWith("GET_")){
            return false;
        }
        if(StringUtils.isBlank(userTableColumnResultDTO.getComments())){
            return false;
        }
        return true;
    }
    /**
     * 主键检查
     * 表需包括主键
     * 名为 GUID，串类型，长度38,非空
     * @return
     */
    private boolean checkPrimaryKey(UserTableColumnResultDTO userTableColumnResultDTO){
        if("GUID".equals(userTableColumnResultDTO.getColumnName())){
            if("Y".equals(userTableColumnResultDTO.getPrimaryKey())){
                if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                    if(userTableColumnResultDTO.getCharLength() == 38){
                        if("N".equals(userTableColumnResultDTO.getNullable())) {
                            if("主键".equals(userTableColumnResultDTO.getComments())) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
    /**
     * 检查 记录创建人字段
     * 表需包括创建人字段
     * 名为 CREATE_BY，串类型，长度100,非空，记录创建者
     * @return
     */
    private boolean checkCreateByColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("CREATE_BY".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength() == 100){
                    if("N".equals(userTableColumnResultDTO.getNullable())) {
                        if("记录创建者".equals(userTableColumnResultDTO.getComments())) {
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
     * 检查 记录修改人字段
     * 表需包括修改人字段
     * 名为 MODIFY_BY，串类型，长度100,非空 记录最后修改者
     * @return
     */
    private boolean checkModifyByColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("MODIFY_BY".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength() == 100){
                    if("N".equals(userTableColumnResultDTO.getNullable())) {
                        if("记录最后修改者".equals(userTableColumnResultDTO.getComments())) {
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
     * 检查 记录创建时间字段
     * 表需包括创建时间字段
     * 名为 CREATE_TIME，日期类型,非空 时间戳
     * @return
     */
    private boolean checkCreateTimeColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("CREATE_TIME".equals(userTableColumnResultDTO.getColumnName())){
            if("DATE".equals(userTableColumnResultDTO.getDataType())){
                if("N".equals(userTableColumnResultDTO.getNullable())) {
                    if("记录创建时间".equals(userTableColumnResultDTO.getComments())) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
    /**
     * 检查 记录修改时间字段
     * 表需包括修改时间字段
     * 名为 MODIFY_TIME，日期类型,非空
     * @return
     */
    private boolean checkModifyTimeColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("MODIFY_TIME".equals(userTableColumnResultDTO.getColumnName())){
            if("DATE".equals(userTableColumnResultDTO.getDataType())){
                if("N".equals(userTableColumnResultDTO.getNullable())) {
                    if("记录最后修改时间".equals(userTableColumnResultDTO.getComments())) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
    /**
     * 检查 记录时间戳字段
     * 表需包括时间戳字段
     * 名为 DATESTAMP，日期类型 缺省值 sysdate,非空
     * 规范要求：java不修改该字段，完全由数据库触发器修改，即数据库级的时间戳
     * @return
     */
    private boolean checkDatestampColumn(UserTableColumnResultDTO userTableColumnResultDTO) {
        if ("DATESTAMP".equals(userTableColumnResultDTO.getColumnName())) {
            if("DATE".equals(userTableColumnResultDTO.getDataType())){
                if ("N".equals(userTableColumnResultDTO.getNullable())) {
                    if ("时间戳".equals(userTableColumnResultDTO.getComments())) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
    /**
     * 检查 记录启用状态字段
     * 表需包括启用状态字段
     * 名为 ENABLED，串类型，长度1,描述：启用状态;ENABLED,非空
     * @return
     */
    private boolean checkEnabledColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("ENABLED".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength() == 1){
                    if("启用状态;ENABLED".equals(userTableColumnResultDTO.getComments())){
                        if("N".equals(userTableColumnResultDTO.getNullable())) {
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
     * 检查 记录删除状态字段
     * 表需包括删除状态字段
     * 名为 DELETED，串类型，长度1,描述：删除状态;DELETED,非空
     * @return
     */
    private boolean checkDeletedColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("DELETED".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR")||userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength() == 1){
                    if("删除状态;DELETED".equals(userTableColumnResultDTO.getComments())){
                        if("N".equals(userTableColumnResultDTO.getNullable())) {
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
     * 检查 记录乐观锁字段
     * 表需包括乐观锁字段
     * 名为 VERSION，NUMBER(22,0)类型，长度1,描述：乐观锁,非空
     * @return
     */
    private boolean checkVersionColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("VERSION".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().equals("NUMBER")){
                if(userTableColumnResultDTO.getDataLength().equals("22") && userTableColumnResultDTO.getDataScale().equals("0")){
                    if("乐观锁".equals(userTableColumnResultDTO.getComments())){
                        if("N".equals(userTableColumnResultDTO.getNullable())) {
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
     * 检查 记录备注字段
     * 表需包括备注字段
     * 名为 REMARKS，串类型，长度900以上,描述：备注,非空
     * @return
     */
    private boolean checkRemarksColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("REMARKS".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR") || userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength() >= 900){
                    if("备注".equals(userTableColumnResultDTO.getComments())){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
    /**
     * 检查 记录租户ID字段
     * 表需包括租户ID字段
     * 名为 TENANT_ID_，串类型，长度38,描述：租户ID,非空
     * @return
     */
    private boolean checkTenantIdColumn(UserTableColumnResultDTO userTableColumnResultDTO){
        if("TENANT_ID_".equals(userTableColumnResultDTO.getColumnName())){
            if(userTableColumnResultDTO.getDataType().startsWith("VARCHAR") || userTableColumnResultDTO.getDataType().startsWith("NVARCHAR")){
                if(userTableColumnResultDTO.getCharLength() == 38){
                    if("租户ID".equals(userTableColumnResultDTO.getComments())){
                        if("N".equals(userTableColumnResultDTO.getNullable())) {
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
