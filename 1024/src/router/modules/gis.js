/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const gisRouter = {
  path: '/gis',
  name: 'Gis',
  component: Layout,
  redirect: '/gis/tianditu/test1',
  srvName: '/api/system/auth/gis',
  meta: {
    title: '地理信息',
    icon: 'example'
  },
  children: [{
    path: 'tianditu',
    name: 'TianDiTu',
    srvName: '/api/system/auth/gis/tianditu',
    component: () => import('@/views/gis/tianditu/index'),
    redirect: '/gis/tianditu/test1',
    meta: {
      title: '天地图',
      icon: 'tree'
    },
    children: [{
      path: 'test1',
      name: 'Test1',
      srvName: '/api/system/auth/gis/tianditu/test1',
      component: () => import('@/views/gis/tianditu/test1/index'),
      meta: {
        title: '测试一',
        icon: 'tree'
      }
    },
    {
      path: 'test2',
      name: 'Test2',
      srvName: '/api/system/auth/gis/tianditu/test2',
      component: () => import('@/views/gis/tianditu/test2/index'),
      meta: {
        title: '测试二',
        icon: 'tree'
      }
    },
    {
      path: 'test3',
      name: 'Test3',
      srvName: '/api/system/auth/gis/tianditu/test3',
      component: () => import('@/views/gis/tianditu/test3/index'),
      meta: {
        title: '测试三',
        icon: 'tree'
      }
    },
    {
      path: 'test4',
      name: 'Test4',
      srvName: '/api/system/auth/gis/tianditu/test4',
      component: () => import('@/views/gis/tianditu/test4/index'),
      meta: {
        title: '测试四',
        icon: 'tree'
      }
    },
    {
      path: 'test5',
      name: 'Test5',
      srvName: '/api/system/auth/gis/tianditu/test5',
      component: () => import('@/views/gis/tianditu/test5/index'),
      meta: {
        title: '测试五',
        icon: 'tree'
      }
    }
    ]
  }]
}
export default gisRouter
