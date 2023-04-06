/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const generatedRouter = {
  path: '/generated',
  name: 'generated',
  component: Layout,
  srvName: '/api/system/auth/generated',
  meta: {
    title: '代码生成效果演示',
    icon: 'example'
  },
   children: [
    {
      path: 'scene1',
      name: 'scene1',
      srvName: '/api/system/auth/generated/all',
      component: () => import('@/views/generated/demo-emp-scene1/index'),
      meta: {
        title: '人员管理（CRUD）',
        icon: 'edit'
      }
    },
    {
      path: 'scene2',
      name: 'scene2',
      srvName: '/api/system/auth/generated/all',
      component: () => import('@/views/generated/demo-org-scene2/index'),
      meta: {
        title: '单位管理（层级CRUD）',
        icon: 'edit'
      }
    },
    {
      path: 'scene4',
      name: 'scene4',
      srvName: '/api/system/auth/generated/all',
      component: () => import('@/views/generated/demo-expense-scene4/index'),
      meta: {
        title: '费用管理（流程）',
        icon: 'edit'
      }
    },

  ]
}
export default generatedRouter
