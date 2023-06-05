/**
* @author 张三丰
* @since 2000-08-20
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

// 生成对单层次单表的基本操作VUE源码
export function generateScene1VueSource(data) {
  return request({
    url: '/api/system/auth/generator/scene1/vue/source',
    method: 'post',
    data
  })
}

// 生成对单层次单表的基本操作VUE文件
export function generateScene1VueFile(data) {
  return request({
    url: '/api/system/auth/generator/scene1/vue/file',
    method: 'post',
    data
  })
}
