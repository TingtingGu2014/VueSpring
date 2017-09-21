<template id="nav-template">
    <nav class="navbar navbar-toggleable-sm navbar-light bg-faded">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="#">XProtocol!</a>

    <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
      <ul class="navbar-nav mr-auto mt-2 mt-md-0">
        <li class="nav-item active my-auto">          
            <router-link :to="{ name: 'home'}"><span class="fa fa-home"></span> &nbsp;Home</router-link>
        </li>
        <li class="nav-item">
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="text" placeholder="Search Protocols">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><i class="fa fa-search"></i></button>
                <!--<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>-->
            </form>
        </li>
        <li class="nav-item form-inline" v-if="isAdminUser">                       
            <button class="btn btn-outline-success my-2 my-sm-0" type="button" onclick="location.href = '/admin';"><i class="fa fa-sun-o"></i>&nbsp;Admin</button>
        </li>
      </ul>
        
      <login-view></login-view>
    </div>
  </nav>
</template>

<script>
    
    import LoginView from './LoginView.vue';
    
    var Utils = require('./Utils')
                
    export default {
        components: {LoginView},    
        computed: {
            isAdminUser: function () {
                return Utils.isAdminUser();
            },
        },
    }
</script>

<style>
.navbar {min-height:50px !important}
.navbar-nav > table > tr > td {vertical-align: middle !important}
div {vertical-align: middle !important}
</style>
