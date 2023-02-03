import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import ProfileView from '../views/ProfileView.vue'
import GroupView from '../views/GroupView.vue'
import MeetingCreate from '@/views/MeetingCreate'
import NotFound from '@/views/NotFound.vue'
import LoginView from '../views/LoginView'
import JoinView from '../views/JoinView'

const routes = [
  {
    path: "/",
    name: "login",
    component: LoginView,
  },
  {
    path: "/join",
    name: "join",
    component: JoinView,
  },
  {
    path: '/',
    name: 'main',
    component: MainView,
    children:[
      {
        path: '',
        name: 'submain',
        component: () => import(/* webpackChunkName: "SubMain" */ '@/views/SubMain.vue')
      },
      {
        path: 'profile',
        name: 'profile',
        component: ProfileView,
        children: [
          {
            path: '',
            name: 'profileDetail',
            component: () => import(/* webpackChunkName: "groupMain" */ '@/components/account/ProfileDetail')
          },
          {
            path: 'update',
            name: 'profileUpdate',
            component: () => import(/* webpackChunkName: "groupMain" */ '@/components/account/ProfileUpdate.vue')
          },
          {
            path: 'changepassword',
            name: 'changePassword',
            component: () => import(/* webpackChunkName: "groupMain" */ '@/components/account/ChangePassword.vue')
          },
        ]
      },
      {
        path: 'group',
        name: 'group',
        component: GroupView,
        children: [
          {
            path: '',
            name: 'groupMain',
            component: () => import(/* webpackChunkName: "groupMain" */ '@/components/group/GroupMain.vue')
          },
          {
            path: 'create',
            name: 'groupCreate',
            component: () => import(/* webpackChunkName: "groupCreate" */ '@/components/group/GroupCreate.vue')
          },
          {
            path: ':groupId',
            name: 'GroupUser',
            component: () => import(/* webpackChunkName: "GroupUser" */ '@/components/group/GroupUser.vue')
          },
        ],
      },
    ]
  },
  {
    path: "/meetingCreate",
    name: "meetingCreate",
    component: MeetingCreate,
  },
  {
    path: '/meeting-end',
    name: 'meeting-end',
    component: () => import(/* webpackChunkName: "meetingEnd" */ '@/components/meeting/meeting-end.vue')
  },
  {
    // 404 page
    path: "/404",
    name: "NotFound",
    component: NotFound,
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: { name: "NotFound" },
  },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
