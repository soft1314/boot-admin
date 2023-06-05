/**
* 本文件自动生成，严禁人工修改
* @author 虚领顶劲气沉丹田
* @since 2000-08-20
*  tg-project表持久化接口
*/
import request from '@/utils/request'

// 按公告和其他条件分页获取数据表列表
export function fetchUserNotifyRecordPageByNotifyGuid(data) {
  return request({
    url: '/api/system/auth/notify/record/bynotify',
    method: 'post',
    data
  })
}
// 按用户GUID查询通知通告
export function fetchUserNotifyRecordPageByUserGuid(data) {
  return request({
    url: '/api/system/auth/notify/record/byuser',
    method: 'post',
    data
  })
}

// 设置已读
export function setNotifyReaded(notifyGuid) {
  return request({
    url: '/api/system/auth/notify/content/read',
    method: 'get',
    params: { notifyGuid }
  })
}
