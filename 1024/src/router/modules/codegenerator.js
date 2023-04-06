/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const codeGeneratorRouter = {
  path: '/generator',
  name: 'generator',
  component: Layout,
  srvName: '/api/system/auth/manage/generator',
  meta: {
    title: '代码生成',
    icon: 'example'
  },
  children: [{
      path: 'project',
      name: 'project',
      srvName: '/api/system/auth/manage/generator/project',
      component: () => import('@/views/generator/project/index'),
      meta: {
        title: '应用参数',
        icon: 'edit'
      }
    },
    {
      path: 'mybatisplus',
      name: 'mybatisplus',
      srvName: '/api/system/auth/manage/generator/mybatisplus',
      component: () => import('@/views/generator/mybatisplus/index'),
      meta: {
        title: 'MyBatis-Plus',
        icon: 'edit'
      }
    },
    {
      path: 'dictype',
      name: 'dictype',
      srvName: '/api/system/auth/manage/generator/dictype',
      component: () => import('@/views/generator/dictype/index'),
      meta: {
        title: '字典常量',
        icon: 'edit'
      }
    },
    {
      path: 'scenario1',
      name: 'scenario1',
      srvName: '/api/system/auth/manage/generator/scenario1',
      component: () => import('@/views/generator/scenario1/index'),
      meta: {
        title: '1车间（B-CRUD）',
        icon: 'edit'
      }
    },
    {
      path: 'scenario2',
      name: 'scenario2',
      srvName: '/api/system/auth/manage/generator/scenario2',
      component: () => import('@/views/generator/scenario2/index'),
      meta: {
        title: '2车间（T-CRUD）',
        icon: 'edit'
      }
    },
    {
      path: 'scenario3',
      name: 'scenario3',
      srvName: '/api/system/auth/manage/generator/scenario3',
      component: () => import('@/views/generator/scenario3/index'),
      meta: {
        title: '3车间（B-FLOW）',
        icon: 'edit'
      }
    },
    {
      path: 'scenario4',
      name: 'scenario4',
      srvName: '/api/system/auth/manage/generator/scenario4',
      component: () => import('@/views/generator/scenario4/index'),
      meta: {
        title: '4车间（F-CRUD）',
        icon: 'edit'
      }
    },
    {
      path: 'scenario5',
      name: 'scenario5',
      srvName: '/api/system/auth/manage/generator/scenario5',
      component: () => import('@/views/generator/scenario5/index'),
      meta: {
        title: '五车间',
        icon: 'edit'
      }
    },
    {
      path: 'tg-file-summary-scene1',
      name: 'tg-file-summary',
      srvName: '/api/system/auth/manage/generator/tg-file-summary-scene1',
      component: () => import('@/views/generator/tg-file-summary-scene1/index'),
      meta: {
        title: '生成日志',
        icon: 'edit'
      }
    },
    // {
    //   path: 'scene1',
    //   component: () => import('@/views/generator/scene1/index'),
    //   name: 'scene1',
    //   srvName: '/api/system/auth/manage/generator/scene1',
    //   meta: {
    //     title: 'OLD经典单表【剧情1】',
    //     icon: 'edit'
    //   },
    //   redirect: '/generator/scene1/front-end',
    //   children: [
    //     {
    //       path: 'front-end',
    //       component: () => import('@/views/generator/scene1/front-end/index'),
    //       name: 'scene1frontend',
    //       srvName: '/api/system/auth/manage/generator/scene1/front-end',
    //       meta: {
    //         title: '前端',
    //         icon: 'edit'
    //       }
    //     },
    //     {
    //       path: 'back-end',
    //       component: () => import('@/views/generator/scene1/back-end/index'),
    //       name: 'scene1backend',
    //       srvName: '/api/system/auth/manage/generator/scene1/back-end',
    //       meta: {
    //         title: '后端',
    //         icon: 'edit'
    //       }
    //     }
    //   ],
    // },
    // {
    //   path: 'vueelementapi',
    //   name: 'vueelementapi',
    //   srvName: '/api/system/auth/manage/generator/vueelementapi',
    //   component: () => import('@/views/generator/vueelementapi/index'),
    //   meta: {
    //     title: 'OLD前端API生成',
    //     icon: 'edit'
    //   }
    // },
    // {
    //   path: 'crudsingle',
    //   name: 'crudsingle',
    //   srvName: '/api/system/auth/manage/generator/scene1/front-end-code',
    //   component: () => import('@/views/generator/scene1/front-end/index'),
    //   meta: {
    //     title: 'OLD剧情1前端',
    //     icon: 'edit'
    //   }
    // },
    // {
    //   path: 'vueelementindex',
    //   name: 'vueelementindex',
    //   srvName: '/api/system/auth/manage/generator/vueelementindex',
    //   component: () => import('@/views/generator/vueelementindex/index'),
    //   meta: {
    //     title: 'OLD vueelement索引页',
    //     icon: 'edit'
    //   }
    // }
  ]
}
export default codeGeneratorRouter
