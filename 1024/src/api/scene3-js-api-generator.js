/**
* @author 张三丰
* @since 2000-08-20
*  js api 相关代码生成接口
*/
import request from '@/utils/request'

// 生成对剧情3操作JsApi源码
export function generateScene3JsApiSource(data) {
  return request({
    url: '/api/system/auth/generator/scene3/js/api/source',
    method: 'post',
    data
  })
}

// 生成对剧情3操作JsApi文件
export function generateScene3JsApiFile(data) {
  return request({
    url: '/api/system/auth/generator/scene3/js/api/file',
    method: 'post',
    data
  })
}
