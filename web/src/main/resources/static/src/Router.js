//https://zq99299.gitbooks.io/vue-note/content/chapter/build_dev_env/vue_router.html

import Home from './Home.vue';
import App from './App.vue';
const UserProfile = resolve => require(['./UserProfile.vue'], resolve);
const SignUp = resolve => require(['./SignUp.vue'], resolve);
const PageNotFound = resolve => require(['./PageNotFound.vue'], resolve);

const routes = [
    { path: '/', name: 'home', component: Home },
    { path: '/profile/:id' , name: 'userProfile', component: UserProfile },
    { path: '/signup/' , name: 'signUp', component: SignUp },
    { path: '/login' , name: 'login' , component: SignUp},
    { path: '/home', redirect: '/' },
    { path: "*", component: PageNotFound }
]

export default routes

