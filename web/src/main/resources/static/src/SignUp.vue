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
            <br>
            <div class="form-group row" v-if="path.includes('signup')">
                <label for="alias" class="col-sm-2 col-form-label">Name:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" v-model="alias">
                </div>
            </div>
            <br>
            <div class="form-group row">
                <label for="password" class="col-sm-2 col-form-label">Password:</label>
                <div class="col-sm-10">                
                    <vue-password v-model="password" class="form-control" :user-inputs="[email]" placeholder="Password"></vue-password>
                </div>
            </div>
            <br>
            <div v-if="path.includes('signup')" class="form-group row">
                <label for="password2" class="col-sm-2 col-form-label">Confirm Password:</label>
                <div class="col-sm-10">                
                    <input type="password" class="form-control" v-model="password2" placeholder="Confirm Password">
                </div>
                <br>
            </div>
            <div class="form-group form-inline justify-content-md-center">
            <button type="button" class="btn btn-primary " v-on:click="signupsubmit" >
                <span v-if="path.includes('signup')" class="text-center">Sign Up</span>
                <span v-else>Sign In</span>
            </button>
            </div>
        </form>
    </div>
</template>

<script>
    var Utils = require('./Utils')
    import VuePassword from 'vue-password'
    export default {
        data: function() {
            return {
                email: '',
                alias: '',
                password: '',
                password2: '',
                path: window.location.pathname,
            }
        },
        components: {
            VuePassword,
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
            
                if(Utils.isEmpty(this.email) || Utils.isEmpty(this.password) || Utils.isEmpty(this.password2)){
                    alert("Please fill your complete information before registration.");
                    return;
                }
                
                if(this.password != this.password2){
                    alert("The two passwords are different.");
                    return;
                }
                
                if (event){
                    event.preventDefault()
                }
                
                var qs = require('qs');
                var url = '';
                if(this.path.includes('signup')) {
                    url = '/api/signUp'
                    Utils.signUp(qs.stringify(this.$data), url)
                    this.setDetailsFetched(false)
                }
                else {
                    url = '/api/signIn'
                }
                axios({
                    method: 'get',
                    url: url,
                    dataType: 'json',
                    headers: {
                        'X-Requested-With': 'XMLHttpRequest',
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
                        this.setDetailsFetched(false)
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