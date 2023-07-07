/**
* 本文件自动生成，严禁人工修改
* @author ${dataContainer.author}
* @since ${.now}
*  tg-project表持久化接口
*/
import request from '@/utils/request'

//分页获取数据表列表
export function fetch${dataContainer.uppercaseHump}Page(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene4/table/page',
    method: 'post',
    data
  })
}

//保存数据
export function save${dataContainer.uppercaseHump}(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene4/save',
    method: 'post',
    data
  })
}

//删除数据
export function del${dataContainer.uppercaseHump}(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene4/del',
    method: 'post',
    data
  })
}
//保存数据并启动流程
export function saveAndStartProcess${dataContainer.uppercaseHump}(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene4/save-start',
    method: 'post',
    data
  })
}
//修改数据并完成任务
export function save${dataContainer.uppercaseHump}AndCompleteTask(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene4/task/complete',
    method: 'post',
    data
  })
}
//按GUID查询一条
export function fetch${dataContainer.uppercaseHump}Info(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene4/info',
    method: 'post',
    data
  })
}
