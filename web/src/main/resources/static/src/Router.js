//https://zq99299.gitbooks.io/vue-note/content/chapter/build_dev_env/vue_router.html

import Home from './Home.vue';
import App from './App.vue';
const UserProfile = resolve => require(['./UserProfile.vue'], resolve);
const SignUp = resolve => require(['./SignUp.vue'], resolve);
const PageNotFound = resolve => require(['./PageNotFound.vue'], resolve);
const UserList = resolve => require(['./UserList.vue'], resolve);
const Admin = resolve => require(['./Admin.vue'], resolve);
const Errors = resolve => require(['./Errors.vue'], resolve);

const routes = [
    { path: '/', name: 'home', component: Home },
    { path: '/userProfile/:userUUID' , name: 'userProfile', component: UserProfile },
    { path: '/admin/userProfile/:userUUID' , name: 'userProfileEdit', component: UserProfile, meta:{userProfileEdit: 'true'}},
    { path: '/signup/' , name: 'signUp', component: SignUp,  meta:{signUpType: 'signup'}},
    { path: '/login' , name: 'login', component: SignUp, meta:{signUpType: 'login'}},
    { path: '/userList', name: 'userList', component: UserList },
    { path: '/admin', name: 'Admin', component: Admin },
    { path: '/home', redirect: '/' },
    { path: '/errors/:errorCode', name: 'errors', component: Errors},
    { path: "*", component: PageNotFound }
]

export default routes

