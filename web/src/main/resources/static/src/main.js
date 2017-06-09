import Vue from 'vue'
//引用路由插件
import VueRouter from 'vue-router'

//使用路由插件
Vue.use(VueRouter)

//引入组件  
import App from './App.vue'
import routejs from './Router.js'


//使用路由规则
const router = new VueRouter({
  mode: 'history',
  routes: routejs
})
//加载路由规则
new Vue({
    router,
    el: '#app',
    render:h => h(App)
})