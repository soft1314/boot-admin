/**
* @author 张三丰
* @since 2022-11-8 22:42:10
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

// 生成VO源码
export function generateScene3GlobalTransactionalSource(data) {
  return request({
    url: '/api/system/auth/generator/scene3/globaltransactional/source',
    method: 'post',
    data
  })
}

// 生成VO文件
export function generateScene3GlobalTransactionalFile(data) {
  return request({
    url: '/api/system/auth/generator/scene3/globaltransactional/file',
    method: 'post',
    data
  })
}
