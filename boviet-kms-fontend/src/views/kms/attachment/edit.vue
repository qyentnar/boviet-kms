<template>
    <div class="app-container">
        <breadcrumb-header
            :breadcrumbList="breadcrumbList"
            background="#ffffff"
            exist-back
            class="mb20">
            <template slot="right">
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
                        <el-descriptions :column="2" border size="mini">
                            <el-descriptions-item label="档案名称" :span="2">
                                <el-input v-model="form.title" placeholder="请输入档案名称" size="mini"/>
                            </el-descriptions-item>
                            <el-descriptions-item label="档案类型">
                                <el-select v-model="form.attType" placeholder="请选择档案类型" size="mini">
                                    <el-option v-for="(attType,index) in attTypeOptions" 
                                            :key="index" 
                                            :label="attType.label"
                                            :value="attType.value"/>
                                </el-select>
                            </el-descriptions-item>
                            <el-descriptions-item label="所属分类">
                                <treeselect v-model="form.catalogId" :options="catalogOptions" :normalizer="normalizer" placeholder="所属分类" />
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
                                <div class="el-input el-input--mini is-disabled">
                                    <span class="el-input__inner">{{form.attCode}}</span>
                                </div>
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
                                    :label="storageTime.label"
                                    :value="storageTime.value" />
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
                                    :label="attClassification.label"
                                    :value="attClassification.value"/>
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
        </el-form>
        <el-backtop />
    </div>
</template>

<script>
    import { listCatalogForTree } from "@/api/kms/catalog";
    import { listArea } from "@/api/kms/area";
    import BreadcrumbHeader from "../../kms/components/breadcrumb-header";
    import { listCustodyUnit } from "@/api/kms/custody-unit";
    import { updateMain, getMain} from "@/api/kms/main";
    import moment from 'moment'
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";

    export default {
        components:{
            BreadcrumbHeader,
            Treeselect
        },
        data() {
            return {
                loading: false,
                form:{
                    createTime: moment(new Date()).format("YYYY-MM-DD HH:mm:ss"),
                    mainContent: '',
                    author: {
                        type: 'Internal',
                        inside: [],
                        external: [],
                        check: false,
                        checkText: ''
                    },
                },
                attTypeOptions:[
                    {
                        value: "1",
                        label: "电子"
                    },
                    {
                        value: "2",
                        label: "纸质"
                    },
                    {
                        value: "3",
                        label: "电子和纸质"
                    }
                ],
                areaOptions:[],
                breadcrumbList: [
                    {
                    name: "档案录入",
                    },
                ],
                storageTimeOptions: [
                    {
                        value: "5",
                        label: "短期"
                    },
                    {
                        value: "10",
                        label: "长期"
                    },
                    {
                        value: "9999",
                        label: "永久"
                    }
                ],
                custodyUnitOptions:[],
                attClassificationOptions:[
                    {
                        value: "1",
                        label: "秘密"
                    },
                    {
                        value: "2",
                        label: "机密"
                    },
                    {
                        value: "3",
                        label: "绝密"
                    }
                ],
                catalogOptions:[],
                directory:"",
            }
        },
        created(){
        },
        mounted() {
            this.getKmsMain()
        },
        methods:{
            getKmsMain(){
                const id = this.$route.params  && this.$route.params.id
                if(id){
                    getMain(id).then(res => {
                        const data = res.data
                        this.form = data
                        listCatalogForTree().then(res => {
                            const catalogs = res.data
                            this.catalogOptions = this.handleTree(catalogs,"id")
                            this.directory = catalogs.filter(item => item.id == this.form.catalogId)[0].title
                        })
                    })
                    listArea().then(res => {
                        this.areaOptions = res.rows
                    })
                    listCustodyUnit().then(res => {
                        this.custodyUnitOptions = res.rows
                    })
                }else{
                    this.gotoBack()
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
                    if (this.form.id != null) {
                        this.form.state = null
                        updateMain(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
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