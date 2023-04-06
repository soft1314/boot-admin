import request from '@/utils/request'

//分页获取已赋权的区划列表
export function fetchGrantedAdmDivPage(data) {
  return request({
    url: '/api/system/auth/tr-user-admdiv/granted/page',
    method: 'post',
    data
  })
}

//分页获取待赋权的区划列表
export function fetchGrantableAdmDivPage(data) {
  return request({
    url: '/api/system/auth/tr-user-admdiv/grantable/page',
    method: 'post',
    data
  })
}

//分页获取待赋权的区划列表
export function grantOneDivToUser(data) {
  return request({
    url: '/api/system/auth/tr-user-admdiv/grant/one',
    method: 'post',
    data
  })
}

//从一个用户收回一个区划的数据权限
export function revokeOneDivFromUser(data) {
  return request({
    url: '/api/system/auth/tr-user-admdiv/revoke/one',
    method: 'post',
    data
  })
}
