/**
* @author 张三丰
* @since 2022-11-8 22:42:10
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

// 生成saveVO源码
export function generateScene2DataSaveVoSource(data) {
  return request({
    url: '/api/system/auth/generator/scene2/vo/data/save/source',
    method: 'post',
    data
  })
}

// 生成saveVO文件
export function generateScene2DataSaveVoFile(data) {
  return request({
    url: '/api/system/auth/generator/scene2/vo/data/save/file',
    method: 'post',
    data
  })
}
// 生成readVO源码
export function generateScene2DataRespVoSource(data) {
  return request({
    url: '/api/system/auth/generator/scene2/vo/data/resp/source',
    method: 'post',
    data
  })
}

// 生成readVO文件
export function generateScene2DataRespVoFile(data) {
  return request({
    url: '/api/system/auth/generator/scene2/vo/data/resp/file',
    method: 'post',
    data
  })
}
