/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*  tg-project表持久化接口
*/
import request from '@/utils/request'

// 分页获取顶层数据表列表
export function fetchTopTbOrganizationPage(data) {
  return request({
    url: '/api/system/auth/tb-organization/scene2/top/page',
    method: 'post',
    data
  })
}
// 分页获取数据表列表
export function fetchTbOrganizationPage(data) {
  return request({
    url: '/api/system/auth/tb-organization/scene2/page',
    method: 'post',
    data
  })
}
// 获取指定GUID数据的下一层全部数据列表
export function fetchTbOrganizationAllChildren(guid) {
  return request({
    url: '/api/system/auth/tb-organization/scene2/children/all',
    method: 'get',
    params: { guid }
  })
}
// 保存数据
export function saveTbOrganization(data) {
  return request({
    url: '/api/system/auth/tb-organization/scene2/save',
    method: 'post',
    data
  })
}
// 删除数据
export function delTbOrganization(data) {
  return request({
    url: '/api/system/auth/tb-organization/scene2/del',
    method: 'post',
    data
  })
}

export function fetchOrgChildrenNode(code, admDivCode) {
  return request({
    url: '/api/system/auth/tb-organization/scene2/children/node',
    method: 'get',
    params: { code, admDivCode }
  })
}
