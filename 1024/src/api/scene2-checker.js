/**
* @author 张三丰
* @since 2022-11-18 22:12:10
*  规范检查 相关代码生成接口
*/
import request from '@/utils/request'

// 对数据表进行规范检查
export function generateScene2TableCheckResult(data) {
  return request({
    url: '/api/system/auth/generator/scene2/check/table',
    method: 'post',
    data
  })
}

// 获取剧本1情节
export function generateScene2Story(data) {
  return request({
    url: '/api/system/auth/generator/scene2/check/story',
    method: 'post',
    data
  })
}
