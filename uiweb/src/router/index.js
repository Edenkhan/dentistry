import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../views/Home'
import login from '../views/login'
import sjyz from '../views/sjyz'
import yuyue from '../views/yuyue'
import yy_succ from '../views/yy_succ'
import cd from '../views/chooseDoc'
import wszl from '../views/wszl'
import pw from '../views/prewszl'
import detail from '../views/detail'
import me from '../views/me'
import succ from '../views/succ'
import phonesucc from '../views/phonesucc'
import yym from '../views/yuyueMessage'
import dui from '../views/duiCode'
import md from '../views/myding'
import dd from '../views/dingDetail'
import yr from '../views/yyrecord'
import mr from '../views/myrecord'
import rl from '../views/recordlist'
import wd from '../views/wodeRecord'
import zs from '../views/zhSet'
import cp from '../views/cphone'
import xp from '../views/xphone'
import sm from '../views/setname'
import test from '../views/test'

import store from '../store';


Vue.use(VueRouter);


const routes = [
  {
    path: '/',
    component: Home
  },
  {
    path: '/login',
    component: login
  },
  {
    path: '/sjyz',
    component: sjyz
  },
  // 添加路由守卫
  {
    path: '/yuyue',
    component: yuyue,
    meta:{
      requireAuth:true
    }
  },
  {
    path: '/yy_succ',
    component: yy_succ
  },
  {
    path: '/cd',
    component: cd
  },
  {
    path: '/wszl',
    component: wszl
  },
  {
    path: '/pw',
    component: pw
  },
  {
    path: '/detail',
    component: detail
  },
  {
    path: '/me',
    component: me
  },
  {
    path: '/succ',
    component: succ
  },
  {
    path: '/phonesucc',
    component: phonesucc
  },
  {
    path: '/yym',
    component: yym
  },
  {
    path: '/dui',
    component: dui
  },
  {
    path: '/md',
    component: md
  },
  {
    path: '/dd',
    component: dd
  },
  {
    path: '/yr',
    component: yr
  },
  {
    path: '/mr',
    component: mr
  },
  {
    path: '/rl',
    component: rl
  },
  {
    path: '/wd',
    component: wd
  },
  {
    path: '/zs',
    component: zs
  },
  {
    path: '/cp',
    component: cp
  },
  {
    path: '/xp',
    component: xp
  },
  {
    path: '/sm',
    component: sm
  },
  {
    path: '/test',
    component: test
  },
];



const router = new VueRouter({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to,from,next)=>{
  if(to.matched.some(r=>r.meta.requireAuth)){
    // 进行检测,store中有没有isLogin
    // sessionStorage中有没有isLogin
    if(store.state.isLogin==false || sessionStorage.getItem('isLogin')==false){
      
      router.push({path:'/login'});
    }else{
      next();
    }
  }else{
    //必须要写next()
    next();
  }
});


export default router
