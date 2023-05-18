/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const myWorkRouter = {
  path: '/work',
  name: 'work',
  component: Layout,
  srvName: '/api/system/auth/work',
  meta: {
    title: '办公管理',
    icon: 'example'
  },
  children: [{
    path: 'notice',
    name: 'notice',
    srvName: '/api/system/auth/work/notice',
    component: () => import('@/views/work/notice/index'),
    meta: {
      title: '我的通知',
      icon: 'edit'
    }
  },
  {
    path: 'notify',
    name: 'notify',
    srvName: '/api/system/auth/work/notify',
    component: () => import('@/views/work/notice/notify'),
    meta: {
      title: '通知通告',
      icon: 'edit'
    }
  }
  ]
}
export default myWorkRouter
