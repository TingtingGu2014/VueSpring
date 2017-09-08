<template id="sign-up-template">
    
    <div class="container">
        <table-component class="table table-sm table-striped table-hover table-responsive"
            :data="tableData"
            :show-filter="showFilter"
            filter-input-class="form-control float-right"
            sort-by="songs"
            sort-order="asc"
            >
            <table-column v-for="column in tableColumns" 
                :key="column.show"
                :show="column.show" 
                :label="column.label" 
                :data-type=" null == column.dataType ? '': column.dataType"
            >

            </table-column>
<!--            <table-column show="firstName" label="First name"></table-column>
            <table-column show="lastName" label="Last name"></table-column>
            <table-column show="songs" label="Songs" data-type="numeric"></table-column>
            <table-column show="birthday" label="Birthday" :filterable="false" data-type="date:DD/MM/YYYY"></table-column>            
            <table-column show="address" label="Address"></table-column>-->
        </table-component>
    </div>
</template>

<script>
    
    var Utils = require('./Utils')

    export default {
        props: {
            tableData: Array,
            tableColumns: Array,
            showFilter: Boolean,
        },
        data: function() {
            return {
                
            }
        },
        computed: {
         
        },

        watch: {
            
        },
        methods: {
         
        }
    }
</script>


</style>