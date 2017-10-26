<template id="editor-template">
    <div class="container">
        <form method="post">
            <h3 style="font-weight: bold">{{heading}}</h3>
            <textarea v-bind:id="editorName"></textarea>
            <br>
            <button type="button" class="btn btn-outline-success btn-lg">
                <span style="font-weight: bold">Save</span>
            </button>
        </form>
    </div>
</template>     

<script>
            
    export default {
        
        props: {
            editorName: String,
            heading: String,
        },
        data: function () {
            return {

            }
        },    
        mounted: function() {
            tinymce.init({
                selector: '#'+this.editorName,
                plugins: [
                    'advlist autolink link image imagetools lists charmap print preview hr anchor pagebreak spellchecker',
                    'searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking',
                    'save table contextmenu directionality emoticons template paste textcolor'
                ],
                toolbar: 'insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright ' +
                         ' alignjustify | bullist numlist outdent indent | link image | print preview media fullpage ' +
                         ' | forecolor backcolor emoticons | code ',
                max_height: 500,
                max_width: 500,
                min_height: 300,
                min_width: 400 
            });
        },
        computed: {
            
        },
        methods: {
            
        }
    }

</script>

<style scoped>

</style>