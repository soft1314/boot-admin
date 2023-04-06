import request from '@/utils/request'

//分页获取已赋权的资源列表
export function fetchGrantedResourcePage(data) {
  return request({
    url: '/api/system/auth/tr-role-resource/granted/page',
    method: 'post',
    data
  })
}

//分页获取待赋权的资源列表
export function fetchGrantableResourcePage(data) {
  return request({
    url: '/api/system/auth/tr-role-resource/grantable/page',
    method: 'post',
    data
  })
}

//分页获取待赋权的资源列表
export function grantOneResourceToRole(data) {
  return request({
    url: '/api/system/auth/tr-role-resource/grant/one',
    method: 'post',
    data
  })
}

//从一个用户收回一个资源权限
export function revokeOneResourceFromRole(data) {
  return request({
    url: '/api/system/auth/tr-role-resource/revoke/one',
    method: 'post',
    data
  })
}
