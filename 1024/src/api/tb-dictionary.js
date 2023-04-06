import request from '@/utils/request'

//分页获取表数据
export function fetchDicTionaryPage(data) {
  return request({
    url: '/api/system/auth/manage/dictionary/page',
    method: 'post',
    data
  })
}
export function saveTbDictionary(data) {
  return request({
    url: '/api/system/auth/manage/dictionary/save',
    method: 'post',
    data
  })
}
export function delTbDictionary(data) {
  return request({
    url: '/api/system/auth/manage/dictionary/del',
    method: 'post',
    data
  })
}
//分页获取表首层数据
export function fetchTopLevelDicTionaryPage(data) {
  return request({
    url: '/api/system/auth/manage/dictionary/top/page',
    method: 'post',
    data
  })
}
//获取指定GUID数据的下一层全部数据列表
export function fetchTbDictionaryAllChildren(guid) {
  return request({
    url: '/api/system/auth/manage/dictionary/children/all',
    method: 'get',
    params: { guid }
  })
}