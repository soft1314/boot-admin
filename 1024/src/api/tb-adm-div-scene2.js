/**
* 本文件自动生成，严禁人工修改
* @author 张三丰
* @since 2000-08-20
*  tg-project表持久化接口
*/
import request from '@/utils/request'

// 分页获取顶层数据表列表
export function fetchTopTbAdmDivPage(data) {
  return request({
    url: '/api/system/auth/tb-adm-div/scene2/top/page',
    method: 'post',
    data
  })
}
// 分页获取顶层数据表列表
export function fetchTbAdmDivPage(data) {
  return request({
    url: '/api/system/auth/tb-adm-div/scene2/page',
    method: 'post',
    data
  })
}
// 获取指定GUID数据的下一层全部数据列表
export function fetchTbAdmDivAllChildren(guid) {
  return request({
    url: '/api/system/auth/tb-adm-div/scene2/children/all',
    method: 'get',
    params: { guid }
  })
}
// 保存数据
export function saveTbAdmDiv(data) {
  return request({
    url: '/api/system/auth/tb-adm-div/scene2/save',
    method: 'post',
    data
  })
}
// 删除数据
export function delTbAdmDiv(data) {
  return request({
    url: '/api/system/auth/tb-adm-div/scene2/del',
    method: 'post',
    data
  })
}
// 获取指定GUID数据的下一层全部数据列表
export function fetchTbAdmDivChildrenNode(code) {
  return request({
    url: '/api/system/auth/tb-adm-div/scene2/children/node',
    method: 'get',
    params: { code }
  })
}
// 获取应用的缺省区划级联值
export function fetchTbAdmDivDefaultCascaderValue() {
  return request({
    url: '/api/system/auth/tb-adm-div/scene2/default/value',
    method: 'get'
  })
}
