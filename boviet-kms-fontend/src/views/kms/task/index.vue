<template>
<div class="app-container">
    <el-tabs v-model="activeTab" class="custom-tabs" @tab-click="handleTabClick">
        <el-tab-pane label="等我审的" name="myApprove">
            <el-row class="mb8">
                <el-col :span="12" align="left">
                    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleMoreSearch">More</el-button>
                    <el-button type="default" icon="el-icon-refresh" size="mini" @click="handleRefresh">Refresh</el-button>
                </el-col>
                <el-col :span="12">
                    <el-input v-model="queryTask.title" placeholder="请输入档案名称" class="input-with-select" clearable>
                        <template slot="prepend">档案名称: </template>
                        <el-button slot="append" icon="el-icon-search" @click="getKmsTask"></el-button>
                    </el-input>
                </el-col>
            </el-row>
            <search-toolbar v-show="showSearch" @change="handleChanged"/>
            <el-row>
                <el-col :xl="8" :lg="8" :md="12" :sm="24" :xs="24" v-for="task in taskList" :key="task.id">
                    <el-card class="box-card" shadow="always">
                        <div slot="header" class="clearfix">
                            <el-tag effect="light" type="warning">
                                #{{task.id}}
                            </el-tag>
                            <el-button class="operator" type="primary" size="mini" @click="openTask(task.id)">提交</el-button>
                        </div>
                        <div class="card-body">
                            <el-descriptions :column="1" border size="mini" class="custom-descriptions">
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
        </el-tab-pane>
        <el-tab-pane label="我已审的" name="approved">
            <el-table ref="dataTable" v-loading="loading" :data="historyList" :default-sort = "{prop: 'createTime'}">
                <el-table-column label="主键ID" type="index" width="60" align="center"/>
                <el-table-column label="档案名称" align="center" prop="title" />
                <el-table-column label="档案编号" align="center" prop="attCode" />
                <el-table-column label="归档人" align="center" prop="archiver" />
                <el-table-column label="审核时间" align="center" prop="endTime" />
                <el-table-column label="Comments" align="center" prop="comments" />
            </el-table>
            <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getHistory" />
        </el-tab-pane>
    </el-tabs>
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
import { listKmsTasks } from "@/api/kms/main"
import FormApprove from "@/views/activiti/components/form-approve"
import { mapGetters } from "vuex"
import { getMain, previewAttFile, listVersion, getHistoryApprove } from "@/api/kms/main"
import { getCatalog } from "@/api/kms/catalog"
import { getTaskList, currentProcess, getTemplate } from "@/api/activiti/template"
import SearchToolbar from "../components/search/search-toolbar";

export default {
    name: "kms-task",
    components: {
        FormApprove,
        SearchToolbar
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
            isSubmit: true,
            activeTab: "myApprove",
            queryParams: {
                pageNum: 1,
                pageSize: 10,
            },
            queryTask: {
                pageNum: 1,
                pageSize: 10,
                catalogId: null,
                attCode: '',
                searchTimes: null,
            },
            historyList: [],
            total: 0,
            showSearch: false,
        }
    },
    created() {
        this.getKmsTask();
    },
    computed: {
        ...mapGetters(['name'])
    },
    mounted() {
    },
    methods: {
        resetParams(){
            this.queryTask = {
                pageNum: 1,
                pageSize: 10,
                catalogId: null,
                attCode: '',
                searchTimes: null,
            }
        },
        initView(id) {
            this.loading = true;
            getMain(id).then(response => {
                this.kmsData = response.data;
                this.processInstanceId = this.kmsData.processInstanceId;
                
                this.processInstance.processInstanceId = this.kmsData.processInstanceId;
                getTaskList(this.processInstanceId, this.name).then(response => {
                    const taskList = response.data;
                    this.activitData = taskList
                })
                currentProcess(this.processInstanceId).then(response => {
                    const historyData = response.data;
                    this.historyData = historyData
                    this.processData.nodeData = historyData
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
            listKmsTasks(this.queryTask).then(res => {
                this.taskList = res.rows
                this.total = res.total
                this.loading = false
            })
        },
        openTask(id) {
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
        },
        handleTabClick(tab, event){
            if(tab.index == "1"){
                this.getHistory();
            }
        },
        getHistory(){
            this.loading = true;
            getHistoryApprove().then(response =>{
                this.historyList = response.rows
                this.total = response.total
                this.loading = false
            })
        },
        handleMoreSearch(){
            if(this.showSearch){
                this.showSearch = false
            }else {
                this.showSearch = true
            }
        },
        handleRefresh(){
            this.resetParams();
            this.getKmsTask();
        },
        handleChanged(data){
            this.queryTask = data
            this.getKmsTask();
        }
}
}
</script>

<style lang="scss" scoped>
.box-card {
    margin: 10px 30px 20px 30px;
    //background-color: #fafafa;
}

.operator {
    float: right;
}

.el-descriptions-row th {
    &:first-of-type {
        width: 30%;
    }
}

.search-toolbar {
    display: flex;
    justify-content: start;
    align-items: center;
    margin-bottom: 10px;
    .toolbar-label {
        margin-right: 30px;
        width: 120px;
        height: 30px;
    }
}

.dropdown-list{
    margin-right: 30px;
}

.toolbar-label {
    display: inline-block;
    line-height: 1;
    white-space: nowrap;
    background: #FFF;
    border: 1px solid #DCDFE6;
    color: #606266;
    text-align: center;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    outline: 0;
    margin: 0;
    -webkit-transition: .1s;
    transition: .1s;
    font-weight: 500;
    padding: 9px 15px;
    font-size: 12px;
    border-radius: 3px;
}

</style>
<style lang="scss">
.custom-descriptions {
    .el-descriptions__body {
        .el-descriptions__table {
            .el-descriptions-row {
                .el-descriptions-item__cell {
                    color: black;
                }
            }
        }
    }
}
</style>
