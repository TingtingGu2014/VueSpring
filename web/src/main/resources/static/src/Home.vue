<template>
    <div>
        <br><h4>Check out the newly added protocols!</h4><br>
    </div>
</template>

<script>
  export default {
    data: function(){
      return {
        name: 'Home'
      }
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
