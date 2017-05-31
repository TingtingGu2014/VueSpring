<template>
    <div>
  <p>Hello {{ name }}</p>
  <button v-on:click="alertName">alert!</button>
    </div>
</template>

<script>
  export default {
    data: function(){
      return {
        name: 'Randy'
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
