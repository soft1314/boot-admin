import request from '@/utils/request'

export function fetchIdStr() {
  return request({
    url: '/api/system/auth/idworker/idstr',
    method: 'post'
  })
}