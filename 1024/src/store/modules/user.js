import {
  Message
} from 'element-ui'
import {
  login,
  logout,
  getInfo,
  refreshToken
} from '@/api/user'
import {
  getToken,
  setToken,
  removeToken,
  getSeed,
  setSeed,
  removeSeed
} from '@/utils/auth'
import router, {
  resetRouter
} from '@/router'

const Base64 = require('js-base64').Base64

const state = {
  token: getToken(),
  seed: getSeed(),
  name: '',
  avatar: '',
  introduction: '',
  roles: [],
  lastRefresh: null
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_SEED: (state, seed) => {
    state.seed = seed
  },
  SET_INTRODUCTION: (state, introduction) => {
    state.introduction = introduction
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLES: (state, roles) => {
    state.roles = roles
  },
  SET_LASTREFRESH_TIME: (state, refreshAt) => {
    state.lastRefresh = refreshAt
  }
}

const actions = {
  // user login
  login({
    commit
  }, userInfo) {
    const {
      username,
      password,
      captchaVerification
    } = userInfo
    return new Promise((resolve, reject) => {
      login({
        username: username.trim(),
        password: Base64.encode(password),
        captchaVerification: captchaVerification.trim()
      }).then(response => {
        if (response.code !== 100) {
          Message({
            message: response.message,
            type: 'error',
            duration: 5 * 1000
          })
          reject(response.message)
        }

        const {
          data
        } = response

        commit('SET_TOKEN', data)
        setToken(data)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info 获取用户信息todo
  getInfo({
    commit,
    state
  }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        if (response.code !== 100) {
          Message({
            message: response.message,
            type: 'error',
            duration: 5 * 1000
          })
          reject(response.message)
        }

        const {
          data
        } = response

        const {
          roles,
          name,
          avatar,
          introduction,
          seed
        } = data

        // roles must be a non-empty array
        if (!roles || roles.length <= 0) {
          reject('getInfo: roles must be a non-null array!')
        }

        commit('SET_ROLES', roles)
        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        commit('SET_INTRODUCTION', introduction)
        commit('SET_SEED', seed)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({
    commit,
    state,
    dispatch
  }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        commit('SET_ROLES', [])
        removeToken()
        removeSeed()
        resetRouter()

        // reset visited views and cached views
        // to fixed https://github.com/PanJiaChen/vue-element-admin/issues/2485
        dispatch('tagsView/delAllViews', null, {
          root: true
        })

        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({
    commit
  }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      commit('SET_ROLES', [])
      removeToken()
      resolve()
    })
  },

  // dynamically modify permissions
  async changeRoles({
    commit,
    dispatch
  }, role) {
    const token = role + '-token'

    commit('SET_TOKEN', token)
    setToken(token)

    const {
      roles
    } = await dispatch('getInfo')

    resetRouter()

    // generate accessible routes map based on roles
    const accessRoutes = await dispatch('permission/generateRoutes', roles, {
      root: true
    })
    // dynamically add accessible routes
    router.addRoutes(accessRoutes)

    // reset visited views and cached views
    dispatch('tagsView/delAllViews', null, {
      root: true
    })
  },
  refreshToken({
    commit
  }) {
    var lastTime = state.lastRefresh
    var thisTime = Date.now()
    var timeSpan = 30001
    if (lastTime !== null) {
      timeSpan = thisTime - lastTime
    }
    //距上次刷新超过10秒钟，才允许刷新
    console.log("------------timeSpan=================="+timeSpan)
    if (timeSpan > 10000) {
      commit('SET_LASTREFRESH_TIME',thisTime)
      return new Promise((resolve, reject) => {
        refreshToken({
          token: state.seed
        }).then(response => {
          if (response.code !== 100) {
            Message({
              message: response.message,
              type: 'error',
              duration: 5 * 1000
            })
            reject(response.message)
          }

          const {
            data
          } = response

          commit('SET_TOKEN', data.firstToken)
          commit('SET_SEED', data.secondToken)
          setToken(data.firstToken)
          setSeed(data.secendToken)
          Message({
            message: '系统成功刷新一次',
            type: 'error',
            duration: 5 * 1000
          })
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    }else{
      // Message({
      //   message: '刷新太频繁，这次不刷新',
      //   type: 'error',
      //   duration: 5 * 1000
      // })
    }
  },
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
