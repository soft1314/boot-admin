/**
* @author 张三丰
* @since 2022-11-18 22:12:10
*  规范检查 相关代码生成接口
*/
import request from '@/utils/request'

// 对数据表进行规范检查
export function generateScene3TableCheckResult(data) {
  return request({
    url: '/api/system/auth/generator/scene3/check/table',
    method: 'post',
    data
  })
}

// 获取剧本3情节
export function generateScene3Story(data) {
  return request({
    url: '/api/system/auth/generator/scene3/check/story',
    method: 'post',
    data
  })
}
