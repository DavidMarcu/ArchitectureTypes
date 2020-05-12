import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Layout from '@/components/Layout'
import LoginForm from "@/components/LoginForm";
import SignupForm from "@/components/SignupForm";
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
          path: '/login',
          component: LoginForm,
          name: 'Login'
        },
        {
          path: '/signup',
          component: SignupForm,
          name: 'Signup'
        },
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


