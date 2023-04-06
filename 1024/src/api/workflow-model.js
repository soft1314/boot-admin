/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2023-2-27 17:01:58
*  tg-project表持久化接口
*/
import request from '@/utils/request'

//分页获取数据表列表
export function fetchModelPage(data) {
  return request({
    url: '/api/workflow/auth/activiti/model/page',
    method: 'post',
    data
  })
}

//创建新模型
export function saveNewModel(data) {
  return request({
    url: '/api/workflow/auth/activiti/model/add',
    method: 'post',
    data
  })
}

//删除数据
export function delModel(data) {
  return request({
    url: '/api/workflow/auth/activiti/model/del',
    method: 'post',
    data
  })
}

//部署模型
export function deployModel(data) {
  return request({
    url: '/api/workflow/auth/activiti/model/deploy',
    method: 'post',
    data
  })
}

//获取模型XML
export function fetchXml(data) {
  return request({
    url: '/api/workflow/auth/activiti/model/xml',
    method: 'post',
    data
  })
}