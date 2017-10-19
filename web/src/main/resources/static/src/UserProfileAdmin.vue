<template id="sign-up-template">
    <div class="col-md-8 offset-md-2">
        <span class="anchor" id="formUserEdit"></span>
        <br>

        <!-- form user info -->
        <div class="card card-outline-secondary">
            
            <div class="card-header">
                <h6 class="mb-0">Manage User Information</h6>
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
                    <div class="form-group row justify-content-md-center">  
                        <input type="hidden" name="roles" v-model="roles"/>
                        <label class="col col-lg-3 col-form-label form-control-label primary">User Authority Roles</label>
                        <div class="col  col-lg-9" role="group" aria-label="...">
                            <label  class="col btn label-user-role col-lg-3 float-left" v-for="(role, index) in roles.split(',')" v-if="role != ''">
                                {{index+1}}.&nbsp;{{role}}
                                <a v-bind:id="role" href="#" class="trash-can" v-on:click.prevent="removeRole">
                                    <i class="fa fa-trash" aria-hidden="true" v-if="role != 'regular' "></i>                                    
                                </a>&nbsp;&nbsp;                                
                            </label>                            
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label primary"></label>
                        <label  class="btn label-user-role" >Restore roles
                            <a v-bind:id="role" href="#" class="trash-can" v-on:click.prevent="restoreRoles">
                                <i class="fa fa-undo" aria-hidden="true"></i>                                    
                            </a>&nbsp;&nbsp;
                        </label>     
                        <label  class="btn label-user-role" >Add other roles
                            <a v-bind:id="role" href="#" class="trash-can" v-on:click.prevent="displayRoles">
                                <i class="fa fa-user-plus" aria-hidden="true"></i>                                   
                            </a>&nbsp;&nbsp;
                        </label>
                    </div>
                    <div class="form-group row other-roles-row" style="display:none" v-if="otherRoles != ''">
                        <label class="col col-lg-3 col-form-label form-control-label primary">Roles to be added</label>
                        <div class="col  col-lg-9" role="group" aria-label="...">
                            <label  class="col btn label-user-role col-lg-3 float-left" v-for="(role, index) in otherRoles.split(',')" v-if="role != ''">
                                {{index+1}}.&nbsp;{{role}}
                                <a v-bind:id="role" href="#" class="trash-can" v-on:click.prevent="removeOtherRole">
                                    <i class="fa fa-plus-square" aria-hidden="true" v-if="role != 'regular' "></i>                                    
                                </a>&nbsp;&nbsp;                                
                            </label>                            
                        </div>
                    </div>
                    <div class="form-group row other-roles-row" style="display:none" v-else>
                        <label class="col col-lg-3 col-form-label form-control-label primary">No roles to be added</label>
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
                            <router-link :to="{ name: 'home'}" class="btn btn-secondary">Cancel</router-link>
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
                userUUID: this.$route.params.userUUID,
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
                oldRoles: '',
                allRoles: '',
            }
        },
        computed: {
            otherRoles: function(){
                var allRoles = this.allRoles
                if(!Utils.isEmpty(allRoles)){
                    var roles = this.roles
                    var diff = Utils.getArraydifferences(allRoles.split(','), roles.split(','))
                    return diff.toString()
                }
                else{
                    return '';
                }
            },            
//            ...mapGetters({                
//                isUserDetailsFetched: 'userModule/isUserDetailsFetched',                
//                getUserDetails: 'userModule/getUserDetails',
//            })
        },
        created: function() {
            var loggedIn = !Utils.isEmpty(Utils.readCookie('loggedIn'));
            if(loggedIn === false){
                document.location.href = '/login';
            }
            else{
                var userUUID = this.userUUID
                var detailsFetched = this.isUserDetailsFetched
                if(!detailsFetched == true){
                    axios({
                        method: 'get',
                        dataType: 'json',
                        url: '/api/admin/userProfile/'+userUUID,
                    }).then( (response) => {
                        if(response.status === 200){
                            var data = response.data
                            this.resetUserProfileAdmin(data)
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
                    // throw an exception here!
                }
            }
        },
        methods: {
            onSubmit: function (event) {
                var qs = require('qs');
                var userUUID = this.userUUID

                axios.post('/api/admin/userProfile/'+userUUID, qs.stringify(this.$data))
                .then( (response) => {
                    if(response.status === 200){
                        alert('Your profile information has been updated!')
                        var data = response.data
                        this.resetUserProfileAdmin(data)
                    }
                            
                })
                .catch(function (error) {
                    alert('Your profile is NOT updated, sorry')
                    console.log(error);
                });
            },           
            resetUserProfileAdmin: function (data){
                var details = data.userDetails
                var user = data.user
                if(!Utils.isEmpty(user)){
                    this.email = user.email
                    this.alias = user.alias
                    this.userUUID = user.userUUID
                    this.createdDate = user.createdDate
                    this.firstName = user.firstName
                    this.lastName = user.lastName  
                    this.roles = user.roles
                    this.oldRoles = user.roles
                }
                else{
                    //throw new exception here
                }
                if(!Utils.isEmpty(details)){                    
                    this.major = details.major
                    this.address = details.address
                    this.affiliation = details.affiliation
                    this.city = details.city
                    this.state = details.state
                    this.zipcode = details.zipcode
                }                  
            },
            removeRole : function (event) {
                if (event){
                    event.preventDefault()
                }
                var target = $(event.currentTarget)
                var role = target.attr('id')
                         
                var roles = this.roles
                var rolesArr = roles.split(',')
                var index = rolesArr.indexOf(role)
 
                if (index > -1) {
                   rolesArr.splice(index, 1);
                }
                
                this.roles = rolesArr.toString()
            },
            removeOtherRole: function (event) {
                if (event){
                    event.preventDefault()
                }
                var target = $(event.currentTarget)
                var role = target.attr('id')
                
                var currentRoles = this.roles
                currentRoles += ',' + role
                this.roles = currentRoles
            },
            restoreRoles: function (event) {
                this.roles = this.oldRoles
            },
            displayRoles: function (event) {
                Utils.getAllUserRoles()
                .then((data) => {
                    alert(data)
                    this.allRoles = data;
                })
                .catch((err) => {
                    alert("oops, something happened")
                });
                $(".other-roles-row").show()
            },
            ...mapMutations({                                
//                setUserDetails: 'userModule/setUserDetails',
//                setDetailsFetched: 'userModule/setDetailsFetched',                
            }),
        }
    }
</script>

<style scoped>

</style>