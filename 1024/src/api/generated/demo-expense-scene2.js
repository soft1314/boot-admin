/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*  tg-project表持久化接口
*/
import request from '@/utils/request'

// 分页获取顶层数据表列表
export function fetchTopDemoExpensePage(data) {
  return request({
    url: '/api/system/auth/demo-expense/scene2/top/page',
    method: 'post',
    data
  })
}
// 分页获取数据表列表
export function fetchDemoExpensePage(data) {
  return request({
    url: '/api/system/auth/demo-expense/scene2/page',
    method: 'post',
    data
  })
}
// 获取指定GUID数据的下一层全部数据列表
export function fetchDemoExpenseAllChildren(guid) {
  return request({
    url: '/api/system/auth/demo-expense/scene2/children/all',
    method: 'get',
    params: { guid }
  })
}
// 保存数据
export function saveDemoExpense(data) {
  return request({
    url: '/api/system/auth/demo-expense/scene2/save',
    method: 'post',
    data
  })
}
// 删除数据
export function delDemoExpense(data) {
  return request({
    url: '/api/system/auth/demo-expense/scene2/del',
    method: 'post',
    data
  })
}
