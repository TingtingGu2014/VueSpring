<template id="sign-up-template">
    <div class="col-lg-4 col-lg-off-4 col-md-4 col-md-offset-4 col-sm-12 col-xs-12 " >
        <form id="signup-form">
            <div class="form-group">
                <label for="email" class="control-label">Email</label>
                <input type="email" class="form-control" v-model='email' placeholder="name@domain.com">
                <span v-if="email.length > 1">{{ email_message }}</span>
            </div>

            <div class="form-group">
                <label for="alias" class="control-label">Xprotocol Name</label>
                <input type="text" class="form-control" v-model="alias">
            </div>

            <div class="form-group">
                <label for="firstName" class="control-label">First Name</label>
                <input type="text" class="form-control" v-model="firstName">
                <label for="lastName" class="control-label">Last Name</label>
                <input type="text" class="form-control" v-model="lastName">
            </div>
            
            <div class="form-group">
                <label for="major" class="control-label">Major</label>
                <input type="text" class="form-control" v-model="major">
                <label for="affiliation" class="control-label">Affiliation</label>
                <input type="text" class="form-control" v-model="affiliation">
                <label for="keywords" class="control-label">Key Words of Interest</label>
                <textarea v-model="keywords" placeholder="add key words of interest"></textarea>
            </div>
            
            <div class="form-group">
                <label for="createdDate" class="control-label">Created Date</label>
                <input type="text" class="form-control" v-model="createdDate">
                <label for="active" class="control-label">Active User</label>
                <input type="text" class="form-control" v-model="active">
            </div>
            
            <div class="form-group">
                <label for="address" class="control-label">Street Address</label>
                <input type="text" class="form-control" v-model="address">
                <label for="city" class="control-label">City</label>
                <input type="text" class="form-control" v-model="city">
                <label for="state" class="control-label">State</label>
                <input type="text" class="form-control" v-model="state">
                <label for="zipcode" class="control-label">Zip Code</label>
                <input type="text" class="form-control" v-model="zipcode">
            </div>

            <div class="form-group">
                <button type="submit" class="btn btn-primary" v-on:click="signupsubmit" >Update</button>
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