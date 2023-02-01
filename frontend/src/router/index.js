import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import ProfileView from '../views/ProfileView.vue'
import GroupView from '../views/GroupView.vue'
import profileDetail from '@/components/layouts/ProfileDetail'
import profileUpdate from '@/components/layouts/ProfileUpdate'
import changePassword from '@/components/layouts/ChangePassword'
import MeetingCreate from '@/views/MeetingCreate'
import meetingEnd from '@/components/layouts/meeting-end'
import NotFound from '@/views/NotFound.vue'
import LoginView from '../views/LoginView'
import JoinView from '../views/JoinView'
import MainView from '../views/MainView'

const routes = [
  {
    path: '/',
    name: 'login',
    component: LoginView
  },
  {
    path: '/join',
    name: 'join',
    component: JoinView
  },
  {
    path: '/main',
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
    component: GroupView,
    children: [
      {
        path: '',
        name: 'groupMain',
        component: () => import(/* webpackChunkName: "groupMain" */ '@/components/layouts/GroupMain.vue')
      },
      {
        path: 'create',
        name: 'groupCreate',
        component: () => import(/* webpackChunkName: "groupCreate" */ '@/components/layouts/GroupCreate.vue')
      },
      {
        path: ':groupId',
        name: 'GroupUser',
        component: () => import(/* webpackChunkName: "GroupUser" */ '@/components/layouts/GroupUser.vue')
      },
    ],
  },
  {
    path: '/meetingCreate',
    name: 'meetingCreate',
    component: MeetingCreate
  },
  {
    path: '/meeting-end',
    name: 'meeting-end',
    component: meetingEnd
  },
  {
    // 404 page
    path: '/404',
    name: 'NotFound',
    component: NotFound
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: { name: 'NotFound' }
  }
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
