<template id="login-template">
    <!--<div class="row form-inline" >-->
        <form class="form-inline" v-if="loggedin">
            <span class="glyphicon glyphicon-user"></span> 
            <!--<router-link to="/profile/userId">Go to notfound</router-link>-->
            <router-link :to="{ name: 'userProfile', params: { id: userId }}">
                <span v-if="userAlias">
                    {{userAlias}}
                </span>
                <span v-else>
                    {{userEmail}}
                </span>
            </router-link>
            
             &nbsp;&nbsp;
            <a href="/signout"><span class="fa fa-sign-out"></span> Sign Out</a>
        </form>
        <form class="form-inline" v-else>
            <input type="email" class="form-control " placeholder="email" v-model='emaillogin'>
            &nbsp;
            <input type="password" class="form-control " placeholder="password" v-model="passwordlogin">
            &nbsp;
            <a href="#" v-on:click="loginsubmit"><span class="fa fa-sign-in"></span> Login</a> 
            &nbsp;&nbsp;
            <a href="/signup"><span class="fa fa-user"></span> Sign Up</a> 
        </form>        
    
</template>     

<script>

                
    export default {
        props: {
            
        },
        data: function () {
            return {
                loggedin: false,
                userEmail: '',
                userAlias: '',
                userId: '',
                emaillogin: '',
                passwordlogin: '',
            }
        },
        methods: {
            loginsubmit: function (message, event) {
                if (event){
                    event.preventDefault()
                }
    //            alert(message)
                axios({
                    method: 'get',
                    url: '/user',
                    params: {
                        email: this.emaillogin,
                        password: this.passwordlogin
                    }
                })
                .then( (response) => {
                    var status = response.status;
                    var data = response.data;
                    if(status == 200){
                        alert("200");
                        this.loggedin = true;
                        this.userEmail = data.email;
                        this.userAlias = data.alias;
                        this.userId = data.userId;
                        alert(this.userAlias);
                    }
                    else{
                        alert("not 200");
                    }                                   
                    console.log(this.userEmail);
                  })
                  .catch( (error) => {
                    console.log(error);
                  });
            
            },
        }
    }
</script>

<style scoped>
    input.form-control {
        width: 100px !important;
    }
</style>