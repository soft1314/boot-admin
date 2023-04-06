import request from '@/utils/request'

export function fetchOnlineUser(data) {
  return request({
    url: '/api/system/auth/online/user/page',
    method: 'post',
    data
  })
}
