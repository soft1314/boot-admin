import request from '@/utils/request'

//获取应用所有Controller信息级联选择器数据
export function fetchAllCascaderData() {
  return request({
    url: '/api/system/auth/generator/controller/info/cascaderdata',
    method: 'get'
  })
}

//获取应用所有Controller信息列表
export function fetchControllerInfoList() {
  return request({
    url: '/api/system/auth/generator/controller/info/list',
    method: 'get'
  })
}
