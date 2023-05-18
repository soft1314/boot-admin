/**
* @author 张三丰
* @since 2022-11-8 22:42:10
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

// 生成全局事务源码
export function generateScene3TransactionSource(data) {
  return request({
    url: '/api/system/auth/generator/scene3/transaction/source',
    method: 'post',
    data
  })
}

// 生成全局事务文件
export function generateScene3TransactionFile(data) {
  return request({
    url: '/api/system/auth/generator/scene3/transaction/file',
    method: 'post',
    data
  })
}
