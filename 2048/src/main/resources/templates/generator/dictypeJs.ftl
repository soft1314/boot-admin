/**
* 本文件自动生成，严禁人工修改
* @author ${dicTypeJs.author}
* @since ${.now}
*/
const ${dicTypeJs.name} = {
<#list dicTypeJs.types as dicType>
    //${dicType.name}
    ${dicType.varName}(){
      return '${dicType.code}'
    },
</#list>
}
export default commonDicType
