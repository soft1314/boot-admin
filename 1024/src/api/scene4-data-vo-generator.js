/**
* @author 张三丰
* @since 2000-08-20
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

// 生成VO源码
export function generateScene4DataVoSource(data) {
  return request({
    url: '/api/system/auth/generator/scene4/vo/data/source',
    method: 'post',
    data
  })
}

// 生成VO文件
export function generateScene4DataVoFile(data) {
  return request({
    url: '/api/system/auth/generator/scene4/vo/data/file',
    method: 'post',
    data
  })
}
