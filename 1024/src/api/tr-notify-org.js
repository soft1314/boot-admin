import request from '@/utils/request'

// 分页获取已添加通知的机构列表
export function fetchGrantedOrgPage(data) {
  return request({
    url: '/api/system/auth/tr-notify-org/granted/page',
    method: 'post',
    data
  })
}

// 分页获取待添加的机构列表
export function fetchGrantableOrgPage(data) {
  return request({
    url: '/api/system/auth/tr-notify-org/grantable/page',
    method: 'post',
    data
  })
}

// 添加一个拟通知机构
export function grantOneOrgToNotify(data) {
  return request({
    url: '/api/system/auth/tr-notify-org/grant/one',
    method: 'post',
    data
  })
}

// 从拟通知机构列表中删除一个机构
export function revokeOneOrgFromNotify(data) {
  return request({
    url: '/api/system/auth/tr-notify-org/revoke/one',
    method: 'post',
    data
  })
}
