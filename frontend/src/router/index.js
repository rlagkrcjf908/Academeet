import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import ProfileView from '../views/ProfileView.vue'
import GroupView from '../views/GroupView.vue'
import profileDetail from '@/components/layouts/ProfileDetail'
import profileUpdate from '@/components/layouts/ProfileUpdate'
import changePassword from '@/components/layouts/ChangePassword'

const routes = [
  {
    path: '/',
    name: 'main',
    component: MainView
  },
  {
    path: '/profile',
    name: 'profile',
    component: ProfileView,
    children: [
      {
        path: '',
        name: 'profileDetail',
        component: profileDetail
      },
      {
        path: 'update',
        name: 'profileUpdate',
        component: profileUpdate
      },
      {
        path: 'changepassword',
        name: 'changePassword',
        component: changePassword
      },
    ]
  },
  {
    path: '/group',
    name: 'group',
    component: GroupView
  },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
