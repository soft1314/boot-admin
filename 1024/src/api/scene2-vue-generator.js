/**
* @author 张三丰
* @since 2022-11-8 14:42:10
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

//生成对剧情2树状单表的基本操作VUE源码
export function generateScene2VueSource(data) {
  return request({
    url: '/api/system/auth/generator/scene2/vue/source',
    method: 'post',
    data
  })
}

//生成对剧情2树状单表的基本操作VUE文件
export function generateScene2VueFile(data) {
  return request({
    url: '/api/system/auth/generator/scene2/vue/file',
    method: 'post',
    data
  })
}
