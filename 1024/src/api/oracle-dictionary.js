import request from '@/utils/request'

export function fetchTableColumnList(data) {
  return request({
    url: '/api/system/auth/dictionary/oracle/column/list',
    method: 'post',
    data
  })
}
