/**
* @author 张三丰
* @since 2022-11-11 14:42:10
*  js api 相关代码生成接口
*/
import request from '@/utils/request'

//生成对单层次单表的基本操作JsApi源码
export function generateScene4JsApiSource(data) {
  return request({
    url: '/api/system/auth/generator/scene4/js/api/source',
    method: 'post',
    data
  })
}

//生成对单层次单表的基本操作JsApi文件
export function generateScene4JsApiFile(data) {
  return request({
    url: '/api/system/auth/generator/scene4/js/api/file',
    method: 'post',
    data
  })
}