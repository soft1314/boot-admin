/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2023-2-27 17:01:58
*
*/
import request from '@/utils/request'

//表单路由
export function fetchFormKey(procDefId) {
  return request({
    url: '/api/workflow/auth/activiti/task/formkey',
    method: 'get',
    params: { procDefId }
  })
}
//获取待办任务
export function fetchTodoPage(data) {
  return request({
    url: '/api/workflow/auth/activiti/task/todo',
    method: 'post',
    data
  })
}
//获取已办任务
export function fetchHisPage(data) {
  return request({
    url: '/api/workflow/auth/activiti/task/his',
    method: 'post',
    data
  })
}

//获取业务数据
export function fetchBusinessVarsByTaskId(data) {
  return request({
    url: '/api/workflow/auth/activiti/task/business/vars',
    method: 'post',
    data
  })
}

//审核
export function reviewTask(data) {
  return request({
    url: '/api/workflow/auth/activiti/task/review',
    method: 'post',
    data
  })
}

//获取所有人的待办任务（在途）管理员用
export function fetchAllTodoPage(data) {
  return request({
    url: '/api/workflow/auth/activiti/task/todo/all',
    method: 'post',
    data
  })
}
//获取所有人的已办任务（管理员用）
export function fetchAllHisPage(data) {
  return request({
    url: '/api/workflow/auth/activiti/task/his/all',
    method: 'post',
    data
  })
}
//获取流程历史记录
export function fetchFlowLog(data) {
  return request({
    url: '/api/workflow/auth/activiti/task/log',
    method: 'post',
    data
  })
}

//获取可撤回任务
export function fetchRecallPage(data) {
  return request({
    url: '/api/workflow/auth/activiti/task/recall/page',
    method: 'post',
    data
  })
}
