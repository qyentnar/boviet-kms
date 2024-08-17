<template>
<div class="app-container">
    <el-row>
        <el-col :span="24">
            <el-page-header @back="goBack">
                <template #content>
                    <span class="text-large font-600 mr-3"> KMS Viewer </span>
                </template>
            </el-page-header>
        </el-col>
    </el-row>
    <div class="main-content">
        <document-viewer :pdfUrl="pdfUrl" />
    </div>
</div>
</template>

    
<script>
import {
    previewAttFile
} from "@/api/kms/main"
import documentViewer from "../components/document-viewer.vue";

export default {
    name: "kms-view",
    components: {
        documentViewer
    },
    data() {
        return {
            pdfUrl: process.env.VUE_APP_BASE_API + this.$route.query.filePath,
        };
    },
    created() {
        this.getDocument();
    },
    methods: {
        getDocument() {
            console.log("pdfUrl:", this.pdfUrl);
        },
        goBack() {
            this.$router.back();
        }
    },
};
</script>

    
<style lang="scss">
.main-content {
    height: 90vh;
    margin-top: 10px
}
</style>
