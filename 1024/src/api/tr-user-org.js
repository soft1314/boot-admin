import request from '@/utils/request'

//分页获取已赋权的区划列表
export function fetchGrantedOrgPage(data) {
  return request({
    url: '/api/system/auth/tr-user-org/granted/page',
    method: 'post',
    data
  })
}

//分页获取待赋权的区划列表
export function fetchGrantableOrgPage(data) {
  return request({
    url: '/api/system/auth/tr-user-org/grantable/page',
    method: 'post',
    data
  })
}

//分页获取待赋权的区划列表
export function grantOneOrgToUser(data) {
  return request({
    url: '/api/system/auth/tr-user-org/grant/one',
    method: 'post',
    data
  })
}

//从一个用户收回一个区划的数据权限
export function revokeOneOrgFromUser(data) {
  return request({
    url: '/api/system/auth/tr-user-org/revoke/one',
    method: 'post',
    data
  })
}
