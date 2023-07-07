/**
* 本文件自动生成，严禁人工修改
* @author ${api.author}
* @since ${.now}
<#if api.apiMessage??>
*  ${api.apiMessage}
</#if>
*/
import request from '@/utils/request'

<#list api.mappingDTOS as method>
    <#if method.mothed == 'POST'>
        <#if method.apiOperationMessage??>
//${method.apiOperationMessage}
        </#if>
export function ${method.name}(data) {
  return request({
    url: '${api.uri}${method.uri}',
    method: 'post',
    data
  })
}
    </#if>

    <#if method.mothed == 'GET'>
        <#if method.apiOperationMessage??>
//${method.apiOperationMessage}
        </#if>
export function ${method.name}() {
  return request({
    url: '${api.uri}${method.uri}',
    method: 'get'
  })
}
    </#if>
</#list>