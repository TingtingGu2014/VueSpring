<template>
    <div>
        <br><h4>Check out the newly added protocols!</h4><br>
        <Editor
            :editorName = "editorName"
            :heading = "heading"
        >            
        </Editor>
    </div>
</template>

<script>
    import Editor from './Editor.vue'
    
    var Utils = require('./Utils')
    var loggedIn = !Utils.isEmpty(Utils.readCookie('loggedIn'))
    
    
  export default {
    data: function(){
      return {
        name: 'Home',
        editorName: 'homeEditor',
        heading: 'Create a new protocol',
        loggedIn: loggedIn,
      }
    },
    components: {
        Editor
    },
    methods: {
      alertName: function(){
        alert(this.name);
      }
    }
  }
</script>

<style>
  p{
    color: #69C;
  }
</style>
