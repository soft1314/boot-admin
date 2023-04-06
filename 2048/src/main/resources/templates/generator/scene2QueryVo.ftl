package ${entity.packageUrl};

import com.qiyuan.bautil.dto.BasePageQueryVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(description = "${entity.tableComment}实体类")
public class ${entity.entityName}Scene2QueryVO extends BasePageQueryVO {

<#if entity.primaryKey??>
    <#if entity.primaryKey.comments??>
        /**
        * ${entity.primaryKey.comments}
        */
    </#if>
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
        private ${col.dataType} ${col.classAttribute};
    </#list>
</#if>
}