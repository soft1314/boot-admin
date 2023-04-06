/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2023-3-30 15:14:03
*  tg-project表持久化接口
*/
import request from '@/utils/request'

//分页获取数据表列表
export function fetchDemoExpensePage(data) {
  return request({
    url: '/api/system/auth/demo-expense/scene4/table/page',
    method: 'post',
    data
  })
}

//保存数据
export function saveDemoExpense(data) {
  return request({
    url: '/api/system/auth/demo-expense/scene4/save',
    method: 'post',
    data
  })
}

//删除数据
export function delDemoExpense(data) {
  return request({
    url: '/api/system/auth/demo-expense/scene4/del',
    method: 'post',
    data
  })
}
//保存数据并启动流程
export function saveAndStartProcessDemoExpense(data) {
  return request({
    url: '/api/system/auth/demo-expense/scene4/save-start',
    method: 'post',
    data
  })
}
//修改数据并完成任务
export function saveDemoExpenseAndCompleteTask(data) {
  return request({
    url: '/api/system/auth/demo-expense/scene4/task/complete',
    method: 'post',
    data
  })
}
//按GUID查询一条
export function fetchDemoExpenseInfo(data) {
  return request({
    url: '/api/system/auth/demo-expense/scene4/info',
    method: 'post',
    data
  })
}
