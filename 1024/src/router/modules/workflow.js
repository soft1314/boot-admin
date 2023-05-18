/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const workflowRouter = {
  path: '/flow',
  name: 'flow',
  component: Layout,
  srvName: '/api/system/auth/work',
  meta: {
    title: '工作流程',
    icon: 'example'
  },
  children: [{
    path: 'todolist',
    name: 'todolist',
    srvName: '/api/system/auth/work/workflow/todolist',
    component: () => import('@/views/work/workflow/todolist'),
    meta: {
      title: '待办',
      icon: 'edit'
    }
  },
  {
    path: 'recalllist',
    name: 'recalllist',
    srvName: '/api/system/auth/work/workflow/recalllist',
    component: () => import('@/views/work/workflow/recalllist'),
    meta: {
      title: '撤回',
      icon: 'edit'
    }
  },
  {
    path: 'hislist',
    name: 'hislist',
    srvName: '/api/system/auth/work/workflow/hislist',
    component: () => import('@/views/work/workflow/hislist'),
    meta: {
      title: '已办',
      icon: 'edit'
    }
  },
  {
    path: 'startprocess',
    name: 'startprocess',
    srvName: '/api/system/auth/work/workflow/startprocess',
    component: () => import('@/views/work/workflow/startprocess'),
    meta: {
      title: '申请',
      icon: 'edit'
    }
  }
  ]
}
export default workflowRouter
