/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2023-5-8 22:37:53
*  tg-project表持久化接口
*/
import request from '@/utils/request'

// 分页获取数据表列表
export function fetchToNotifyPage(data) {
  return request({
    url: '/api/system/auth/to-notify/scene1/table/page',
    method: 'post',
    data
  })
}

// 保存数据
export function saveToNotify(data) {
  return request({
    url: '/api/system/auth/to-notify/scene1/save',
    method: 'post',
    data
  })
}

// 删除数据
export function delToNotify(data) {
  return request({
    url: '/api/system/auth/to-notify/scene1/del',
    method: 'post',
    data
  })
}

// 保存数据
export function saveTrNotifyDiv(data) {
  return request({
    url: '/api/system/auth/tr-notify-div/save',
    method: 'post',
    data
  })
}
// 发出通知通告
export function sendNotify(data) {
  return request({
    url: '/api/system/auth/notify/send',
    method: 'post',
    data
  })
}
// 发出通知通告前检查
export function preSendNotify(data) {
  return request({
    url: '/api/system/auth/notify/presend',
    method: 'post',
    data
  })
}
// 装入正文
export function fetchNotifyContent(notifyGuid) {
  return request({
    url: '/api/system/auth/notify/content/load',
    method: 'get',
    params: { notifyGuid }
  })
}

// 保存正文
export function saveNotifyContent(data) {
  return request({
    url: '/api/system/auth/notify/content/save',
    method: 'post',
    data
  })
}
