<template>
<div class="app-container">
    <el-row>
        <el-col :span="24"></el-col>
    </el-row>
    <el-row>
        <el-col :xl="8" :lg="8" :md="12" :sm="24" :xs="24" v-for="task in taskList" :key="task.id">
            <el-card class="box-card" shadow="hover">
                <div slot="header" class="clearfix">
                    <el-tag effect="light" type="warning">
                        #{{task.id}}
                    </el-tag>
                    <el-button class="operator" type="primary" size="mini" @click="handleSubmit(task.id)">提交</el-button>
                </div>
                <div class="card-body">
                    <el-descriptions :column="1" border size="mini">
                        <el-descriptions-item label="Title">
                            {{task.title}}
                        </el-descriptions-item>
                        <el-descriptions-item label="Summary">
                            {{task.summary}}
                        </el-descriptions-item>
                        <el-descriptions-item label="Version">
                            <el-tag effect="plain" type="warning" size="mini">
                                v{{task.version}}
                            </el-tag>
                        </el-descriptions-item>
                        <el-descriptions-item label="Author">
                            <el-tag effect="light" type="primary" v-for="author in task.extAuthor" :key="author" size="mini" style="letter-spacing: 1px;">
                                {{author}}
                            </el-tag>
                        </el-descriptions-item>
                        <el-descriptions-item label="Create Time">
                            {{task.createTime}}
                        </el-descriptions-item>
                    </el-descriptions>
                </div>
            </el-card>
        </el-col>
    </el-row>

    <el-dialog :title="title" :visible.sync="open" width="960px" append-to-body style="height: 80%;">
        <form-approve ref="approve" 
                    @task-completed="handleTaskCompleted"
                    @is-submit="handleSetSubmitState"
                    :list="activitData" 
                    :historyData="historyData" 
                    :rejectApprovalNode="rejectApprovalNode" 
                    :processInstance="processInstance"
                    :versionList="versionList"
                    :key="dialogKey"
                    :processData="processData"/>
        <div slot="footer">
            <el-button type="primary" @click="submitApprove" :disabled="isSubmit">提交</el-button>
            <el-button type="danger" @click="cancel">取 消</el-button>
        </div>
    </el-dialog>
</div>
</template>

<script>
import {
    listKmsTasks
} from "@/api/kms/main"
import FormApprove from "@/views/activiti/components/form-approve";
import { mapGetters } from "vuex";
import { getMain, previewAttFile, listVersion } from "@/api/kms/main";
import { getCatalog } from "@/api/kms/catalog";
import { findTask, findHistory, findAllNodes, processHistory, getTemplate } from "@/api/activiti/template"

export default {
    name: "kms-task",
    components: {
        FormApprove
    },
    data() {
        return {
            loading: true,
            taskList: [],
            total: 0,
            open: false,
            title: "",
            form: {
                handle: '通过',
                desc: '同意',
                nodeValue: '',
            },
            kmsData: {},
            previewFile: [],
            versionList: [],
            rejectApprovalNode: [],
            activitData: [],
            processInstance:{},
            historyData: [],
            processData: {},
            dialogKey:"",
            isSubmit: true
        }
    },
    created() {
        this.getKmsTask();
    },
    computed: {
        ...mapGetters(['name'])
    },
    mounted() {
        setTimeout(() => {
            const contents = document.getElementsByClassName("el-descriptions-item__content");
            contents.forEach(content => {
                content.style.border = 'none';
            });
            const labels = document.getElementsByClassName("el-descriptions-item__label");
            labels.forEach(label => {
                label.style.border = 'none';
            });
        },100);
    },
    methods: {
        initView(id) {
            this.loading = true;
            getMain(id).then(response => {
                this.kmsData = response.data;
                this.processInstanceId = this.kmsData.processInstanceId;
                
                this.processInstance.processInstanceId = this.kmsData.processInstanceId;
                findTask(this.processInstanceId, this.name).then(response => {
                    const findTask = response.data;
                    this.activitData = findTask
                })
                processHistory(this.processInstanceId).then(response => {
                    const findHistory = response.data;
                    this.historyData = findHistory
                    this.processData.nodeData = findHistory
                })

                getCatalog(this.kmsData.catalogId).then(response => {
                    const catalog = response.data;
                    getTemplate(catalog.templateId).then(response =>{
                        const template = response.data;
                        if(template.processDefXml == undefined || template.processDefXml == ""){
                            this.$message.error("没有找到模板");
                        }
                        this.processData.xmlData = template.processDefXml

                    })
                })
                this.loading = false;
            })
            previewAttFile(id).then(response => {
                const previewData = response.data;
                //console.log('previewAttFile=========,previewData:',previewData)
                this.previewFile = []
                for (let i in previewData) {
                    this.previewFile.push({
                        name: previewData[i].fileName,
                        path: previewData[i].converted,
                        state: previewData[i].state,
                        type: previewData[i].fileType
                    })
                }
            })
            listVersion(id).then(response => {
                const verList = response.data;
                this.versionList = verList
            })
            //console.log(fileIconStyle('.doc'))
        },
        getKmsTask() {
            this.loading = true;
            listKmsTasks().then(res => {
                this.taskList = res.rows
                this.total = res.total
                this.loading = false
            })
        },
        handleSubmit(id) {
            const date = new Date().getTime()
            this.initView(id);
            this.dialogKey = id + date; 
            this.open = true;
            this.title = "提交任务";
        },
        cancel() {
            this.open = false;
        },
        submitApprove() {
            this.$refs.approve.onSubmit();
            this.open = false;
        },
        handleTaskCompleted(){
            this.getKmsTask();
        },
        handleSetSubmitState(isSubmit){
            this.isSubmit = isSubmit
        }
    }
}
</script>

<style lang="scss" scoped>
.app-container {
    background-color: #f0f2f5;
    height: 85vh;
}

.box-card {
    margin: 10px 30px 20px 30px;
    //background-color: #fafafa;
}

.operator {
    float: right;
}

.el-descriptions-row th {
    &:first-of-type {
        width: 20%;
    }
}
</style>
