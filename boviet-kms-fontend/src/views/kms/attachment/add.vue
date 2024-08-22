<template>
    <div class="app-container">
        <breadcrumb-header
            :breadcrumbList="breadcrumbList"
            background="#ffffff"
            exist-back
            class="mb20">
            <template slot="right">
                <el-button type="default" size="small" @click="submitForm(0)" v-if="form.state!=30" :loading="loading">保 存</el-button>
                <el-button type="primary" size="small" @click="submitForm(1)" :loading="loading">提 交</el-button>
                <el-button type="danger" size="small" @click="close">关 闭</el-button>
            </template>
        </breadcrumb-header>
        <el-form :model="form">
            <el-card class="card-box" ref="card-info">
                <div slot="header" class="card-header">
                    <span>档案信息</span>
                </div>
                <el-row type="flex" justify="center">
                    <el-col :span="18">
                        <el-descriptions :column="2" border size="mini" class="kms-info">
                            <el-descriptions-item label="档案名称" :span="2">
                                <el-input v-model="form.title" placeholder="请输入档案名称" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="档案类型">
                                <el-select v-model="form.attType" placeholder="请选择档案类型" size="mini">
                                    <el-option v-for="(attType,index) in attTypeOptions" 
                                            :key="index" 
                                            :label="attType.name"
                                            :value="attType.id"/>
                                </el-select>
                            </el-descriptions-item>
                            <el-descriptions-item label="所属分类">
                                <el-input :value="kmsCatalog.title" disabled size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="所属区域">
                                <el-select v-model="form.area" placeholder="请选择所属区域" size="mini">
                                     <el-option v-for="(area,index) in areaOptions" 
                                            :key="index" 
                                            :label="area.areaName"
                                            :value="area.areaName"/>
                                </el-select>
                            </el-descriptions-item>
                            <el-descriptions-item label="归档人">
                                <el-input v-model="form.archiver" placeholder="请输入归档人" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="档案总称">
                                <el-input v-model="form.generalName" placeholder="请输入档案总称" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="档案材料内容">
                                <el-input v-model="form.summary" placeholder="请输入档案材料内容" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="档案编号">
                                <el-input size="mini" value="提交时自动生成" disabled />
                            </el-descriptions-item>
                            <el-descriptions-item label="历史档案编号">
                                <el-input v-model="form.attCodeH" placeholder="请输入历史档案编号" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="归档日期">
                                <el-date-picker v-model="form.createTime" placeholder="请输入归档日期" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="资料形成日期">
                                <el-date-picker v-model="form.attCreateTime" placeholder="请输入资料形成日期" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="保管期限">
                                <el-select v-model="form.storageTime" placeholder="请选择保管期限" size="mini">
                                  <el-option v-for="(storageTime,index) in storageTimeOptions" 
                                    :key="index" 
                                    :label="storageTime.name"
                                    :value="storageTime.id" />
                                </el-select>
                            </el-descriptions-item>
                            <el-descriptions-item label="档案有效期">
                                <el-date-picker v-model="form.attExpirationTime" placeholder="请输入档案有效期" size="mini" />
                            </el-descriptions-item>
                            <el-descriptions-item label="箱号">
                                <el-input v-model="form.boxNo" placeholder="请输入箱号" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="盒号">
                                <el-input v-model="form.boxesNo" placeholder="请输入盒号" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="案卷号">
                                <el-input v-model="form.attNumber" placeholder="请输入案卷号" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="目录号">
                                <el-input v-model="form.catalogNumber" placeholder="请输入目录号" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="卷内序号">
                                <el-input v-model="form.attSerialNumber" placeholder="请输入卷内序号" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="密级程度">
                                <el-select v-model="form.attClassification" placeholder="请选择密级程度" size="mini">
                                  <el-option v-for="(attClassification, index) in attClassificationOptions" 
                                    :key="index" 
                                    :label="attClassification.name"
                                    :value="attClassification.id"/>
                                </el-select>
                            </el-descriptions-item>
                            <el-descriptions-item label="件数/本">
                                <el-input v-model="form.attCount" placeholder="请输入件数/本" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="页数/页码">
                                <el-input v-model="form.pageNumber" placeholder="请输入页数/页码" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="保管单位">
                                <el-select v-model="form.custodyUnit" placeholder="请选择保管单位" size="mini">
                                  <el-option v-for="custodyUnit in custodyUnitOptions" 
                                    :key="custodyUnit.id"
                                    :label="custodyUnit.name"
                                    :value="custodyUnit.name"/>
                                </el-select>
                            </el-descriptions-item>
                            <el-descriptions-item label="外部相关单位">
                                <el-input v-model="form.externalUnit" placeholder="请输入外部相关单位" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="移交档案部门">
                                <el-input v-model="form.filingDept" placeholder="请输入移交档案部门" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item></el-descriptions-item>
                            <el-descriptions-item label="备注" :span="2">
                                <el-input v-model="form.marks" placeholder="请输入备注" size="mini"/>
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
                  <el-col :span="24" align="center">
                    <file-upload @input="getFileList" :drag="true" :directory="directory" :value="form.filePath" />
                  </el-col>
                </el-row>
            </el-card>
            <el-card class="card-box card-permission" ref="card-permission">
                <div slot="header">
                    <span>权限</span>
                </div>
                <el-descriptions :column="1" border class="descriptions-permission" >
                    <el-descriptions-item label="档案权限">
                        <el-input
                            type="textarea"
                            placeholder=""
                            :autosize="{ minRows: 6, maxRows: 6}"
                            resize="none"
                            readonly/>
                            <el-button icon="el-icon-search" @click="handlePermission"/>
                    </el-descriptions-item>
                </el-descriptions>
            </el-card>
        </el-form>
        <<el-dialog
            title="权限"
            :visible.sync="open"
            width="30%"
            style="height: 680px;">
            <div class="dialog-body">
                <el-tabs v-model="activeTab" type="card" tab-position="top" @tab-click="">
                    <el-tab-pane label="部门" name="department">
                        <div class="head-container">
                            <el-input v-model="deptName" placeholder="请输入部门名称" clearable size="small" prefix-icon="el-icon-search"
                                style="margin-bottom: 20px" />
                        </div>
                        <div class="head-container">
                            <el-tree :data="deptOptions" 
                                    :props="defaultProps" 
                                    :expand-on-click-node="false"
                                    :filter-node-method="filterNode" 
                                    ref="dept_tree" 
                                    default-expand-all 
                                    highlight-current
                                    show-checkbox
                                    @check-change="handleDepartmentChange"/>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="用户组" name="group">
                    </el-tab-pane>
                    <el-tab-pane label="用户" name="user">
                    </el-tab-pane>
                </el-tabs>
                
            </div>
            <div slot="footer">
                <el-button @click="cancel">Cancel</el-button>
                <el-button type="primary">OK</el-button>
            </div>
        </el-dialog>
        
        <el-backtop />
    </div>
</template>

<script>
    import { getCatalog } from "@/api/kms/catalog"
    import { listArea } from "@/api/kms/area"
    import BreadcrumbHeader from "../../kms/components/breadcrumb-header"
    import { listCustodyUnit } from "@/api/kms/custody-unit"
    import {addMain, updateMain } from "@/api/kms/main"
    import moment from 'moment'
    import { listAttachmentType } from "@/api/kms/attachment-type"
    import { listStorageTime } from "@/api/kms/storage-time"
    import { treeselect } from "@/api/system/dept"

    export default {
        components:{
            BreadcrumbHeader
        },
        data() {
            return {
                loading: false,
                form:{
                    createTime: moment(new Date()).format("YYYY-MM-DD HH:mm:ss")
                },
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
                directory:"",
                open: false,
                activeTab:"department",
                deptName:"",
                deptOptions: undefined,
                defaultProps: {
                    children: "children",
                    label: "label"
                },
                permissions: {
                    department: [],
                    user: [],
                    group: [],
                },
            }
        },
        created(){
        },
        mounted() {
            this.getList()
        },
        watch: {
            // 根据名称筛选部门树
            deptName(val) {
            this.$refs.dept_tree.filter(val);
            }
        },
        methods:{
            getList(){
                const id = this.$route.params && this.$route.params.catalogId
                if (id){
                    getCatalog(id).then(res => {
                        const data = res.data
                        if(data != undefined) {
                            this.kmsCatalog = data
                            this.directory = this.kmsCatalog.title
                        }
                        else{
                            this.close();
                        }
                    })

                    listAttachmentType().then(res => {
                        this.attTypeOptions = res.rows
                    })
                    
                    listStorageTime().then(res => {
                        this.storageTimeOptions = res.rows
                    })

                    listArea().then(res => {
                        this.areaOptions = res.rows
                    })

                    listCustodyUnit().then(res => {
                        this.custodyUnitOptions = res.rows
                    })

                    this.getDepartment()
                    
                }
                else{
                    this.close();
                }
                
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
            getDepartment() {
                treeselect().then(response => {
                    this.deptOptions = response.data;
                });
            },
            // 筛选节点
            filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
            },
            handleDepartmentChange(data, checked, indeterminate) {
                if(checked) {
                    this.permissions.department.push(data.id);
                }else{
                    const index = this.permissions.department.indexOf(data.id);
                    if(index > -1){
                        this.permissions.department.splice(index, 1);
                    }
                }
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
                    this.form.catalogId = this.kmsCatalog.id
                    this.form.attCreateTime = this.form.attCreateTime == null ? null : moment(this.form.attCreateTime).format("YYYY-MM-DD HH:mm:ss")
                    this.form.attExpirationTime = this.form.attExpirationTime == null ? null : moment(this.form.attExpirationTime).format("YYYY-MM-DD HH:mm:ss")
                    if (this.form.id != null) {
                        this.form.state = null
                        updateMain(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.loading = false;
                            this.close();
                        });
                    } else {
                        addMain(this.form).then(response => {
                            this.$modal.msgSuccess("新增成功");
                            this.loading = false;
                            this.close();
                        });
                    }
                }
            },
            close(){
                this.$router.push({
                    path: "/kms/attachment"
                })
            },
            cancel(){
                this.open = false;
            },
            handlePermission(){
                this.open = true;
            }
        }
    }
</script>

<style lang="scss" scoped>
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
</style>

<style lang="scss">
.descriptions-permission {
    .el-descriptions__body {
        .el-descriptions__table {
            .el-descriptions-row {
                .el-descriptions-item__label {
                    width: 15%;
                    text-align: center;
                    font-size: 16px;
                }
                .el-descriptions-item__content {
                    display: flex;
                }
            }
        }
    }
}

.kms-info{
    .el-descriptions__body {
        .el-descriptions__table {
            .el-descriptions-row {
                .el-descriptions-item__cell {
                    color: black;
                    letter-spacing: 1px;
                    font-size: 16px;
                }
            }
        }
    }
}
</style>