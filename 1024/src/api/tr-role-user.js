import request from '@/utils/request'

//分页获取已赋权的区划列表
export function fetchGrantedUserPage(data) {
  return request({
    url: '/api/system/auth/tr-role-user/granted/page',
    method: 'post',
    data
  })
}

//分页获取待赋权的区划列表
export function fetchGrantableUserPage(data) {
  return request({
    url: '/api/system/auth/tr-role-user/grantable/page',
    method: 'post',
    data
  })
}

//分页获取待赋权的区划列表
export function grantOneUserToRole(data) {
  return request({
    url: '/api/system/auth/tr-role-user/grant/one',
    method: 'post',
    data
  })
}

//从一个用户收回一个区划的数据权限
export function revokeOneUserFromRole(data) {
  return request({
    url: '/api/system/auth/tr-role-user/revoke/one',
    method: 'post',
    data
  })
}
