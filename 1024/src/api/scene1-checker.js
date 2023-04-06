/**
* @author 张三丰
* @since 2022-11-11 14:42:10
*  规范检查 相关代码生成接口
*/
import request from '@/utils/request'

//对数据表进行规范检查
export function generateScene1TableCheckResult(data) {
  return request({
    url: '/api/system/auth/generator/scene1/check/table',
    method: 'post',
    data
  })
}

//获取剧本1情节
export function generateScene1Story(data) {
  return request({
    url: '/api/system/auth/generator/scene1/check/story',
    method: 'post',
    data
  })
}
