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
                tableData: [
                    { firstName: 'John', birthday: '04/10/1940', songs: 72, lastName: 'White', address: '234 North Blue task AVE' },
                    { firstName: 'Paul', birthday: '18/06/1942', songs: 70, lastName: 'White', address: '234 North Blue task AVE' },
                    { firstName: 'George', birthday: '25/02/1943', songs: 22, lastName: 'White', address: '234 North Blue task AVE' },
                    { firstName: 'Ringo', birthday: '07/07/1940', songs: 2, lastName: 'White', address: '234 North Blue task AVE' },
                ],
                tableColumns: [
                    {show: 'firstName', label: 'First Name', dataType: ''},
                    {show: 'lastName', label: 'Last Name', dataType: ''},
                    {show: 'songs', label: 'Songs', dataType: 'numeric'},
                    {show: 'birthday', label: 'Birthday', dataType: ''},
                    {show: 'address', label: 'Address', },
                ],
                showFilter: true,
            }
        },
        computed: {
            
        },
        components: {
            VueDataTable,
        },
        watch: {

        },
        methods: {
         
        }
    }
</script>


</style>