/**
* @author 张三丰
* @since 2022-11-8 14:42:10
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

//生成对剧情4工作流查看表单VUE源码
export function generateScene4ViewVueSource(data) {
  return request({
    url: '/api/system/auth/generator/scene4/vue/view/source',
    method: 'post',
    data
  })
}

//生成对剧情4工作流查看表单VUE文件
export function generateScene4ViewVueFile(data) {
  return request({
    url: '/api/system/auth/generator/scene4/vue/view/file',
    method: 'post',
    data
  })
}
