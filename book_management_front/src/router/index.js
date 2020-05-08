import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Shop from '@/components/Shop'
import Product from '@/components/Product'
import Blog from '@/components/Blog'
import Post from '@/components/Post'
import Cart from '@/components/Cart'
import Layout from '@/components/Layout'
import LoginForm from "../components/LoginForm";
import store from '../store/index.js';

Vue.use(Router)
const router = new Router({
  routes: [
    {
      path: '/',
      component: Layout,
      children:[
        {
          path:'/',
          component:Home,
          name:'Home',
          meta: {
            requiresAuth: true
          }
        },
        {
          path:'/shop',
          component:Shop,
          name:'Shop',
          meta: {
            requiresAuth: true
          }
        },
        {
          path:'/product',
          component:Product,
          name:'Product'
        },
        {
          path:'/blog',
          component:Blog,
          name:'Blog'
        },
        {
          path:'/post',
          component:Post,
          name:'Post'
        },
        {
          path:'/cart',
          component:Cart,
          name:'Cart'
        },
        {
          path: '/login',
          component: LoginForm,
          name: 'Login'
        }
      ]

    }
  ],
  mode:'history'
})

router.beforeEach(((to, from, next) => {
  if(to.matched.some(record => record.meta.requiresAuth)) {
    if(!store.getters.isLoggedIn) {
      next({name: 'Login'})
    } else {
      next()
    }
  } else {
    next()
  }
}))

export default router


