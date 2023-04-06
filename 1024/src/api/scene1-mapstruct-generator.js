/**
* @author 张三丰
* @since 2022-11-8 14:42:10
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

//生成VO源码
export function generateScene1MapstructSource(data) {
  return request({
    url: '/api/system/auth/generator/scene1/mapstruct/source',
    method: 'post',
    data
  })
}

//生成VO文件
export function generateScene1MapstructFile(data) {
  return request({
    url: '/api/system/auth/generator/scene1/mapstruct/file',
    method: 'post',
    data
  })
}
