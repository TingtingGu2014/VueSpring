<template id="sign-up-template">
    
    <div class="container"><br><br>
        <span>Please sign in or register as a user to use XProtocol services.</span>
        <form>
            <div class="form-group row justify-content-md-center">
              <label for="inputEmail3" class="col-sm-2 col-form-label">Email:</label>
              <div class="col-sm-10">                
                <input type="email" class="form-control" v-model='email' placeholder="Email">
              </div>
              <span v-if="email.length > 1">{{ email_message }}</span>
            </div>
            <div class="form-group row" v-if="path.includes('signup')">
                <label for="alias" class="col-sm-2 col-form-label">Name:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="alias">
                </div>
            </div>
            <div class="form-group row">
                <label for="inputPassword3" class="col-sm-2 col-form-label">Password:</label>
                <div class="col-sm-10">                
                    <input type="password" class="form-control" v-model="password" placeholder="Password">
                </div>
            </div>
            
            <div class="form-group form-inline">
            <button type="button" class="btn btn-primary" v-on:click="signupsubmit" >
                <span v-if="path.includes('signup')">Sign Up</span>
                <span v-else>Sign In</span>
            </button>
            </div>
        </form>
    </div>
</template>

<script>

    export default {
        data: function() {
            return {
                email: '',
                alias: '',
                password: '',
                path: window.location.pathname,
            }
        },
        watch: {
            email: function(value) {
            this.validate_email(value, 'email_message')
            }
        },
        methods: {
            validate_email (value, email_message) {
                if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value)) {
                    this[email_message] = '';
                    return true;
                } else {
                    this[email_message] = 'Keep typing...waiting for a valid email';
                    return false;
                }
            },
            signupsubmit: function (message, event) {
                
                if (event){
                    event.preventDefault()
                }

                axios({
                    method: 'post',
                    url: '/signIn',
                    dataType: 'json',
                    headers: {
//                        "Authorization": make_base_auth(this.emaillogin, this.passwordlogin)
                        headers: {'X-Requested-With': 'XMLHttpRequest'},
                    },
                    auth: {
                        username: this.email,
                        password: this.password,
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
                        document.location.href = '/home';
                    }
                    else{
                        alert("Error status : " + status);
                        return;
                    }                                   
                  })
                  .catch( (error) => {
                    console.log(error);
                  });                                    
            },
            set_current_user: function() {
                alert('new user!');
            }
        }
    }
</script>

<style scoped>
    
</style>