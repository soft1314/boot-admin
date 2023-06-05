/**
* @author 张三丰
* @since 2000-08-20
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

// 生成对剧情3工作流查看表单VUE源码
export function generateScene3ViewVueSource(data) {
  return request({
    url: '/api/system/auth/generator/scene3/vue/view/source',
    method: 'post',
    data
  })
}

// 生成对剧情3工作流查看表单VUE文件
export function generateScene3ViewVueFile(data) {
  return request({
    url: '/api/system/auth/generator/scene3/vue/view/file',
    method: 'post',
    data
  })
}
