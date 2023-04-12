/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2023-4-12 22:27:12
* 表持久化接口
*/
import request from '@/utils/request'

// 插入数据并启动流程
export function saveDemoLeaveAndStartProcess(data) {
  return request({
    url: '/api/system/auth/demo-leave/scene3/start',
    method: 'post',
    data
  })
}
// 修改数据并完成任务
export function saveDemoLeaveAndCompleteTask(data) {
  return request({
    url: '/api/system/auth/demo-leave/scene3/task/complete',
    method: 'post',
    data
  })
}
// 按GUID查询一条
export function fetchDemoLeaveInfo(data) {
  return request({
    url: '/api/system/auth/demo-leave/scene3/info',
    method: 'post',
    data
  })
}
