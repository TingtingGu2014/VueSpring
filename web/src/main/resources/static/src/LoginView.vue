<template id="login-template">
    <!--<div class="row form-inline" >-->
        <form class="form-inline" v-if="loggedin">
            <span class="glyphicon glyphicon-user"></span> 
            <a href="#" v-if="userAlias">
                {{userAlias}}
            </a>
            <a href="#" v-else>
                {{userEmail}}
            </a>
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
    <!--</div>-->
<!--    <table class="table form-inline" style="margin-top:2.3%">
        <tr v-if="loggedin">
            <td style="padding-top:20%">
                <span class="glyphicon glyphicon-user"></span> 
                <a v-if="userAlias">
                    {{userAlias}}
                </a>
                <a v-else>
                    {{userEmail}}
                </a>
            </td>
        </tr>
        <tr v-else>
            <td>
                <input type="email" class="form-control " placeholder="email" v-model='emaillogin'>
                &nbsp;
                <input type="password" class="form-control " placeholder="password" v-model="passwordlogin">
                &nbsp;
                <a href="#" v-on:click="loginsubmit"><span class="glyphicon glyphicon-log-in"></span> Login</a> 
                &nbsp;&nbsp;
            </td>
            <td>
                <a href="/signup"><span class="glyphicon glyphicon-user"></span> Sign Up</a>
            </td> 
        </tr>        
    </table>-->
    
</template>     

<script>

                
    export default {
        props: {
            
        },
        data: function () {
            return {
                loggedin: true,
                userEmail: 'taocom',
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