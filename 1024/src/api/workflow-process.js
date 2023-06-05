/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*  tg-project表持久化接口
*/
import request from '@/utils/request'

// 分页获取数据表列表(最新版本)
export function fetchLastVersionProcessPage(data) {
  return request({
    url: '/api/workflow/auth/activiti/process/page',
    method: 'post',
    data
  })
}
// 分页获取数据表列表(所有版本)
export function fetchAllProcessPage(data) {
  return request({
    url: '/api/workflow/auth/activiti/process/page/all',
    method: 'post',
    data
  })
}
// 创建新模型
export function saveNewProcess(data) {
  return request({
    url: '/api/workflow/auth/activiti/process/add',
    method: 'post',
    data
  })
}

// 删除数据
export function delProcess(data) {
  return request({
    url: '/api/workflow/auth/activiti/process/del',
    method: 'post',
    data
  })
}

// 转为模型
export function deployModel(data) {
  return request({
    url: '/api/workflow/auth/activiti/process/deploy',
    method: 'post',
    data
  })
}

// 强行终止流程
export function stopProcessInstanceById(processInstanceId, taskId, businessTable, businessId) {
  return request({
    url: '/api/workflow/auth/activiti/process/destroy',
    method: 'get',
    params: { processInstanceId, taskId, businessTable, businessId }
  })
}
