/**
* 本文件是下载
* @author 虚领顶劲气沉丹田
* @since 2023-2-27 22:01:58
*
*/
import request from '@/utils/requestDownload'
// 导出流程跟踪图
export function fetchProcessDiagram(processInstanceId) {
  return request({
    url: '/api/workflow/auth/activiti/task/process/diagram',
    method: 'get',
    params: { processInstanceId }
  })
}
// 导出流程XML或图片资源
export function fetchResource(id, resType) {
  return request({
    url: '/api/workflow/auth/activiti/res/exp',
    method: 'get',
    params: { id, resType }
  })
}

// 读取带跟踪的图片
export function fetchTracePhoto(processInstanceId) {
  return request({
    url: '/api/workflow/auth/activiti/task/trace/photo',
    method: 'get',
    params: { processInstanceId }
  })
}

