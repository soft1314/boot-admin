/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const documentationRouter = {
  path: '/documentation',
  component: Layout,
  children: [{
    path: 'index',
    component: () => import('@/views/documentation/index'),
    name: 'Documentation',
    meta: {
      title: 'Documentation',
      icon: 'documentation',
      affix: true
    }
  }]
}
export default documentationRouter
