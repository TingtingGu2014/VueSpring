<template>
    <div class="">
        <vuetable ref="vuetable"
            api-url="https://vuetable.ratiw.net/api/users"
            :fields="fields"
            pagination-path=""
            @vuetable:pagination-data="onPaginationData"  
        ></vuetable>
        <div>
            <vuetable-pagination-info ref="paginationInfo"></vuetable-pagination-info>
            <vuetable-pagination ref="pagination"
                @vuetable-pagination:change-page="onChangePage"
            ></vuetable-pagination>
        </div>
    </div>
</template>

<script>
    import Vuetable from './table/Vuetable.vue'
    import VuetablePagination from './table/VuetablePagination.vue'
    import VuetablePaginationInfo from './table/VuetablePaginationInfo.vue'

export default {
    components: {
        Vuetable,
        VuetablePagination,
        VuetablePaginationInfo,
    },
    data () {
        return {
            fields: [
                'name', 'email',
                {
                  name: 'birthdate',
                  titleClass: 'center aligned',
                  dataClass: 'center aligned'
                },
                {
                  name: 'nickname',
                  callback: 'allcap',
                },
                {
                  name: 'gender',
                  titleClass: 'center aligned',
                  dataClass: 'center aligned'
                },
                {
                  name: 'salary',
                  titleClass: 'center aligned',
                  dataClass: 'right aligned'
                }
            ]
        }
    },
    methods: {
        allcap (value) {
            return value.toUpperCase()
        },
        onPaginationData (paginationData) {
            this.$refs.pagination.setPaginationData(paginationData)
        },
        onChangePage (page) {
            this.$refs.vuetable.changePage(page)
        },
        onPaginationData (paginationData) {
            this.$refs.pagination.setPaginationData(paginationData)
            this.$refs.paginationInfo.setPaginationData(paginationData)   // <----
        },
    }
}
</script>
