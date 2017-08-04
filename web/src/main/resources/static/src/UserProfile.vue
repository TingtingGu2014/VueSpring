<template id="sign-up-template">
    <div class="col-md-8 offset-md-2">
        <span class="anchor" id="formUserEdit"></span>
        <br>

        <!-- form user info -->
        <div class="card card-outline-secondary">
            <div class="card-header">
                <h6 class="mb-0">Update Your Profile</h6>
            </div>
            <div class="card-block">
                <form class="form" enctype="multipart/form-data" v-bind:action="'/api/userProfile/'+userUUID" method="post" name="userProfileInfo" role="form" autocomplete="off">
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label">First name</label>
                        <div class="col-lg-9">
                            <input class="form-control" type="text" v-model="firstName">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label">Last name</label>
                        <div class="col-lg-9">
                            <input class="form-control" type="text" v-model="lastName">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label">Email</label>
                        <div class="col-lg-9">
                            <input class="form-control" type="email" v-model="email">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label">Xprotocol Name</label>
                        <div class="col-lg-9">
                            <input class="form-control" type="text" v-model="alias">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label">Major</label>
                        <div class="col-lg-9">
                            <input class="form-control" type="text" v-model="major">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label">Affiliation</label>
                        <div class="col-lg-9">
                            <input class="form-control" type="text" v-model="affiliation">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label">Address</label>
                        <div class="col-lg-9">
                            <input class="form-control" type="text" v-model="address">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-1 col-form-label form-control-label">City</label>
                        <div class="col-sm-4">
                            <input class="form-control" type="text" v-model="city">
                        </div>
                        <label class="col-sm-1 col-form-label form-control-label">State</label>
                        <div class="col-sm-2">
                            <input class="form-control" type="text" v-model="state">
                        </div>
                        <label class="col-sm-2 col-form-label form-control-label">Zip code</label>
                        <div class="col-sm-2">
                            <input class="form-control" type="text" v-model="zipcode">
                        </div>
                    </div>
                    
                    <div class="form-group row">
                        <label class="col-md-4 col-form-label form-control-label"></label>
                        <div class="col-md-8">
                            <input type="reset" class="btn btn-secondary" value="Cancel">
                            &nbsp;
                            &nbsp;
                            <input type="submit" class="btn btn-primary" value="Save Changes" v-on:click.prevent="onSubmit">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>

    import { mapGetters, mapMutations } from 'vuex'
    
    var Utils = require('./Utils')
            
    export default {
        data: function() {
            return {
                email: '',
                alias: '',
                userUUID: '',
                createdDate: '',
                firstName: '',
                lastName: '',
                major: '',
                address: '',
                affiliation: '',
                state: '',
                city: '',
                zipcode: '',
            }
        },
        computed: {
            ...mapGetters({
                isUserDetailsFetched: 'userModule/isUserDetailsFetched',
                getUserDetails: 'userModule/getUserDetails',
            })
        },
        created: function() {//alert('profile is being created!');           
            
            var loggedIn = !Utils.isEmpty(Utils.readCookie('loggedIn'));
            if(loggedIn === false){
                document.location.href = '/login';
            }
            else{
                var details = this.getUserDetails
                if(Utils.isEmpty(details)){
                    axios({
                        method: 'get',
                        dataType: 'json',
                        url: '/api/userProfile/'+localStorage.userUUID,
                    }).then( (response) => {
                        if(response.status === 200){
                            var data = response.data
                            this.resetUserProfile(data)
                        }
                        else{
                            alert("not 200");
                            return;
                        }     
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
                }
                else{
                    
                }
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
            onSubmit: function (message, event) {
                
                var qs = require('qs');
                axios.post('/api/userProfile/'+localStorage.userUUID, qs.stringify(this.$data))
                .then( (response) => {
                    if(response.status === 200){
                        alert('Your profile information has been updated!')
                        var data = response.data
                        this.resetUserProfile(data)
                    }
                            
                })
                .catch(function (error) {
                    alert('Your profile is NOT updated, sorry')
                    console.log(error);
                });
            },           
            resetUserProfile: function (data){
                var details = data.userDetails
                var user = data.user
                if(!Utils.isEmpty(details) && !this.isUserDetailsFetched){
                    this.setUserDetails(data.userDetails)
                    this.major = details.major
                    this.address = details.address
                    this.affiliation = details.affiliation
                    this.city = details.city
                    this.state = details.state
                    this.zipcode = details.zipcode
                }                            
                this.email = user.email
                this.alias = user.alias
                this.userUUID = user.userUUID
                this.createdDate = user.createdDate
                this.firstName = user.firstName
                this.lastName = user.lastName                            
                this.setDetailsFetched(true)
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