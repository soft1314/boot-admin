import request from '@/utils/request'
//分页获取数据表和视图
export function fetchTableAndViewsPage(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/table/page',
    method: 'post',
    data
  })
}
//生成指定数据表的VO类
export function generateVo(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/vo',
    method: 'post',
    data
  })
}
//生成指定数据表的VO类源代码
export function generateVoSource(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/vo/source',
    method: 'post',
    data
  })
}
//生成指定数据表的实体类
export function generateEntity(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/entity',
    method: 'post',
    data
  })
}
//生成指定数据表的实体类源代码
export function generateEntitySource(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/entity/source',
    method: 'post',
    data
  })
}
//检查指定数据表的实体类是否已经存在
export function checkEntityExist(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/entity/already',
    method: 'post',
    data
  })
}
//生成指定数据表的Service
export function generateService(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/service',
    method: 'post',
    data
  })
}
//生成指定数据表的Service源代码
export function generateServiceSource(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/service/source',
    method: 'post',
    data
  })
}
export function generateMapper(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/mapper',
    method: 'post',
    data
  })
}
export function generateMapperSource(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/mapper/source',
    method: 'post',
    data
  })
}
export function generateController(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/controller',
    method: 'post',
    data
  })
}
export function generateControllerSource(data) {
  return request({
    url: '/api/system/auth/generator/mybatisplus/controller/source',
    method: 'post',
    data
  })
}