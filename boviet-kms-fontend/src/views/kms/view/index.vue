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
            baseUrl: process.env.VUE_APP_BASE_API,
            pdfUrl: "",
            id: this.$route.query.id,
        };
    },
    created() {
        this.getDocument();
    },
    methods: {
        getDocument() {
            previewAttFile(this.id).then(res => {
                const data = res.data
                data.forEach(item => {
                    console.log("item:", item.converted);
                });
                this.pdfUrl = this.baseUrl + res.data[0].converted
            });
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
