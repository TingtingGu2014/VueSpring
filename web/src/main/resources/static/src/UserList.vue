<template id="sign-up-template">
    
    <div class="container"><br><br>
    <h3>List of Users</h3>
        <VueDataTable 
            :tableData = "tableData"
            :tableColumns = "tableColumns"
            :showFilter = "showFilter"
        ></VueDataTable>
    </div>
</template>

<script>
    
    import VueDataTable from './VueDataTable.vue'
    
    var Utils = require('./Utils')
    
    var loggedIn = !Utils.isEmpty(Utils.readCookie('loggedIn'))
    if(loggedIn != true){
        document.location.href = '/login'
    }
    
    var isAdminUser = Utils.isAdminUser();
    if(!isAdminUser){
        document.location.href = '/errors/403'
    }
    
    export default {
        data: function() {
            return {
                tableData: [],
                tableColumns: [
                    {show: 'firstName', label: 'First Name', dataType: ''},
                    {show: 'lastName', label: 'Last Name', dataType: ''},
                    {show: 'email', label: 'Email', dataType: ''},
                    {show: 'userUUID', label: 'UUID', dataType: ''},
                    {show: 'createdDate', label: 'Registeration Date', dataType: 'date:DD/MM/YYYY'},
                    {show: 'active', label: 'Active', dataType: 'boolean'},
                ],
                showFilter: true,
            }
        },
        beforeMount: function(){
            axios({
                method: 'get',
                url: '/api/admin/users',
                dataType: 'json',
                headers: {'X-Requested-With': 'XMLHttpRequest'},
            })
            .then( (response) => {
                var status = response.status;
                if(status == 200 || status == "200"){
                    console.log(response.data)
                    this.tableData = response.data
                }
                else{
                    alert("not 200");
                    this.tableData = response.data;
                }                                   
            })
            .catch( (error) => {
                console.log(error);
            });  
        },
        components: {
            VueDataTable,
        },
    }
</script>


</style>