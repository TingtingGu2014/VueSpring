<template>
    <div>
        <div class="container" style="margin-top: 5%">
            <!-- Jumbotron -->
            <div class="jumbotron">
            <h1><div ref="errorDiv"><span class='fa fa-frown-o red'>&nbsp;&nbsp;</span></div></h1>
              <p class="lead" v-html="errorMessage"><em><span id="display-domain"></span></em></p>
                <div>                    
                    <router-link :to="{ name: 'home'}" class="btn btn-primary">Take Me To The Homepage</router-link>
                </div>
              </p>
            </div>
          </div>          
      </div>
</template>

<script>
    var Utils = require('./Utils')
    var errorDetailMessage = sessionStorage.errorMessage
    errorDetailMessage = Utils.isEmpty(errorDetailMessage) ? '' : errorDetailMessage
    
    export default {
        data: function(){
            var error = Utils.getErrorPage(this.$route.params.errorCode, errorDetailMessage)
            sessionStorage.errorMessage = ''
            return {
                errorTitle: error.title,
                errorMessage: error.message,
            }
        },
        mounted: function() {
            var errorDiv = this.$refs.errorDiv
            var i = document.createElement("span")
            i.innerHTML = this.errorTitle
            errorDiv.appendChild(i)
        }
    }
</script>

<style>
  p{
    color: #69C;
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

</style>
