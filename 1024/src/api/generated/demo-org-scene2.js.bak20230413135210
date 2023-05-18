/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2023-4-13
*  tg-project表持久化接口
*/
import request from '@/utils/request'

// 分页获取顶层数据表列表
export function fetchTopDemoOrgPage(data) {
  return request({
    url: '/api/system/auth/demo-org/scene2/top/page',
    method: 'post',
    data
  })
}
// 分页获取数据表列表
export function fetchDemoOrgPage(data) {
  return request({
    url: '/api/system/auth/demo-org/scene2/page',
    method: 'post',
    data
  })
}
// 获取指定GUID数据的下一层全部数据列表
export function fetchDemoOrgAllChildren(guid) {
  return request({
    url: '/api/system/auth/demo-org/scene2/children/all',
    method: 'get',
    params: { guid }
  })
}
// 保存数据
export function saveDemoOrg(data) {
  return request({
    url: '/api/system/auth/demo-org/scene2/save',
    method: 'post',
    data
  })
}
// 删除数据
export function delDemoOrg(data) {
  return request({
    url: '/api/system/auth/demo-org/scene2/del',
    method: 'post',
    data
  })
}
