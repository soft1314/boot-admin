import request from '@/utils/request'

//分页获取自动生成项目数据
export function fetchProjectPage(data) {
  return request({
    url: '/api/system/auth/generator/project/table/page',
    method: 'post',
    data
  })
}
export function saveTgProject(data) {
  return request({
    url: '/api/system/auth/generator/project/save',
    method: 'post',
    data
  })
}
export function delTgProject(data) {
  return request({
    url: '/api/system/auth/generator/project/del',
    method: 'post',
    data
  })
}
export function activeTgProject(data) {
  return request({
    url: '/api/system/auth/generator/project/active',
    method: 'post',
    data
  })
}
