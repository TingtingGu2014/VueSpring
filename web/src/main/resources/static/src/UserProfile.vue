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
                        <label class="col-lg-3 col-form-label form-control-label primary">User Authority Roles</label>
                        <div class="btn-group col-lg-9" role="group" aria-label="...">
                            <button type="button" class="btn btn-info" v-for="(role, index) in roles.split(',')">{{role}}</button>
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
    
    var loggedIn = !Utils.isEmpty(Utils.readCookie('loggedIn'))
    if(loggedIn != true){
        document.location.href = '/login'
    }
    
    var currentUrl = window.location.href
    if(currentUrl.indexOf('admin/') >=0) {
        var isAdminUser = Utils.isAdminUser();
        if(!isAdminUser){
            document.location.href = '/errors/403'
        }
    }
            
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
                roles: '',
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
                var userUUID = ''
                try {
                    var userInfo = JSON.parse(localStorage.userInfo)
                    userUUID = userInfo.userUUID
                }
                catch(err) {
                    console.log(err.message)
                }
                var detailsFetched = this.isUserDetailsFetched
                if(!detailsFetched == true){
                    axios({
                        method: 'get',
                        dataType: 'json',
                        url: '/api/userProfile/'+userUUID,
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
                    if(detailsFetched == true){
                        try{
                            var user = JSON.parse(localStorage.userInfo)
                        }
                        catch(err) {
                            console.log(err.message)
                        }
                        this.email = user.email
                        this.alias = user.alias
                        this.userUUID = user.userUUID
                        this.createdDate = user.createdDate
                        this.firstName = user.firstName
                        this.lastName = user.lastName    
                        this.roles = user.roles
                    }
                    var details = this.getUserDetails
                    this.major = details.major
                    this.address = details.address
                    this.affiliation = details.affiliation
                    this.city = details.city
                    this.state = details.state
                    this.zipcode = details.zipcode   
                }
            }
        },
        watch: {
        },
        methods: {
            onSubmit: function (message, event) {
                
                var qs = require('qs');
                var userInfo = null
                try{
                    userInfo = JSON.parse(localStorage.userInfo)
                }
                catch(err) {
                    console.log(err.message)
                }
                axios.post('/api/userProfile/'+userInfo.userUUID, qs.stringify(this.$data))
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
                this.setUserDetails(details)
                if(!Utils.isEmpty(details)){                    
                    this.major = details.major
                    this.address = details.address
                    this.affiliation = details.affiliation
                    this.city = details.city
                    this.state = details.state
                    this.zipcode = details.zipcode
                    this.setDetailsFetched(true)
                }  
                if(Utils.isEmpty(user)) {
                    try{
                        user = JSON.parse(localStorage.userInfo)
                    }
                    catch(err) {
                        console.log(err.message)
                    }
                }
                if(!Utils.isEmpty(user)){
                    this.email = user.email
                    this.alias = user.alias
                    this.userUUID = user.userUUID
                    this.createdDate = user.createdDate
                    this.firstName = user.firstName
                    this.lastName = user.lastName  
                    this.roles = user.roles
                    localStorage.userInfo = JSON.stringify(user)
                }
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