/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2023-4-12 15:23:38
*  tg-project表持久化接口
*/
import request from '@/utils/request'

// 分页获取数据表列表
export function fetchDemoTestPage(data) {
  return request({
    url: '/api/system/auth/demo-test/scene1/table/page',
    method: 'post',
    data
  })
}

// 保存数据
export function saveDemoTest(data) {
  return request({
    url: '/api/system/auth/demo-test/scene1/save',
    method: 'post',
    data
  })
}

// 删除数据
export function delDemoTest(data) {
  return request({
    url: '/api/system/auth/demo-test/scene1/del',
    method: 'post',
    data
  })
}
