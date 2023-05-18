/**
* 本文件是下载附件
* @author 虚领顶劲气沉丹田
* @since 2023-2-27 22:01:58
*
*/
import request from '@/utils/requestDownload'

// 读取上传的附件文件
export function fetchAttachment(guid) {
  return request({
    url: '/api/system/auth/file/attachment',
    method: 'get',
    params: { guid }
  })
}
