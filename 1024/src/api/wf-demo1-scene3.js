/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2023-3-7 17:42:39
*  表持久化接口
*/
import request from '@/utils/request'

//保存数据
export function saveWfDemo1AndStartProcess(data) {
  return request({
    url: '/api/system/auth/wf-demo1/scene3/start',
    method: 'post',
    data
  })
}
//修改数据并完成任务
export function saveWfDemo1AndCompleteTask(data) {
  return request({
    url: '/api/system/auth/wf-demo1/scene3/task/complete',
    method: 'post',
    data
  })
}
//按GUID查询一条
export function fetchWfDemo1Info(data) {
  return request({
    url: '/api/system/auth/wf-demo1/scene3/info',
    method: 'post',
    data
  })
}
