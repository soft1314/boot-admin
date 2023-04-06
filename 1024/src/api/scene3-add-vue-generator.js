/**
* @author 张三丰
* @since 2022-11-8 14:42:10
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

//生成对剧情3工作流添加表单VUE源码
export function generateScene3AddVueSource(data) {
  return request({
    url: '/api/system/auth/generator/scene3/vue/add/source',
    method: 'post',
    data
  })
}

//生成对剧情3工作流添加表单VUE文件
export function generateScene3AddVueFile(data) {
  return request({
    url: '/api/system/auth/generator/scene3/vue/add/file',
    method: 'post',
    data
  })
}
