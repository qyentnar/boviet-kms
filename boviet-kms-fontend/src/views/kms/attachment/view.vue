<template>
    <div class="app-container">
        <breadcrumb-header
            :breadcrumbList="breadcrumbList"
            background="#ffffff"
            exist-back
            class="mb20">
        </breadcrumb-header>
        <el-card class="card-box" ref="card-info" v-loading="loading">
                <div slot="header" class="card-header">
                    <span>档案信息</span>
                </div>
                <el-row type="flex" justify="center">
                    <el-col :span="18">
                        <el-descriptions :column="2" border >
                            <el-descriptions-item label="档案名称" :span="2">
                                <span>{{form.title}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="档案类型">
                                <span>{{attTypeName}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="所属分类">
                                <span>{{catalogTitle}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="所属区域">
                                <span>{{form.area}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="归档人">
                                <span>{{form.archiver}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="档案总称">
                                <span>{{form.generalName}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="档案材料内容">
                                <span>{{form.summary}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="档案编号">
                                <span>{{form.attCode}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="历史档案编号">
                                <span>{{form.attCodeH}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="归档日期">
                                <span>{{form.createTime}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="资料形成日期">
                                <span>{{form.attCreateTime}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="保管期限">
                                <span>{{storageTime}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="档案有效期">
                                <span>{{form.attExpirationTime}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="箱号">
                                <span>{{form.boxNo}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="盒号">
                                <span>{{form.boxesNo}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="案卷号">
                                <span>{{form.attNumber}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="目录号">
                                <span>{{form.catalogNumber}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="卷内序号">
                                <span>{{form.attSerialNumber}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="密级程度">
                                <span>{{attClassificationName}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="件数/本">
                                <span>{{form.attCount}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="页数/页码">
                                <span>{{form.pageNumber}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="保管单位">
                                <span>{{form.custodyUnit}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="外部相关单位">
                                <span>{{form.externalUnit}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item label="移交档案部门">
                                <span>{{form.filingDept}}</span>
                            </el-descriptions-item>
                            <el-descriptions-item/>
                            <el-descriptions-item label="备注" :span="2">
                                <span>{{form.marks}}</span>
                            </el-descriptions-item>
                        </el-descriptions>
                    </el-col>
                </el-row>
            </el-card>
            <el-card class="card-box" ref="card-upload">
                <div slot="header">
                    <span>上转文件</span>
                </div>
                <el-row>
                    <el-col class="mb8" :span="24" align="left" v-for="(item,index) in fileConverted" :key="index" >
                        <el-tag @click="handleView(item)" class="tag-filename" effect="plain">
                            <el-row style="width: 100%;">
                                <el-col :span="20">
                                    <span>{{ item.fileName }}.{{item.fileType}}</span>
                                </el-col>
                                <el-col :span="4" align="right">
                                    <el-button icon="el-icon-search" circle size="mini" @click="handleView(item)"></el-button>
                                    <el-button icon="el-icon-download" circle size="mini" @click="handleDownload"></el-button>
                                </el-col>
                            </el-row>
                        </el-tag>
                    </el-col>
                </el-row>
            </el-card>
            <el-card class="card-box" ref="card-process">
                <div slot="header">
                    <span>流程处理</span>
                </div>
                <el-tabs v-model="tabPaneProcessActive">
                    <el-tab-pane name="process" label="流程处理" class="tab-pane-view">
                        <div class="block">
                            <el-timeline v-for="history in historyData" :key="history.activityName" :reverse="true">
                                <el-timeline-item :timestamp="history.endTime" placement="top" v-if="history.completed">
                                    <el-card style="width: 30%">
                                        <el-tag type="success">
                                            节点名称：{{history.activityName}}
                                        </el-tag>
                                        <div class="process-history-content">
                                            <el-descriptions border size="mini" :column="1">
                                                <el-descriptions-item label="操作者" style="width: 80px;">
                                                    {{history.assignee}}
                                                </el-descriptions-item>
                                                <el-descriptions-item label="操作">
                                                    {{history.operator}}
                                                </el-descriptions-item>
                                                <el-descriptions-item label="处理意见">
                                                    {{history.comments}}
                                                </el-descriptions-item>
                                            </el-descriptions>
                                        </div>
                                    </el-card>
                                </el-timeline-item>
                            </el-timeline>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane name="process_status" label="流程状态" class="tab-pane-view">
                        <el-row>
                          <el-col :md="4" :sm="6" :xs="24" class="pr20 pl20">
                            <div class="card-panel">
                                <div class="card-panel-icon-wrapper icon-people">
                                <svg-icon icon-class="peoples" class-name="card-panel-icon" />
                                </div>
                                <div class="card-panel-description">
                                <count-to :start-val="0" :end-val="0" :duration="2600" class="card-panel-num" />
                                <div class="card-panel-text">
                                    总人次
                                </div>
                                </div>
                            </div>
                          </el-col>
                          <el-col :md="4" :sm="6" :xs="24">
                            <div class="card-panel">
                                <div class="card-panel-icon-wrapper icon-eye">
                                <svg-icon icon-class="close-eye" class-name="card-panel-icon" />
                                </div>
                                <div class="card-panel-description">
                                <count-to :start-val="0" :end-val="0" :duration="2600" class="card-panel-num" />
                                <div class="card-panel-text">
                                    未查看
                                </div>
                                </div>
                            </div>
                          </el-col>
                          <el-col :md="4" :sm="6" :xs="24">
                            <div class="card-panel">
                                <div class="card-panel-icon-wrapper icon-show">
                                <svg-icon icon-class="show" class-name="card-panel-icon" />
                                </div>
                                <div class="card-panel-description">
                                <count-to :start-val="0" :end-val="0" :duration="2600" class="card-panel-num" />
                                <div class="card-panel-text">
                                    已查看
                                </div>
                                </div>
                            </div>
                          </el-col>
                          <el-col :md="4" :sm="6" :xs="24">
                            <div class="card-panel">
                                <div class="card-panel-icon-wrapper icon-remove">
                                <svg-icon icon-class="remove" class-name="card-panel-icon" />
                                </div>
                                <div class="card-panel-description">
                                <count-to :start-val="0" :end-val="0" :duration="2600" class="card-panel-num" />
                                <div class="card-panel-text">
                                    未提交
                                </div>
                                </div>
                            </div>
                          </el-col>
                          <el-col :md="4" :sm="6" :xs="24">
                            <div class="card-panel">
                                <div class="card-panel-icon-wrapper icon-select">
                                <svg-icon icon-class="select" class-name="card-panel-icon" />
                                </div>
                                <div class="card-panel-description">
                                <count-to :start-val="0" :end-val="0" :duration="2600" class="card-panel-num" />
                                <div class="card-panel-text">
                                    已提交
                                </div>
                                </div>
                            </div>
                          </el-col>
                          <el-col :md="4" :sm="6" :xs="24">
                            <div class="card-panel">
                                <div class="card-panel-icon-wrapper icon-notification">
                                <svg-icon icon-class="notification" class-name="card-panel-icon" />
                                </div>
                                <div class="card-panel-description">
                                <count-to :start-val="0" :end-val="0" :duration="2600" class="card-panel-num" />
                                <div class="card-panel-text">
                                    总催办次
                                </div>
                                </div>
                            </div>
                          </el-col>
                        </el-row>
                    </el-tab-pane>
                    <el-tab-pane name="process_chart" label="流程图" class="tab-pane-view">
                        <process-viewer :processData="processData" />
                    </el-tab-pane>
                    <el-tab-pane name="process_table" label="流程表格" class="tab-pane-view">

                    </el-tab-pane>
                    <el-tab-pane name="process_log" label="流程日志" class="tab-pane-view">
                        
                    </el-tab-pane>
                </el-tabs>
            </el-card>
            <el-card class="card-box" ref="card-permission">
                <div slot="header">
                    <span>权限</span>
                </div>
            </el-card>
            <el-card class="card-box" ref="card-statistics">
                <div slot="header">
                    <span>访问统计</span>
                </div>
                <el-table :data="kmsVisited">
                  <el-table-column label="序号" type="index" width="68" />
                  <el-table-column label="阅读人" align="center" prop="userVisited"/>
                  <el-table-column label="阅读时间" align="center" prop="visitTime" />
                  <el-table-column label="部门" align="center" prop="deptName" />
                  <el-table-column label="阅读阶段" align="center" prop="readState" />
                </el-table>
                <pagination
                    v-show="kmsVisitedTotal>0"
                    :total="kmsVisitedTotal"
                    :page.sync="kmsVisitQueryParams.pageNum"
                    :limit.sync="kmsVisitQueryParams.pageSize"
                    @pagination="getKmsVisit(form.id)"
                />
            </el-card>
            <el-card class="card-box" ref="card-borrow">
                <div slot="header">
                    <span>借阅记录</span>
                </div>
            </el-card>
        <el-backtop />
    </div>
</template>

<script>
    import { getCatalog } from "@/api/kms/catalog";
    import { listArea } from "@/api/kms/area";
    import BreadcrumbHeader from "../../kms/components/breadcrumb-header";
    import { listCustodyUnit } from "@/api/kms/custody-unit";
    import {addMain, getMain, previewAttFile } from "@/api/kms/main";
    import moment from 'moment'
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    
    import { getAttachmentLevel } from "@/api/kms/attachment-level"
    import { getAttachmentType } from "@/api/kms/attachment-type"
    import { getStorageTime } from "@/api/kms/storage-time"
    import { currentProcess, getTemplate } from "@/api/activiti/template"
    import ProcessViewer from '@/views/activiti/components/process-viewer';
    import CountTo from 'vue-count-to'
    import { listKmsVisit } from '@/api/kms/kms-visit'

    export default {
        components:{
            BreadcrumbHeader,
            Treeselect,
            ProcessViewer,
            CountTo
        },
        data() {
            return {
                loading: false,
                form:{},
                kmsCatalog:{},
                attTypeOptions:[],
                areaOptions:[],
                breadcrumbList: [
                    {
                    name: "档案录入",
                    },
                ],
                storageTimeOptions: [],
                custodyUnitOptions:[],
                attClassificationOptions:[
                    {id: 1, name: "秘密"},
                    {id: 2, name: "机密"},
                    {id: 3, name: "绝密"},
                ],
                catalogOptions:[],
                directory:"",
                catalogTitle:"",
                attClassificationName:"",
                attTypeName:"",
                storageTime:"",
                tabPaneProcessActive:"process",
                processList:[],
                historyData:[],
                processData:{},
                fileConverted:[],
                kmsVisitQueryParams: {
                    pageNum: 1,
                    pageSize: 10,
                },
                kmsVisited:[],
                kmsVisitedTotal: 0,
            }
        },
        created(){
        },
        mounted() {
            this.getKmsMain()
        },
        methods:{
            getKmsMain(){
                const id = this.$route.params && this.$route.params.id
                getMain(id).then(res => {
                    const data = res.data
                    this.form = data
                    getCatalog(this.form.catalogId).then(res => {
                        const catalog = res.data
                        if(catalog != null) {
                            this.catalogTitle = catalog.title
                            getTemplate(catalog.templateId).then(response =>{
                                const template = response.data;
                                if(template.processDefXml == undefined || template.processDefXml == ""){
                                    this.$message.error("没有找到模板");
                                }else{
                                    this.processData.xmlData = template.processDefXml
                                }
                            })
                        }
                    })

                    if(this.form.attClassification != null){
                        this.form.attClassificatio = this.attClassificationOptions.filter(item => item.id == this.form.attClassification)[0].name
                    }

                    if(this.form.attType != null){
                        getAttachmentType(this.form.attType).then(res => {
                            const data = res.data
                            if(data != null) {
                                this.attTypeName = data.name
                            }
                        })
                    }
                    
                    if(this.form.storageTime != null){
                        getStorageTime(this.form.storageTime).then(res => {
                            const data = res.data
                            if(data != null) {
                                this.storageTime = data.name
                            }
                        })
                    }

                    listArea().then(res => {
                        this.areaOptions = res.rows
                    })
                    listCustodyUnit().then(res => {
                        this.custodyUnitOptions = res.rows
                    })

                    if(this.form.filePath.length > 0){
                        this.form.fileNames = this.form.fileName.split(",")
                    }

                    currentProcess(this.form.processInstanceId).then(response => {
                        const findHistory = response.data;
                        this.historyData = findHistory
                        this.historyData.forEach(item => {
                            if(item.class == "startEvent"){
                                this.historyData.fdAssignee = ""
                            }
                        });
                        this.processData.nodeData = findHistory
                    })

                    previewAttFile(this.form.id).then(res => {
                        const data = res.data
                        if(data != null){
                            this.fileConverted = data
                        }
                    })

                    this.getKmsVisit(this.form.id);
                    
                })
                
            },
            getKmsVisit(id){
                const params = {
                    kmsId: id
                }
                listKmsVisit(params).then(res => {
                    this.kmsVisited = res.rows
                    this.kmsVisitedTotal = res.total
                })
            },
            gotoBack(){
                this.$router.back()
            },
            validate(){
                // 验证表单数据
                const { form } = this
                const { title, attType, area, filePath, fileName} = form
                
                if(!title){
                    this.$message.error("请输入档案名称")
                    return false
                }

                if(!area){
                    this.$message.error("请选择所属区域")
                    return false
                }

                if(!attType){
                    this.$message.error("请选择档案类型")
                    return false
                }

                if(!filePath || filePath.length == 0){
                    this.$message.error("请上传文件")
                    return false
                }

                if(!fileName || fileName.length == 0){
                    this.$message.error("请上传文件")
                    return false
                }

                return true
            },
            getFileList(list, names, attMainIds) {
                this.form.filePath = list
                this.form.fileName = names
                this.form.attMainIds = attMainIds
            },
            submitForm(oper) {
                this.loading = true
                if(this.validate()) {
                    this.form.state = oper === 1 || oper === 3 ? '30' : '10'
                    this.form.operation = oper
                    this.form.publishDate = null
                    this.form.updateTime = null
                    this.form.updateBy = null
                    this.form.updateUserId = null
                    this.form.attCreateTime = moment(this.form.attCreateTime).format("YYYY-MM-DD HH:mm:ss")
                    this.form.attExpirationTime = moment(this.form.attExpirationTime).format("YYYY-MM-DD HH:mm:ss")
                    this.form.currentVersionId = this.form.id
                    this.form.id = null
                    addMain(this.form).then(response => {
                        this.$modal.msgSuccess("新增成功");
                        this.loading = false;
                        this.close();
                    });
                }
            },
            close(){
                this.$router.push({
                    path: "/kms/attachment"
                })
            },
            normalizer(node) {
                if (node.children && !node.children.length) {
                    delete node.children;
                }
                return {
                    id: node.id,
                    label: node.title,
                    children: node.children
                };
            },
            handleView(item){
                console.log(item.converted)
                this.$router.push( {
                    name: "kms-view",
                    query: {
                        filePath: item.converted
                    }
                });
            },
            handleDownload(){

            }
        }
    }
</script>

<style lang="scss" scoped>
    .app-container {
        background-color: #eff1f4;
    }

    .card-header {
        display: flex;
        justify-content: center;
        font-size: 24px;
        font-weight: bold;
        letter-spacing: 3px;
    }

    .card-box {
        margin-bottom: 20px;
    }

    .header-toolbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 5px 0;
        margin-bottom: 20px;
    }
    
    .tag-filename {
        cursor: pointer;
        width: 100%;
        height: 48px;
        display: flex;
        align-items: center;
    }

    .operator{
        display: flex;
        justify-content: end;
        align-items: center;
    }

    .process-history-content {
        margin-top: 20px;
        margin-left: 30px;
    }

    .card-status {
        background-color: #f7f7f7;
    }

    .img-box {
        background-color: #D4997B;
        width: 68px;
        height: 68px;
        padding: 10px;
    }

    .img-status {
        width: 48px;
        height: 48px;
    }



    .card-panel {
        height: 108px;
        cursor: pointer;
        font-size: 12px;
        position: relative;
        overflow: hidden;
        color: #666;
        background: #eff1f4;
        //box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
        border-color: rgba(0, 0, 0, .05);
        margin: 15px 10px;

        &:hover {
        box-shadow: 5px 7px 20px grey;
        }

        .icon-people {
            color: #fff;
            background: #d4997b;
        }
        .icon-eye {
            color: #fff;
            background: #82b2d1;
        }

        .icon-show {
            color: #fff;
            background: #82b2d1;
        }

        .icon-remove {
            color: #fff;
            background: #92c18f;
        }

        .icon-select {
            color: #fff;
            background: #92c18f;
        }

        .icon-notification {
            color: #fff;
            background: #c0a1cd;
        }

        .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
        }

        .card-panel-icon {
            float: left;
            font-size: 48px;
        }

        .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
        margin-left: 0px;

        .card-panel-text {
            line-height: 18px;
            color: rgba(0, 0, 0, 0.45);
            font-size: 16px;
            margin-bottom: 12px;
            margin-top: 12px;
        }

        .card-panel-num {
            font-size: 20px;
        }
    }
  }

.tab-pane-view {
    height: 540px;
    overflow: auto;
}

</style>