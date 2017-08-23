<template>
<div id="app">
    <Navbar></Navbar>
    <router-view
      class="view"
      keep-alive
      transition
      transition-mode="out-in">
    </router-view>
    
<!--    <div class="ui container">
      <my-vuetable></my-vuetable>
    </div>-->
    
  </div>
</template>

<script>
    import User from './User.vue';
    import Navbar from './Navbar.vue';
    import Profile from './UserProfile.vue';
//    import Datatable from './Datatable.vue';
    import MyVuetable from './Vuetable.vue'
    
    export default {
	name: 'app',
//        router,
        components: {User,Navbar,MyVuetable},
        data() {
            return {
                
            }
        }
    }
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

#ok {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}

</style>
