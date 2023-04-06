/**
* 本文件自动生成，严禁人工修改
* @author ${dataContainer.author}
* @since ${.now}
*  tg-project表持久化接口
*/
import request from '@/utils/request'

//分页获取顶层数据表列表
export function fetchTop${dataContainer.uppercaseHump}Page(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene2/top/page',
    method: 'post',
    data
  })
}
//分页获取数据表列表
export function fetch${dataContainer.uppercaseHump}Page(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene2/page',
    method: 'post',
    data
  })
}
//获取指定GUID数据的下一层全部数据列表
export function fetch${dataContainer.uppercaseHump}AllChildren(guid) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene2/children/all',
    method: 'get',
    params: { guid }
  })
}
//保存数据
export function save${dataContainer.uppercaseHump}(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene2/save',
    method: 'post',
    data
  })
}
//删除数据
export function del${dataContainer.uppercaseHump}(data) {
  return request({
    url: '/api/system/auth/${dataContainer.lowercaseMinusSign}/scene2/del',
    method: 'post',
    data
  })
}