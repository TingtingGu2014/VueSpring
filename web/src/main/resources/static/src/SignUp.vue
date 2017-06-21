<template id="sign-up-template">
<!--    <div class="col-lg-4 col-lg-off-4 col-md-4 col-md-offset-4 col-sm-12 col-xs-12 col-center" >
    <br><br>
        <form class="form-horizontal" id="signup-form">
            <div class="form-group form-inline">
                <label for="email" class="control-label">Email: &nbsp;&nbsp;&nbsp;</label>
                <input type="email" class="form-control" v-model='email' placeholder="name@domain.com">
                <span v-if="email.length > 1">{{ email_message }}</span>
            </div>

            <div class="form-group form-inline">
                <label for="alias" class="control-label">Name: &nbsp;&nbsp;&nbsp;</label>
                <input type="text" class="form-control" v-model="alias">
            </div>

            <div class="form-group form-inline">
                <label for="password" class="control-label">Password: &nbsp;&nbsp;&nbsp;</label>
                <input type="password" class="form-control" v-model="password">
            </div>

            <div class="form-group form-inline">
                <button type="submit" class="btn btn-primary" v-on:click="signupsubmit" >Sign Up</button>
            </div>	
        </form>
    </div>-->
    
    <div class="container"><br><br>
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
                <button type="submit" class="btn btn-primary" v-on:click="signupsubmit" >Sign Up</button>
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
                    url: '/user',
                    params: {
                    email: this.email,
                            password: this.password,
                            alias: this.alias
                    }
                }).then(function (response) {
                    console.log(response);
                })
                .catch(function (error) {
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