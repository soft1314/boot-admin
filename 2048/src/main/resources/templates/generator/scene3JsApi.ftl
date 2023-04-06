/**
* 本文件自动生成，严禁人工修改
* @author ${dataContainer.author}
* @since ${.now}
* 表持久化接口
*/
import request from '@/utils/request'

//插入数据并启动流程
export function save${dataContainer.uppercaseHump}AndStartProcess(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene3/start',
    method: 'post',
    data
  })
}
//修改数据并完成任务
export function save${dataContainer.uppercaseHump}AndCompleteTask(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene3/task/complete',
    method: 'post',
    data
  })
}
//按GUID查询一条
export function fetch${dataContainer.uppercaseHump}Info(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene3/info',
    method: 'post',
    data
  })
}
