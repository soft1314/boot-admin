package ${entity.packageUrl};

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
* 本文件自动生成，严禁人工修改
* ${entity.tableComment}实体类
* </p>
*
* @author ${entity.author}
* @since ${.now}
*/

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("${entity.tableName}")
@ApiModel(description = "${entity.tableComment}实体类")
public class ${entity.entityName} implements Serializable {

    private static final long serialVersionUID = 1L;
<#if entity.primaryKey??>
<#if entity.primaryKey.comments??>
    /**
    * ${entity.primaryKey.comments}
    */
</#if>
    @TableId(value = "${entity.primaryKey.columnName}", type = IdType.ASSIGN_ID)
    private ${entity.primaryKey.dataType} ${entity.primaryKey.classAttribute};
</#if>
<#if entity.nonPkColumns??>
<#--循环生成变量-->
    <#list entity.nonPkColumns as col>
        <#if col.comments??>
    /**
     *  ${col.comments}
     */
        </#if>
        <#if col.classAttribute == 'version'>
    @Version
    @TableField(value = "${col.columnName}" ,fill = FieldFill.INSERT)
    private Integer ${col.classAttribute};
        <#elseif col.classAttribute == 'createTime'>
    @TableField(value = "${col.columnName}" ,fill = FieldFill.INSERT)
    private ${col.dataType} ${col.classAttribute};
        <#elseif col.classAttribute == 'createBy'>
    @TableField(value = "${col.columnName}" ,fill = FieldFill.INSERT)
    private ${col.dataType} ${col.classAttribute};
        <#elseif col.classAttribute == 'modifyTime'>
    @TableField(value = "${col.columnName}" ,fill = FieldFill.INSERT_UPDATE)
    private ${col.dataType} ${col.classAttribute};
        <#elseif col.classAttribute == 'modifyBy'>
    @TableField(value = "${col.columnName}" ,fill = FieldFill.INSERT_UPDATE)
    private ${col.dataType} ${col.classAttribute};
        <#else >
    @TableField("${col.columnName}")
    private ${col.dataType} ${col.classAttribute};
        </#if>
    </#list>
</#if>
}