import {
  Message
} from 'element-ui'
import {
  getMenus
} from '@/api/menu'
import {
  getToken
} from '@/utils/auth'
import {
  asyncRoutes
} from '@/router/index'

const getDefaultState = () => {
  return {
    token: getToken(),
    menuList: []
  }
}

const state = getDefaultState()

const mutations = {
  SET_MENUS: (state, menus) => {
    state.menuList = menus
  }
}

// 动态菜单还是定义在前端，后台只会返回有权限的菜单列表，通过遍历服务端的菜单数据，没有的将对于菜单进行隐藏
// 这样的好处是服务端无需返回前端菜单相关结构，并且菜单显示又可以通过服务端来控制，进行菜单的动态控制
// 前端新增页面也无需先通过服务端进行菜单添加，遵循了前后端分离原则
export function generaMenu(routes, srvMenus) {
  for (let i = 0; i < routes.length; i++) {
    const routeItem = routes[i]
    var showItem = false
    for (let j = 0; j < srvMenus.length; j++) {
      const srvItem = srvMenus[j]

      // 前后端数据通过 srvName 属性来匹配
      if (routeItem.srvName !== undefined && routeItem.srvName === srvItem.srvName && srvItem.show === true) {
        showItem = true
        routes[i]['hidden'] = false
        break
      }
    }
    if (showItem === false) {
      routes[i]['hidden'] = true
    }

    if (routeItem['children'] !== undefined && routeItem['children'].length > 0) {
      generaMenu(routes[i]['children'], srvMenus)
    }
  }
}

const actions = {
  getMenus({
    commit
  }) {
    return new Promise((resolve, reject) => {
      getMenus(state.token).then(response => {
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
        if (!data) {
          reject('Verification failed, please Login again.')
        }

        const srvMenus = data
        var pushRouter = asyncRoutes
        generaMenu(pushRouter, srvMenus)
        commit('SET_MENUS', pushRouter)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
