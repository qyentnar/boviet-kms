<template>
    <vue-pdf-app :pdf="pdfUrl" :theme="theme" />
</template>
    
    <script>
    import VuePdfApp from "vue-pdf-app"
    import "vue-pdf-app/dist/icons/main.css";
    
    export default {
        name: "document-viewer",
        components: {
            VuePdfApp
        },
        props: {
            pdfUrl: String,
            theme: {
                type: String,
                default: "dark"
            }
        },
        data() {
            return {

            };
        },
        created() {
        },
        methods: {
        },
    };
    </script>
    
    <style lang="scss">
    
    </style>
    