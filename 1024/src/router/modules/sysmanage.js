/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const sysManageRouter = {
  path: '/manage',
  name: 'SysManage',
  component: Layout,
  redirect: '/manage/basemanage/dictionary',
  srvName: '/api/system/auth/manage',
  meta: {
    title: '系统管理',
    icon: 'example'
  },
  children: [{
    path: 'basemanage',
    name: 'BaseManage',
    srvName: '/api/system/auth/manage/basemanage',
    component: () => import('@/views/manage/basemanage/index'),
    redirect: '/manage/basemanage/dictionary',
    meta: {
      title: '基础管理',
      icon: 'tree'
    },
    children: [{
      path: 'dictionary',
      name: 'DicManage',
      srvName: '/api/system/auth/manage/basemanage/dictionary',
      component: () => import('@/views/manage/basemanage/dictionary/index'),
      meta: {
        title: '字典管理',
        icon: 'tree'
      }
    },
    {
      path: 'region',
      name: 'DivManage',
      srvName: '/api/system/auth/manage/basemanage/region',
      component: () => import('@/views/manage/basemanage/region/index'),
      meta: {
        title: '区域管理',
        icon: 'tree'
      }
    },
    {
      path: 'organization',
      name: 'OrgManage',
      srvName: '/api/system/auth/manage/basemanage/organization',
      component: () => import('@/views/manage/basemanage/organization/index'),
      meta: {
        title: '组织管理',
        icon: 'tree'
      }
    },
    {
      path: 'employee',
      name: 'EmpManage',
      srvName: '/api/system/auth/manage/basemanage/employee',
      component: () => import('@/views/manage/basemanage/employee/index'),
      meta: {
        title: '人员管理',
        icon: 'tree'
      }
    }
    ]
  },
  {
    path: 'authmanage',
    name: 'AuthManage',
    srvName: '/api/system/auth/manage/authmanage',
    component: () => import('@/views/manage/authmanage/index'),
    redirect: '/manage/authmanage/menu',
    meta: {
      title: '权限管理',
      icon: 'tree'
    },
    children: [{
      path: 'menu',
      name: 'MenuManage',
      srvName: '/api/system/auth/manage/authmanage/menu',
      component: () => import('@/views/manage/authmanage/menu'),
      meta: {
        title: '菜单管理',
        icon: 'table'
      }
    }, {
      path: 'resource',
      name: 'ResourceManage',
      srvName: '/api/system/auth/manage/authmanage/resource',
      component: () => import('@/views/manage/authmanage/resource'),
      meta: {
        title: '功能管理',
        icon: 'table'
      }
    }, {
      path: 'user',
      name: 'UserManage',
      srvName: '/api/system/auth/manage/authmanage/user',
      component: () => import('@/views/manage/authmanage/user'),
      meta: {
        title: '用户管理',
        icon: 'tree'
      }
    },
    {
      path: 'role',
      name: 'RoleManage',
      srvName: '/api/system/auth/manage/authmanage/role',
      component: () => import('@/views/manage/authmanage/role'),
      meta: {
        title: '角色管理',
        icon: 'tree'
      }
    }, {
      path: 'userofrole',
      name: 'UserOfRoleManage',
      srvName: '/api/system/auth/manage/authmanagele/userofrole',
      component: () => import('@/views/manage/authmanage/userofrole'),
      meta: {
        title: '角色-用户',
        icon: 'tree'
      }
    }, {
      path: 'resourceofrole',
      name: 'ResourceOfRoleManage',
      srvName: '/api/system/auth/manage/authmanage/resourceofrole',
      component: () => import('@/views/manage/authmanage/resourceofrole/index'),
      meta: {
        title: '角色-功能',
        icon: 'tree'
      }
    }
    ]
  },
  {
    path: 'operationmanage',
    name: 'OperationManage',
    srvName: '/api/system/auth/manage/operationmanage',
    component: () => import('@/views/manage/operationmanage/index'),
    meta: {
      title: '运行管理',
      icon: 'tree'
    },
    children: [{
      path: 'online',
      name: 'OnlineManage',
      srvName: '/api/system/auth/manage/operationmanage/online',
      component: () => import('@/views/manage/operationmanage/online/index'),
      meta: {
        title: '在线用户',
        icon: 'tree'
      }
    },
    {
      path: 'job',
      name: 'JobManage',
      srvName: '/api/system/auth/manage/operationmanage/job',
      component: () => import('@/views/manage/operationmanage/job/index'),
      meta: {
        title: '定时任务',
        icon: 'tree'
      }
    },
    {
      path: 'task',
      name: 'TaskManage',
      srvName: '/api/system/auth/manage/operationmanage/task',
      component: () => import('@/views/manage/operationmanage/task/index'),
      meta: {
        title: '流程任务',
        icon: 'tree'
      }
    },
    {
      path: 'histask',
      name: 'HisTaskManage',
      srvName: '/api/system/auth/manage/operationmanage/task/his',
      component: () => import('@/views/manage/operationmanage/histask/index'),
      meta: {
        title: '历史任务',
        icon: 'tree'
      }
    },
    // {
    //   path: 'load',
    //   name: 'LoadManage',
    //   srvName: '/api/system/auth/manage/operationmanage/load',
    //   component: () => import('@/views/manage/operationmanage/load/index'),
    //   meta: {
    //     title: '负载监控',
    //     icon: 'tree'
    //   }
    // },
    // {
    //   path: 'pool',
    //   name: 'PoolManage',
    //   srvName: '/api/system/auth/manage/operationmanage/pool',
    //   component: () => import('@/views/manage/operationmanage/pool/index'),
    //   meta: {
    //     title: '池塘监控',
    //     icon: 'tree'
    //   }
    // },
    {
      path: 'log',
      name: 'LogManage',
      srvName: '/api/system/auth/manage/operationmanage/log',
      component: () => import('@/views/manage/operationmanage/log/index'),
      meta: {
        title: '系统日志',
        icon: 'tree'
      }
    },
    {
      path: 'nacos',
      name: 'nacos',
      srvName: '/api/system/auth/manage/operationmanage/nacos',
      component: () => import('@/views/manage/operationmanage/nacos/index'),
      meta: {
        title: 'Nacos',
        icon: 'link'
      }
    },
    {
      path: 'admin',
      name: 'admin',
      srvName: '/api/system/auth/manage/operationmanage/admin',
      component: () => import('@/views/manage/operationmanage/admin/index'),
      meta: {
        title: 'Admin',
        icon: 'link'
      }
    }
    ]
  },
  {
    path: 'definitionmanage',
    name: 'DefManage',
    srvName: '/api/system/auth/manage/definitionmanage',
    component: () => import('@/views/manage/definitionmanage/index'),
    meta: {
      title: '定义管理',
      icon: 'tree'
    },
    children: [
      {
        path: 'model',
        name: 'ModelManage',
        srvName: '/api/system/auth/manage/definitionmanage/model',
        component: () => import('@/views/manage/definitionmanage/model/index'),
        meta: {
          title: '模型管理',
          icon: 'tree'
        }
      },
      {
        path: 'process',
        name: 'ProcessManage',
        srvName: '/api/system/auth/manage/definitionmanage/process',
        component: () => import('@/views/manage/definitionmanage/process/index'),
        meta: {
          title: '流程管理',
          icon: 'tree'
        }
      },
      {
        path: 'drools',
        name: 'DroolsManage',
        srvName: '/api/system/auth/manage/definitionmanage/drools',
        component: () => import('@/views/manage/definitionmanage/drools/index'),
        meta: {
          title: '规则管理',
          icon: 'tree'
        }
      }
    ]
  },
  {
    path: 'datamaintain',
    name: 'DataMaintain',
    srvName: '/api/system/auth/manage/datamaintain',
    component: () => import('@/views/manage/datamaintain/index'),
    meta: {
      title: '数据处理',
      icon: 'tree'
    },
    children: [{
      path: 'sqlinput',
      name: 'SqlInput',
      srvName: '/api/system/auth/manage/datamaintain/sqlinput',
      component: () => import('@/views/manage/datamaintain/sqlinput/index'),
      meta: {
        title: '提交',
        icon: 'tree'
      }
    }, {
      path: 'sqlexec',
      name: 'sqlexec',
      srvName: '/api/system/auth/manage/datamaintain/sqlexec',
      component: () => import('@/views/manage/datamaintain/sqlexec/index'),
      meta: {
        title: '执行',
        icon: 'tree'
      }
    }]
  }
  ]
}
export default sysManageRouter
