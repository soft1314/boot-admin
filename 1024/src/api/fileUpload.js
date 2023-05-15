import request from '@/utils/request'

export function fetchAttachs(data) {
  return request({
    url: '/api/system/auth/file/fetch-file-querydto',
    method: 'post',
    data
  })
}
