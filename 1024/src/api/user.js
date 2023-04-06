import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/api/system/free/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/api/system/auth/user/info',
    method: 'get',
    params: { }
  })
}

export function logout() {
  return request({
    url: '/api/system/auth/user/logout',
    method: 'post'
  })
}

export function refreshToken(data) {
  return request({
    url: '/api/system/auth/token/refresh',
    method: 'post',
    data
  })
}