/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const guideRouter = {
  path: '/guide',
  component: Layout,
  redirect: '/guide/index',
  children: [
    {
      path: 'index',
      component: () => import('@/views/guide/index'),
      name: 'Guide',
      meta: { title: '指导', icon: 'guide', noCache: true }
    }
  ]
}
export default guideRouter
