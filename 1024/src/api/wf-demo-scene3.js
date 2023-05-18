/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2023-3-3 22:00:25
*  tg-project表持久化接口
*/
import request from '@/utils/request'

// 保存数据
export function saveWfDemoAndStartProcess(data) {
  return request({
    url: '/api/system/auth/wf-demo/scene3/start',
    method: 'post',
    data
  })
}
export function saveWfDemoAndCompleteTask(data) {
  return request({
    url: '/api/system/auth/wf-demo/scene3/task/complete',
    method: 'post',
    data
  })
}
// 按GUID查询一条
export function fetchWfDemoInfo(data) {
  return request({
    url: '/api/system/auth/wf-demo/scene3/info',
    method: 'post',
    data
  })
}
