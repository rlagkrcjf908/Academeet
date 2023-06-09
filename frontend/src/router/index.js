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
  // 로그인
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    beforeEnter: (to, from, next) => {
      const isAuthenticated = store.state.accountStore.isAuthenticated
      if ( isAuthenticated === true ) {
        next({ name : 'submain' })
      }else{
        next()
      }
    }
  },
  // 회원가입
  {
    path: '/join',
    name: 'join',
    component: JoinView,
    beforeEnter: (to, from, next) => {
      const isAuthenticated = store.state.accountStore.isAuthenticated
      if ( isAuthenticated === true ) {
        next({ name : 'submain' })
      }else{
        next()
      }
    }
  },
  // 메인페이지
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
        path: 'meetingCreate',
        name: 'meetingCreate',
        component: MeetingCreate
      },
      {
        path: 'profile',
        name: 'profile',
        component: ProfileView,
        children: [
          // 프로필 상세
          {
            path: '',
            name: 'profileDetail',
            component: () => import(/* webpackChunkName: "groupMain" */ '@/components/account/ProfileDetail')
          },
          // 프로필 수정
          {
            path: 'update',
            name: 'profileUpdate',
            component: () => import(/* webpackChunkName: "groupMain" */ '@/components/account/ProfileUpdate.vue')
          },
          // 비밀번호 변경
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
          // 로그인 된 유저의 그룹 리스트
          {
            path: '',
            name: 'groupMain',
            component: () => import(/* webpackChunkName: "groupMain" */ '@/components/group/GroupMain.vue')
          },
          // 그룹 생성
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
              
              // 그룹의 유저 리스트
              {
                path: '',
                name: 'groupUser',
                component: () => import(/* webpackChunkName: "GroupUser" */ '@/components/group/GroupUser.vue'),
              },
              // 출석 관련 routes
              {
                //개인출석테스트 중
                path: `attdUser/:selectUserId/:hostId`,
                name: "attdUser",
                component: () => import(/* webpackChunkName: "attdUser" */"@/components/group/attendance/AttdUser.vue"),
              },
              {
                //전체 출석보기
                path: `attdList/:hostId`,
                name: "attdList",
                component: () => import(/* webpackChunkName: "attdList" */"@/components/group/attendance/AttdList.vue"),
              },
              // 전체 공지사항 조회
              {
                path: 'article',
                name: 'articleList',
                component: () => import(/* webpackChunkName: "articleList" */"@/components/group/article/ArticleList.vue"),
              },
              // 공지글 작성
              {
                path: 'article/create',
                name: 'articleCreate',
                component: () => import(/* webpackChunkName: "articleCreate" */"@/components/group/article/ArticleCreate.vue"),
              },
              // 공지글 조회
              {
                path: 'article/:articleId',
                name: 'articleDetail',
                component: () => import(/* webpackChunkName: "articleDetail" */"@/components/group/article/ArticleDetail.vue"),
              },
              // 공지글 수정
              {
                path: 'article/:articleId/update',
                name: 'articleUpdate',
                component: () => import(/* webpackChunkName: "articleUpdate" */"@/components/group/article/ArticleUpdate.vue"),
              },
              //그룹 자료실
              {
                path:'refer',
                name:'groupRefer',
                component: () => import(/* webpackChunkName: "groupRefer "*/ "@/components/group/refer/ReferItem.vue"),
              
              },
            ]
          },
        ],
      },
      {
        path: 'list',
        name: 'listMain',
        component: ListView
        // component: () => import(/* webpackChunkName: "listMain" */ '@/components/list/ListMain.vue')
      },
    ]
  },
  {
    path: '/meeting',
    name: 'meeting',
    component: MeetingView
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