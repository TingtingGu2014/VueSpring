<template>
    <div>
        <div class="container">
            <!-- Jumbotron -->
            <div class="jumbotron">
              <h1 id="hh1"><i class="fa fa-frown-o red"></i> </h1>
              <p class="lead">{{errorMessage}}<em><span id="display-domain"></span></em>.</p>
              <p><a class="btn btn-default btn-lg"><span class="green">Take Me To The Homepage</span></a>
              </p>
            </div>
          </div>          
      </div>
</template>

<script>
    var Utils = require('./Utils')
    
    export default {
        data: function(){
            var error = Utils.getErrorPage(this.$route.params.errorCode)
            return {
                errorTitle: error.title,
                errorMessage: error.message,
            }
        },
        created: function() {
            alert('ok');
            var i = document.createElement("p");
            i.innerHTML = "okok";
            document.getElementById("hh1").appendChild(i);
        }
    }
</script>

<style>
  p{
    color: #69C;
  }
</style>
