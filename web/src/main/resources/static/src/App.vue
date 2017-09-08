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
    import Navbar from './Navbar.vue';
    
    export default {
	name: 'app',
        components: {Navbar},
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

table.table-component__table {
    position: relative;
    //display: block;
    margin: 10px auto;
    padding: 0;
    width: 100%;
    height: auto;
    border-collapse: collapse;
    text-align: center;
}

th.table-component__th {
    text-align: center;
}

.table-hover tbody tr:hover {
    background-color: #E0FFFF;
}

input.table-component__filter__field {
    width: 30%;
    margin-bottom: 5px;
}
</style>
