<template>
<div class="app-container">
    <el-tabs v-model="activeName">
        <el-tab-pane label="知识详情" name="tab1">
            <div class="kms-view">
                <div class="kms-header">
                    <div class="kms-header-title">{{kmsData.title}}</div>
                    <!--<div class="kms-header-summary" v-show="kmsData.summary!=null&&kmsData.summary!=''">{{kmsData.summary}}</div>-->
                    <div class="kms-header-create-info">
                        <div class="kms-header-author">作者：{{kmsData.createBy}}</div>
                        <div class="kms-header-time">创建时间：{{kmsData.createTime}}</div>
                    </div>
                </div>
                <div class="kms-content" v-show="kmsData.mainContent!=null&&kmsData.mainContent!=''">
                    <div v-html="kmsData.mainContent"></div>
                </div>
                <div class="list-panel" v-show="previewFile.length>0">
                    <!--<div class="list-title"><i class="iconfont ic-xiangguanziliao-copy"></i><span class="">附件列表</span></div>-->
                    <div class="list-title">附件列表</div>
                    <ul class="preview-ul">
                        <li v-for="item in previewFile" @click="openFile(item.path,item.state)" :key="item.path">
                            <i class="iconfont" :class="item.type | fileIcon"></i>{{item.name}}.{{item.type}}
                        </li>
                    </ul>
                </div>

                <!--版本列表-->
                <div class="list-panel" v-show="versionList.length>0">
                    <div class="list-title">版本列表</div>
                    <ul class="version-ul">
                        <li v-for="item in versionList" @click="wait" :key="item.id">
                            <div class="label">{{item.title}}</div> <span></span>
                            <div class="time"> 发布时间：{{item.publishDate}}</div>
                        </li>
                    </ul>
                </div>

                <!--审批-->
                <activiti-approve :isAssignee="isAssignee" :list="activitData" :historyData="historyData" :rejectApprovalNode="rejectApprovalNode" @submit="submitApprove" />
            </div>
        </el-tab-pane>
        <el-tab-pane label="流程图" name="tab2">
            <!-- <process-viewer :processData="processData" /> -->
        </el-tab-pane>
    </el-tabs>
</div>
</template>

<script>
import { getMain, previewAttFile, listVersion } from "@/api/kms/main";
import { getTaskList, getHistory, getNodes } from "@/api/activiti/template"
import { fileIconStyle } from '@/utils/fileType'
import ActivitiApprove from "@/views/activiti/components/activiti-approve";
import { completeTask } from '@/api/activiti/template';
import { mapGetters } from "vuex";
import ProcessViewer from '../../activiti/components/process-viewer';

export default {
    name: "kms-detail",
    components: {
        ActivitiApprove,
        ProcessViewer
    },
    data() {
        return {
            id: this.$route.query.id,
            kmsData: {},
            previewFile: [],
            versionList: [],
            rejectApprovalNode: [],
            activitData: [],
            processInstanceId: '',
            historyData: [],
            isAssignee: true,
            activeName: 'tab1'
        }
    },
    computed: {
        ...mapGetters(['name'])
    },
    filters: {
        fileIcon(v) {
            return fileIconStyle(v)
        }
    },
    created() {
        this.initView();
    },
    methods: {
        /** 查询知识列表 */
        initView() {
            this.loading = true;
            getMain(this.id).then(response => {
                this.kmsData = response.data;
                this.loading = false;
                this.processInstanceId = this.kmsData.processInstanceId;
                getTaskList(this.processInstanceId, this.name).then(response => {
                    const taskList = response.data;
                    this.activitData = taskList
                    console.log('taskList=========:', taskList)
                })
                getHistory(this.processInstanceId).then(response => {
                    const historyData = response.data;
                    this.historyData = historyData
                    console.log('historyData=========:', historyData)
                })
                getNodes(this.processInstanceId).then(response => {
                    const nodeData = response.data;
                    console.log('getNodes=========:', nodeData)
                })
            })
            previewAttFile(this.id).then(response => {
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
            listVersion(this.id).then(response => {
                const verList = response.data;
                console.log('listVersion=========,verList:', verList)
                this.versionList = verList
            })
            //console.log(fileIconStyle('.doc'))
        },
        //打开文件
        openFile(path, state) {
            //console.log('openFile=========,state:',state)
            if (state === 2) {
                let baseUrl = process.env.VUE_APP_BASE_API //"http://localhost:9090"  //window.origin
                window.open(baseUrl + path, '_blank')
            } else {
                this.$modal.msgWarning("该附件还未转换完成或转换失败！");
            }
        },
        wait() {
            window.alert('期待你的加入')
        },
        //查询历史审批节点详情
        listRejectApprovalNode() {

        },
        //流程审批提交
        submitApprove(comment, approvalNodeId) {
            const data = {
                processInstanceId: this.processInstanceId,
                assignee: this.name,
                comment: comment
            }
            completeTask(data).then(response => {
                if (response?.message) {
                    this.$modal.msgError(response.message)
                } else {
                    this.$modal.msgSuccess("审批成功");
                    this.isAssignee = false;
                    this.initView();
                    //关闭本页签
                    setTimeout(function () {
                        this.$tab.closePage();
                    }, 1500)

                }
            })
        }
    }
}
</script>

<style scoped>
/*@import url("http://at.alicdn.com/t/font_2602024_czfyrrtt7tk.css");*/
.iconfont {
    font-family: hufont !important;
    font-style: normal;
    margin-right: 5px;
    color: #00afff;

    speak: none;
    font-weight: 400;
    font-variant: normal;
    text-transform: none;
    line-height: 1;
    vertical-align: baseline;
    display: inline-block;
}

.ic-xiangguanziliao-copy {
    font-size: 13px;
}

.list-title {
    /*border-bottom: solid 1px #eee;
    padding: 0 1px 8px 1px;*/
    color: #00afff;
}

.kms-view {
    background: #FFFFFF;
    margin: 10px auto;
}

.kms-header {
    background: #FFFFFF;
    box-shadow: 0px 2px 18px 1px rgba(0, 0, 0, 0.08);
    border-radius: 4px 4px 4px 4px;
    margin: 15px auto;
    width: 90%;
}

.kms-header-title {
    text-align: center;
    padding: 30px 0px;
}

.kms-header-summary {
    text-align: center;
    color: #5d5d5d;
    padding: 5px 10px 30px;
}

.kms-header-create-info {
    color: #5d5d5d;
    font-size: 13px;
    padding: 5px 10px 30px;
    display: flex;
    justify-content: space-around;
    width: 30%;
    margin: 0 auto;
}

.kms-content {
    background: #FFFFFF;
    box-shadow: 0px 2px 18px 1px rgba(0, 0, 0, 0.08);
    border-radius: 4px 4px 4px 4px;
    margin: 15px auto;
    width: 90%;
    padding: 30px;
}

.list-panel {
    background: #FFFFFF;
    box-shadow: 0px 2px 18px 1px rgba(0, 0, 0, 0.08);
    border-radius: 4px 4px 4px 4px;
    margin: 15px auto;
    width: 90%;
    padding: 15px 20px 30px;
}

.preview-ul {
    list-style: none;
    width: 95%;
}

.preview-ul li {
    padding: 5px 10px;
    cursor: pointer;
    font-size: 15px;
}

.preview-ul li:hover {
    color: #409eff;
}

.version-ul {
    list-style: none;
    width: 95%;
}

.version-ul li {
    display: flex;
    height: 50px;
    align-items: center;
    border-bottom: solid 1px #eee;
    padding-left: 15px;
    cursor: pointer;
}

.version-ul li:hover {
    color: #409eff;
}

.version-ul li span {
    margin: 0 15px 0 35px;
    color: #ddd;
}

.label {}

.time {
    color: #bbb;
    font-size: 12px;
    padding-top: 5px;
}
</style>
