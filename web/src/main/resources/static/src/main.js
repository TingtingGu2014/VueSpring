import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'

Vue.use(VueRouter)
Vue.use(Vuex)
 
import App from './App.vue'
import routejs from './Router.js'
import userModule from './userModule.js';

const router = new VueRouter({
  mode: 'history',
  routes: routejs
})

const store = new Vuex.Store({
    modules: {
        userModule : userModule,
    },
});

new Vue({
    router,
    store,
    el: '#app',
    render:h => h(App)
})