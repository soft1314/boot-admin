import request from '@/utils/request'

// 分页获取已赋权的机构列表
export function fetchGrantedOrgPage(data) {
  return request({
    url: '/api/system/auth/tr-user-org/granted/page',
    method: 'post',
    data
  })
}

// 分页获取待赋权的机构列表
export function fetchGrantableOrgPage(data) {
  return request({
    url: '/api/system/auth/tr-user-org/grantable/page',
    method: 'post',
    data
  })
}

// 分页获取待赋权的机构列表
export function grantOneOrgToUser(data) {
  return request({
    url: '/api/system/auth/tr-user-org/grant/one',
    method: 'post',
    data
  })
}

// 从一个用户收回一个机构的数据权限
export function revokeOneOrgFromUser(data) {
  return request({
    url: '/api/system/auth/tr-user-org/revoke/one',
    method: 'post',
    data
  })
}
