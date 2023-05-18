/**
* @author 张三丰
* @since 2022-11-8 22:42:10
*  Vue Element Admin相关代码生成接口
*/
import request from '@/utils/request'

// 生成源码
export function generateScene4VueRouterSource(data) {
  return request({
    url: '/api/system/auth/generator/scene4/vue/router/source',
    method: 'post',
    data
  })
}

