import request from '@/utils/request'

// 按主数据获取所有附件
export function fetchAttachmentList(data) {
  return request({
    url: '/api/system/auth/attachment/list',
    method: 'post',
    data
  })
}
