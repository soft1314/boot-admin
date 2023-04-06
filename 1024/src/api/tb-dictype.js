import request from '@/utils/request'

//分页获取表数据
export function fetchDicTypePage(data) {
  return request({
    url: '/api/system/auth/manage/dictionary/type/page',
    method: 'post',
    data
  })
}
export function saveTbDictype(data) {
  return request({
    url: '/api/system/auth/manage/dictionary/type/save',
    method: 'post',
    data
  })
}
export function delTbDictype(data) {
  return request({
    url: '/api/system/auth/manage/dictionary/type/del',
    method: 'post',
    data
  })
}

export function fetchDictypeJsSource(data) {
  return request({
    url: '/api/system/auth/generator/dictype/js/code',
    method: 'post',
    data
  })
}
export function generateDictypeJsFile(data) {
  return request({
    url: '/api/system/auth/generator/dictype/js/file',
    method: 'post',
    data
  })
}