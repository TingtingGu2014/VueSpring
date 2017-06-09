<template id="sign-up-template">
    <div class="col-lg-4 col-lg-off-4 col-md-4 col-md-offset-4 col-sm-12 col-xs-12 " >
        <form id="signup-form">
            <div class="form-group">
                <label for="email" class="control-label">Email</label>
                <input type="email" class="form-control" v-model='email' placeholder="name@domain.com">
                <span v-if="email.length > 1">{{ email_message }}</span>
            </div>

            <div class="form-group">
                <label for="alias" class="control-label">Name</label>
                <input type="text" class="form-control" v-model="alias">
            </div>

            <div class="form-group">
                <label for="password" class="control-label">Password</label>
                <input type="password" class="form-control" v-model="password">
            </div>

            <div class="form-group">
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