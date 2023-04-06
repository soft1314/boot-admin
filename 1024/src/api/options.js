import request from '@/utils/request'

export function fetchOracleObjectTypeOptions() {
  return request({
    url: '/api/system/auth/generator/dictionary/options/oracle/object/type',
    method: 'get'
  })
}
//获取Options数据
export function fetchOptionsByType(data) {
  return request({
    url: '/api/system/auth/generator/dictionary/options/type',
    method: 'post',
    data
  })
}