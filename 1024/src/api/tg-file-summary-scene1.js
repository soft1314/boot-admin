/**
* 本文件自动生成，严禁人工修改
* @author 张三丰
* @since 2000-08-20
*  tg-project表持久化接口
*/
import request from '@/utils/request'

// 分页获取数据表列表
export function fetchTgFileSummaryPage(data) {
  return request({
    url: '/api/system/auth/tg-file-summary/scene1/table/page',
    method: 'post',
    data
  })
}

// 保存数据
export function saveTgFileSummary(data) {
  return request({
    url: '/api/system/auth/tg-file-summary/scene1/save',
    method: 'post',
    data
  })
}

// 删除数据
export function delTgFileSummary(data) {
  return request({
    url: '/api/system/auth/tg-file-summary/scene1/del',
    method: 'post',
    data
  })
}
