/**
* @author 张三丰
* @since 2000-08-20
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

// 生成对剧情4工作流修改表单VUE源码
export function generateScene4EditVueSource(data) {
  return request({
    url: '/api/system/auth/generator/scene4/vue/edit/source',
    method: 'post',
    data
  })
}

// 生成对剧情4工作流修改表单VUE文件
export function generateScene4EditVueFile(data) {
  return request({
    url: '/api/system/auth/generator/scene4/vue/edit/file',
    method: 'post',
    data
  })
}
