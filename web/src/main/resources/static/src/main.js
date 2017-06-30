import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)
 
import App from './App.vue'
import routejs from './Router.js'

const router = new VueRouter({
  mode: 'history',
  routes: routejs
})

new Vue({
    router,
    el: '#app',
    render:h => h(App)
})