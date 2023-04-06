import axios from 'axios'
import {
  Message,
  MessageBox
} from 'element-ui'
import store from '@/store'
import {
  getToken
} from '@/utils/auth'

// create an axios instance
const service = axios.create({
  baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 15000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent
    if (store.getters.token) {
      // let each request carry token
      // ['X-Token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['X-Token'] = 'Bearer' + getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * 200是正确返回，其他为错误
   */
  response => {
    if (response.status !== 200) {
      Message({
        message: response.statusText,
        type: 'error',
        duration: 5 * 1000
      })
    }
    const res = response.data
    if (res.status !== 200) {
      const result = {}
      result.code = '102'
      result.message = '系统错误信息:Status[' + res.status + '],Message[' + res.message + ']'
      return result
    }
    if('true' === res.tokenTimeout){
      Message({
        message: '系统检测令牌需刷新一下子。',
        type: 'error',
        duration: 5 * 1000
      })
      //因并发，控制30秒最多刷新一次吧
      
      store.dispatch('user/refreshToken')

    }
    // 成功返回数据
    return res.data
  },
  error => {
    if (typeof (error.code) !== 'undefined') {
      // 超时 等情形
      const result = {}
      result.code = '102'
      result.message = 'Error code[' + error.code + '],Error message:' + error.message
      return result
    }

    if (error.response.status === 401 && error.response.data.status === 401) {
      MessageBox.confirm('系统令牌已过期,您可以点击“取消”停留在本页面,或者点击“重新登录”继续使用。',
        '安全提示', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
        store.dispatch('user/resetToken').then(() => {
          location.reload()
        })
      })
    } else {
      const result = {}
      result.code = '102'
      result.message = 'HTTP状态码[' + error.response.status + '],HTTP状态[' + error.response.statusText + '],系统错误信息:  Error[' + error.response.data.error + '],Status[' + error.response.data.status + '],Message[' + error.response.data.message + '],URI[' + error.response.data.path + '],Exception:' + error.response.data.exception
      return result
    }
  }
)

export default service
