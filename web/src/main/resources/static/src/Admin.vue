<template id="nav-template">
    <div class="container">
<!--        <div class="row">
            <img src="..." class="img-fluid" alt="Responsive image">
        </div>-->
        <div class="row" style="margin-top: 10%;">
            <div style="width: 100%">
                <div>
                    <h1>Administrator Management Tools</h1>
                    <br><br>
                </div>
                <div class="">
                    <div class="row">
                        <div class="col-xs-6 col-md-6 col-lg-6 ">
                            <div class="">                                    
                                <a href="/userlist" class="btn btn- btn-lg" role="button"><i class="fa fa-users fa-5x"></i><span class=""></span><br><br>User List</a>
                            </div>
                        </div>
                        <div class="col-xs-6 col-md-6 col-lg-6 ">
                            <div class="">                                    
                                <a href="#" class="btn btn- btn-lg" role="button"><i class="fa fa-user-md fa-5x"></i><span class=""></span><br><br>User Management</a>
                            </div>
                        </div>
                    </div>                    
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    
    var Utils = require('./Utils')
    
    var loggedIn = !Utils.isEmpty(Utils.readCookie('loggedIn'))
    if(loggedIn != true){
//        document.location.href = '/login'
    }
    
    var isAdminUser = Utils.isAdminUser();
    if(!isAdminUser){
//        document.location.href = '/errors/403'
    }
                
    export default {  
        computed: {

        },
    }
</script>

<style>
body { padding-top:20px; }
.panel-body .btn:not(.btn-block) { width:120px;margin-bottom:10px; }
.admin-heading {margin-top: 10%}
</style>
