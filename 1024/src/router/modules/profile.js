/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout'

const profileRouter = {
  path: '/profile',
  component: Layout,
  redirect: '/profile/index',
  hidden: false,
  children: [
    {
      path: 'index',
      component: () => import('@/views/profile/index'),
      name: 'Profile',
      meta: { title: 'Profile', icon: 'user', noCache: true }
    }
  ]
}
export default profileRouter
