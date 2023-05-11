import request from '@/utils/request'

// 分页获取已添加通知的人员列表
export function fetchGrantedEmpPage(data) {
  return request({
    url: '/api/system/auth/tr-notify-emp/granted/page',
    method: 'post',
    data
  })
}

// 分页获取待添加的人员列表
export function fetchGrantableEmpPage(data) {
  return request({
    url: '/api/system/auth/tr-notify-emp/grantable/page',
    method: 'post',
    data
  })
}

// 添加一个拟通知人员
export function grantOneEmpToNotify(data) {
  return request({
    url: '/api/system/auth/tr-notify-emp/grant/one',
    method: 'post',
    data
  })
}

// 从拟通知人员列表中删除一个人员
export function revokeOneEmpFromNotify(data) {
  return request({
    url: '/api/system/auth/tr-notify-emp/revoke/one',
    method: 'post',
    data
  })
}
