<template id="login-template">
    <form class="form-inline" v-if=loggedIn>        
        <!--<router-link to="/profile/userId">Go to notfound</router-link>-->
        <router-link :to="{ name: 'userProfile', params: { userUUID: userUUID }  }" >
        <!--<a href="#" v-on:click="getUserDetails">--> 
            <span class="fa fa-user"></span>    
            <span v-if="userAlias">
                {{userAlias}}
            </span>
            <span v-else>
                {{userEmail}}
            </span>
        <!--</a>-->
        </router-link>

         &nbsp;&nbsp;
        <a href="#" v-on:click="logoutsubmit"><span class="fa fa-sign-out"></span>&nbsp;Sign Out</a> 
    </form>
    <form class="form-inline" v-else>
        <input type="email" class="form-control " placeholder="email" v-model='emaillogin'>
        &nbsp;
        <input type="password" class="form-control " placeholder="password" v-model="passwordlogin">
        &nbsp;
        <a href="#" v-on:click="loginsubmit"><span class="fa fa-sign-in"></span>&nbsp;Sign In</a> 
        &nbsp;&nbsp;             
        <router-link :to="{ name: 'signUp'}"><span class="fa fa-user"></span>&nbsp;Sign Up</router-link>
    </form>        
    
</template>     

<script>

    import { mapGetters, mapMutations } from 'vuex'
    
    var Utils = require('./Utils')
    var loggedIn = !Utils.isEmpty(Utils.readCookie('loggedIn'));
    if(loggedIn === false){
        localStorage.userEmail = '';
        localStorage.userName = '';
        localStorage.userUUID = '';
    }
                
    export default {
        
        props: {
            
        },
        data: function () {
            return {
                loggedIn: loggedIn,
                userEmail: localStorage.userEmail,
                userAlias: localStorage.userAlias,
                userUUID: localStorage.userUUID,
                emaillogin: '',
                passwordlogin: '',
            }
        },        
        methods: {
            loginsubmit: function (message, event) {
                
                if(Utils.isEmpty(this.emaillogin) || Utils.isEmpty(this.passwordlogin)){
                    alert("Please fill your email and password before sign in.");
                    return;
                }
                
                if (event){
                    event.preventDefault()
                }

                axios({
                    method: 'post',
                    url: '/api/signIn',
                    dataType: 'json',
                    headers: {'X-Requested-With': 'XMLHttpRequest'},
                    auth: {
                        username: this.emaillogin,
                        password: this.passwordlogin
                    },
                })
                .then( (response) => {
                    var status = response.status;
                    var data = response.data;

                    if(status == 200){
//                        alert("200");
                        localStorage.userEmail = data.email;
                        localStorage.userName = data.alias;
                        localStorage.userUUID = data.userUUID;
                        this.loggedIn = true;
                        this.userEmail = data.email;
                        this.userAlias = data.alias;
                        this.userUUID = data.userUUID;    
                        this.setDetailsFetched(false)
                        document.location.href = '/home';
                    }
                    else{
                        alert("not 200");
                        return;
                    }                                   
                })
                .catch( (error) => {
                    console.log(error);
                });                                    
            },
            logoutsubmit: function (message, event) {
                if (event){
                    event.preventDefault()
                }

                axios({
                    method: 'post',
                    url: '/api/logout',
                    dataType: 'json',
                })
                .then( (response) => {
                    var status = response.status;
                    var data = response.data;

                    if(status == 200 || status == 204){
                        localStorage.userEmail = '';
                        localStorage.userName = '';
                        localStorage.userUUID = '';
                        var loggedIn = Utils.readCookie('loggedIn');
                        if(loggedIn == 'true'){
                            Utils.eraseCookie('loggedIn');
                        }
                        this.loggedIn = false;
                        this.userEmail = '';
                        this.userAlias = '';
                        this.userUUID = '';
                        this.setDetails(null);
                        this.setDetailsFetched(false)
                        document.location.href = '/home';
                    }
                    else{
                        alert("not 200 "+status);
                    }                                   
                    console.log(this.userEmail);
                  })
                  .catch( (error) => {
                    console.log(error);
                  });
            
            },
            ...mapMutations({                
                setUserDetails: 'userModule/setDetails',
                setDetailsFetched: 'userModule/setDetailsFetched',
            }),
        }
    }
    
    function make_base_auth(user, password) {
        var tok = 'Basic ' + user + ':' + password;
        return tok;
//        var hash = btoa(tok);
//        return 'Basic ' + hash;
    }
</script>

<style scoped>
    input.form-control {
        width: 100px !important;
    }
</style>