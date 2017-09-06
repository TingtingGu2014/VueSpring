import Vue from 'vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import { TableComponent, TableColumn } from 'vue-table-component';
import VuePassword from 'vue-password'

Vue.use(VueRouter)
Vue.use(Vuex)
Vue.component(VuePassword)
Vue.component('table-component', TableComponent);
Vue.component('table-column', TableColumn);
 
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

//axios.interceptors.request.use(function (config) {
//    alert('ok');
//    return config;
//  }, function (error) {
//    // Do something with request error
//    alert('not ok');
//    return Promise.reject(error);
//  });

new Vue({
    router,
    store,
    el: '#app',
    render:h => h(App)
})