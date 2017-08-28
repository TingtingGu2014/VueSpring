<template id="sign-up-template">
    
    <div class="container"><br><br>
        <my-vuetable></my-vuetable>
    </div>
</template>

<script>
    
    var Utils = require('./Utils')
    
    var loggedIn = !Utils.isEmpty(Utils.readCookie('loggedIn'))
    if(loggedIn != true){
        document.location.href = '/login'
    }
    
    var isAdminUser = Utils.isAdminUser();
    if(!isAdminUser){
        document.location.href = '/errors/403'
    }

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
        computed: {
            ...mapGetters({
                isUserDetailsFetched: 'userModule/isUserDetailsFetched',
                getUserDetails: 'userModule/getUserDetails',
            })
        },
        components: {
            VuePassword,
        },
        watch: {
            email: function(value) {
                this.validate_email(value, 'email_message')
            },
            '$route' (to, from) {
                this.path = to.meta.signUpType
            },
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
            
                if(this.path.indexOf('signup') >= 0){
                    if(Utils.isEmpty(this.email) || Utils.isEmpty(this.password) || Utils.isEmpty(this.password2)){
                        alert("Please fill your complete information before registration.");
                        return;
                    }
                    if(this.password != this.password2){
                        alert("The two passwords are different.");
                        return;
                    }
                }
                else{
                    if(Utils.isEmpty(this.email) || Utils.isEmpty(this.password)){
                        alert("Please fill your complete information before registration.");
                        return;
                    }
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
                    method: 'POST',
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
                        localStorage.userInfo = JSON.stringify(data)
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
            },
            ...mapMutations({                
                setUserDetails: 'userModule/setUserDetails',
                setDetailsFetched: 'userModule/setDetailsFetched',
            }),
        }
    }
</script>

<style scoped>
    
</style>