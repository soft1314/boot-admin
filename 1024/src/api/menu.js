import request from '@/utils/request'

export function getMenus(token) {
  return request({
    url: '/api/system/auth/user/menu',
    method: 'get',
    params: { token }
  })
}
