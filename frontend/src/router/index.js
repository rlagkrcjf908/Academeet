import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import ProfileView from '../views/ProfileView.vue'
import GroupView from '../views/GroupView.vue'
import MeetingView from '../views/MeetingView.vue'
import MeetingCreate from '@/views/MeetingCreate'
import NotFound from '@/views/NotFound.vue'
import LoginView from '../views/LoginView'
import JoinView from '../views/JoinView'
import ListView from '../views/ListView'

import store from "@/store/index"

const routes = [
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    beforeEnter: (to, from, next) => {
      const isAuthenticated = store.state.accountStore.isAuthenticated
      if ( isAuthenticated === true ) {
        console.log('로그인 된 사용자입니다');
        next({ name : 'submain' })
      }else{
        next()
      }
    }
  },
  {
    path: '/join',
    name: 'join',
    component: JoinView,
    beforeEnter: (to, from, next) => {
      const isAuthenticated = store.state.accountStore.isAuthenticated
      if ( isAuthenticated === true ) {
        console.log('로그인 된 사용자입니다');
        next({ name : 'submain' })
      }else{
        next()
      }
    }
  },

  {
    path: '/',
    name: 'main',
    component: MainView,
    // beforeEnter: requireToken(),
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
            name: 'groupDetail',
            component: () => import(/* webpackChunkName: "GroupDetail" */ '@/components/group/GroupDetail.vue'),
          
            children: [
              {
                path: '',
                name: 'groupUser',
                component: () => import(/* webpackChunkName: "GroupUser" */ '@/components/group/GroupUser.vue'),
              },
              // 출석 관련 routes
              {
                //attdView
                path: `attdView`,
                name: "attdView2",
                component: () => import(/* webpackChunkName: "attdUser" */"@/views/AttdView2.vue"),
              },
              {
                //개인출석테스트 중
                // path: `attdUser`,
                path: `attdUser/:userId/:hostId`,
                name: "attdUser",
                component: () => import(/* webpackChunkName: "attdUser" */"@/components/group/attendance/AttdListUser.vue"),
              },
              {
                //전체 출석보기
                // path: `attdList`,
                path: `attdList/:hostId`,
                name: "attdList",
                component: () => import(/* webpackChunkName: "attdList" */"@/components/group/attendance/AttdList.vue"),
              },
                //상세출석 - 호스트
              { path: `attModify`,
                // path: `attModify/:{groupId}/:{userId}`,
                name: "attModify",
                component: () => import(/* webpackChunkName: "attModify" */"@/components/group/attendance/AttModify.vue")
              },
            ]
          },
        ],
      },
      {
        path: 'list',
        name: 'list',
        component: ListView,
        children: [
          {
            path: '',
            name: 'listMain',
            component: () => import(/* webpackChunkName: "listMain" */ '@/components/list/ListMain.vue')
          },
          // {
          //   //전체 출석보기
          //   // path: `meetingList`,
          //   path: `meetingList`,
          //   name: "meetingList",
          //   component: () => import(/* webpackChunkName: "meetingList" */"@/components/list/MeetingList.vue"),
          // },
        ]
      },
    ]
  },
  {
    path: '/meeting',
    name: 'meeting',
    component: MeetingView
  },
  {
    path: '/meetingCreate',
    name: 'meetingCreate',
    component: MeetingCreate
  },
  {
    path: '/meeting-end',
    name: 'meeting-end',
    component: () => import(/* webpackChunkName: "meetingEnd" */ '@/components/meeting/meeting-end.vue')
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
]
const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  const isAuthenticated = store.state.accountStore.isAuthenticated
  if (to.name !== 'login' && to.name !== 'join' && !isAuthenticated) next({ name: 'login' })
  else next()
})


export default router
