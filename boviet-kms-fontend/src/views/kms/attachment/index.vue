<template>
    <div class="app-container">
        <el-tabs v-model="mainTabs" @tab-click="handleTabsClick">
            <el-tab-pane label="我录入的" name="my-attachment">
                <el-card v-show="showSearch" :style="`margin-bottom: 30px`">
                    <el-row class="search-input">
                        <el-col :md="12" :sm="24" :xs="24">
                            <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
                                <el-row>
                                    <el-col :span="24">
                                        <el-form-item prop="catalogId">
                                            <div class="search-toolbar">
                                                <el-dropdown class="dropdown-list">
                                                    <el-button style="width: 120px;">
                                                        所属分类 <i class="el-icon-arrow-down el-icon--right"></i>
                                                    </el-button>
                                                    <el-dropdown-menu slot="dropdown">
                                                        <el-dropdown-item>Action 1</el-dropdown-item>
                                                        <el-dropdown-item>Action 2</el-dropdown-item>
                                                        <el-dropdown-item>Action 3</el-dropdown-item>
                                                        <el-dropdown-item>Action 4</el-dropdown-item>
                                                        <el-dropdown-item>Action 5</el-dropdown-item>
                                                    </el-dropdown-menu>
                                                </el-dropdown>
                                                <div v-for="catalog in catalogOptions" :key="catalog.id">
                                                    <el-button :type="buttonType" plain size="mini" @click="handleCatalogSelected(catalog.id)" style="margin-right: 20px;">
                                                        {{catalog.title}} <i class="el-icon-back el-icon--right" v-if="catalog.id === catalogSelected"/>
                                                    </el-button>
                                                </div>
                                            </div>
                                        </el-form-item>
                                    </el-col>
                                    <el-col :span="24" style="margin-top: -20px;">
                                        <div class="search-toolbar">
                                            <span class="toolbar-label">
                                                档案编号
                                            </span>
                                            <el-input v-model="attCode" clearable  placeholder="请输入" size="small" style="width: 350px;" @input="handleAttCodeInput">
                                                <el-button v-show="showBtnAttCodeSearch" :slot="showBtnAttCodeSearch ? 'append': ''" icon="el-icon-search" @click="handleAttCodeSearch"/>
                                            </el-input>
                                        </div>
                                    </el-col>
                                    <el-col :span="24">
                                        <div class="search-toolbar">
                                            <span class="toolbar-label">
                                                归档日期
                                            </span>
                                            <div class="block">
                                                <el-date-picker
                                                    v-model="queryParams.createTime"
                                                    type="daterange"
                                                    align="left"
                                                    unlink-panels
                                                    range-separator="到"
                                                    start-placeholder="开始时间"
                                                    end-placeholder="结束时间"
                                                    :picker-options="pickerOptions" 
                                                    @change="handleCreateTimeChanged"/>
                                            </div>
                                        </div>
                                    </el-col>
                                </el-row>
                            </el-form>
                        </el-col>
                        <el-col :md="12" :sm="24" :xs="24">
                            <el-input placeholder="请输入" class="input-with-select" clearable >
                                <el-button slot="append" icon="el-icon-search"></el-button>
                            </el-input>
                            <div>
                                <el-tag closable @close="handleCloseTag(1)" v-if="queryParams.catalogId.length > 0" type="warning" effect="dark" style="margin-right: 10px;">
                                    <span v-for="(catalog,index) in catalogList.filter(item => queryParams.catalogId.includes(item.id))" :key="index">
                                        <span v-if="index <= 0">
                                            所属分类: 
                                        </span>
                                        <span>
                                            {{catalog.title}}
                                        </span>
                                        <span v-if="index >= 0 && index < catalogList.filter(item => queryParams.catalogId.includes(item.id)).length - 1">
                                            >
                                        </span>
                                    </span>
                                </el-tag>
                                <el-tag closable @close="handleCloseTag(2)" v-if="queryParams.attCode.length > 0" type="warning" effect="dark" style="margin-right: 10px;">
                                    档案编号: {{queryParams.attCode}}
                                </el-tag>
                                <el-tag closable @close="handleCloseTag(3)" v-if="queryParams.createTime.length > 0" type="warning" effect="dark">
                                    归档日期: {{queryParams.createTime}}
                                </el-tag>
                            </div>
                        </el-col>
                    </el-row>
                </el-card>
                <el-row :gutter="10" class="mb8" style="margin-right: 10px;">
                    <el-col :span="1.5">
                        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['kms:main:add']">新增</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['kms:main:edit']">修改</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="info" plain icon="el-icon-magic-stick" size="mini" :disabled="single" @click="handleNewVersion" v-hasPermi="['kms:main:add']">新版本</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['kms:main:remove']">删除</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['kms:main:export']">导出</el-button>
                    </el-col>
                    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
                </el-row>
                <el-table ref="dataTable" v-loading="loading" :data="mainList" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="主键ID" align="center" prop="id"/>
                    <el-table-column label="档案名称" align="center" prop="title" />
                    <el-table-column label="档案编号" align="center" prop="attCode" />
                    <el-table-column label="归档人" align="center" prop="archiver" />
                    <el-table-column label="归档日期" align="center" prop="createTime" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <!-- <el-button size="mini" type="primary" class="btn-action" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['kms:main:edit']" v-if="scope.row.state!=20">编辑</el-button> -->
                            <el-button size="mini" type="success" plain class="btn-action" icon="el-icon-position" @click="handleStartProcess(scope.row)" v-hasPermi="['kms:main:start']" v-if="!scope.row.processInstanceId">发布</el-button>
                            <!-- <el-button size="mini" type="primary" class="btn-action" icon="el-icon-edit" @click="handleNewVersion(scope.row)" v-hasPermi="['kms:main:edit']" v-if="scope.row.state==='20'">新版本</el-button> -->
                            <!-- <el-button size="mini" type="danger" class="btn-action" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['kms:main:remove']" v-if="!scope.row.processInstanceId">删除</el-button> -->
                            <!-- <el-button size="mini" type="text" icon="el-icon-preview" @click="handlePreviewAttFile(scope.row)" v-if="scope.row.fileName!=null">预览附件</el-button> -->
                            <el-button size="mini" type="default" plain class="btn-action" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
            </el-tab-pane>
            <el-tab-pane label="档案录入">
                <el-row :gutter="20">
                    <el-col :xl="4" :md="4" :sm="24" :xs="24">
                        <div class="head-container">
                            <el-input
                                v-model="catalogName"
                                placeholder="请输入档案类型"
                                clearable
                                size="small"
                                prefix-icon="el-icon-search"
                                style="margin-bottom: 20px"
                            />
                        </div>
                        <div class="head-container">
                            <el-tree
                            :data="catalogTreeSelect"
                            :props="defaultProps"
                            default-expand-all 
                            :filter-node-method="filterNode"
                            ref="tree"
                            highlight-current>
                            <span class="custom-tree-node" slot-scope="{ node, data }">
                                <span>{{ node.label }}</span>
                                <span>
                                <el-button
                                    type="text"
                                    size="mini"
                                    @click="() => handleAddButtonCatalog(data)" v-if="buttonCatalogs.findIndex(item => item.id === data.id) === -1">
                                    添加
                                </el-button>
                                <el-button
                                    type="text"
                                    size="mini"
                                    style="color: red"
                                    @click="() => handleRemoveButtonCatalog(node, data)" v-if="buttonCatalogs.findIndex(item => item.id === data.id) !== -1">
                                    删除
                                </el-button>
                                </span>
                            </span>
                            </el-tree>
                        </div>
                    </el-col>
                        <el-col :xl="20" :md="20" :sm="24" :xs="24">
                            <el-button type="text" class="button-catalog" v-for="catalog in buttonCatalogs" :key="catalog.id" @click="handleCatalogClick(catalog.id)">
                                {{catalog.label}}
                            </el-button>
                        </el-col>
                    </el-row>
            </el-tab-pane>
            <el-tab-pane :label="`草稿箱 (${totalTemp})`">
                <el-table ref="dataTableTemp" v-loading="loading" :data="mainListTemp" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="主键ID" align="center" prop="id"/>
                    <el-table-column label="档案名称" align="center" prop="title" />
                    <el-table-column label="档案编号" align="center" prop="attCode" />
                    <el-table-column label="归档人" align="center" prop="archiver" />
                    <el-table-column label="归档日期" align="center" prop="createTime" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template slot-scope="scope">
                            <!-- <el-button size="mini" type="primary" class="btn-action" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['kms:main:edit']" v-if="scope.row.state!=20">编辑</el-button> -->
                            <el-button size="mini" type="text" icon="el-icon-position" @click="handleStartProcess(scope.row)" v-hasPermi="['kms:main:start']" v-if="!scope.row.processInstanceId">发布</el-button>
                            <!-- <el-button size="mini" type="primary" class="btn-action" icon="el-icon-edit" @click="handleNewVersion(scope.row)" v-hasPermi="['kms:main:edit']" v-if="scope.row.state==='20'">新版本</el-button> -->
                            <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['kms:main:remove']" v-if="!scope.row.processInstanceId">删除</el-button>
                            <!-- <el-button size="mini" type="text" icon="el-icon-preview" @click="handlePreviewAttFile(scope.row)" v-if="scope.row.fileName!=null">预览附件</el-button> -->
                            <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <pagination v-show="totalTemp>0" :total="totalTemp" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getListTemp" />
            </el-tab-pane>
        </el-tabs>
        <!-- 附件预览对话框 -->
        <el-dialog :title="title" :visible.sync="openPreview" width="960px" append-to-body>
            <el-row>
                <el-col :span="24" align="left" style="margin-bottom: 10px;">
                    <el-select v-model="pdfUrl" style="width: 50%;">
                        <el-option v-for="file in previewFile" 
                            :key="file.fullPath" 
                            :label="file.name" 
                            :value="file.fullPath" 
                            :default-first-option = "true"/>
                    </el-select>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24" style="height: 540px;">
                    <document-viewer :pdfUrl="pdfUrl" />
                </el-col>
            </el-row>
            <div slot="footer" class="dialog-footer">
                <el-button type="danger" @click="cancelPreview">关闭</el-button>
            </div>
        </el-dialog>

        <el-dialog :title="title" :visible.sync="open" width="640px" append-to-body>
            <el-card style="height: 480px;">
                <el-row :gutter="20">
                    <el-col :span="24">
                        <div class="head-container">
                            <el-input
                                v-model="catalogName"
                                placeholder="请输入档案类型"
                                clearable
                                size="small"
                                prefix-icon="el-icon-search"
                                style="margin-bottom: 20px"
                            />
                        </div>
                        <div class="head-container">
                            <el-tree
                                :data="catalogTreeSelect"
                                :props="defaultProps"
                                :expand-on-click-node="true"
                                :filter-node-method="filterNode"
                                ref="tree"
                                highlight-current
                                @node-click="handleNodeClick"
                            />
                        </div>
                    </el-col>
                </el-row>
            </el-card>
            <div slot="footer">
                <el-button type="primary" @click="submitCatalog">提 交</el-button>
                <el-button @click="cancel()">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>
    
    <script>
    import { listMain, getMain, delMain, previewAttFile, startProcess, listMainTemp } from "@/api/kms/main";
    import { listCatalogForTree, listCatalog, treeselect } from "@/api/kms/catalog";
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    import documentViewer from "../components/document-viewer";
    import moment from 'moment'
    
    export default {
        name: "Kms-main",
        components: {
            Treeselect,
            documentViewer},
        data() {
            return {
                loading: true,
                ids: [],
                single: true,
                multiple: true,
                showSearch: false,
                total: 0,
                totalTemp:0,
                mainList: [],
                mainListTemp: [],
                title: "",
                open: false,
                pdfUrl: "",
                baseUrl: process.env.VUE_APP_BASE_API,
                mainTabs:"my-attachment",
                buttonType: "primary",
                catalogSelected: null,
                catalogList: [],
                attCode: "",
                catalogName:"",
                showBtnAttCodeSearch: false,
                catalogTreeSelect:[],
                openPreview: false,
                previewFile: [],
                catalogOptions: [],
                buttonCatalogs:[],
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    title: null,
                    catalogId: [],
                    deptId: null,
                    mainContent: null,
                    summary: null,
                    keyword: null,
                    coverImg: null,
                    version: null,
                    state: null,
                    publishDate: null,
                    readCount: null,
                    createUser: null,
                    updateUser: null,
                    createTime: [],
                    attCode: ""
                },
                // 表单参数
                form: {
                    mainContent: '',
                    author: {
                        type: 'Internal',
                        inside: [],
                        external: [],
                        check: false,
                        checkText: ''
                    },
                },
                // 表单校验
                rules: {
                    title: [{
                            required: true,
                            message: '请输入知识标题',
                            trigger: 'blur'
                        },
                        {
                            min: 5,
                            max: 30,
                            message: '长度在 5 到 30 个字符',
                            trigger: 'blur'
                        }
                    ],
                    catalogId: [{
                        required: true,
                        message: '请选择目录',
                        trigger: 'blur'
                    }, ],
                    author: [{
                        required: true,
                        message: '请输入作者',
                        trigger: 'blur'
                    }, ],
                    summary: [{
                            required: true,
                            message: '请输入知识摘要',
                            trigger: 'blur'
                        },
                        {
                            min: 10,
                            max: 100,
                            message: '长度在 10 到 100 个字符',
                            trigger: 'blur'
                        }
                    ]
                },
                pickerOptions: {
                    shortcuts: [{
                        text: '近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    }, 
                    {
                        text: '近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    },
                    {
                        text: '近三个月',
                        onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setMonth(start.getMonth() - 3);
                        picker.$emit('pick', [start, end]);
                        },
                    },
                    {
                        text: '近半年',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setMonth(start.getMonth() - 6);
                            picker.$emit('pick', [start, end]);
                        },
                    },
                    {
                        text: '近一年',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setFullYear(start.getFullYear() - 1);
                            picker.$emit('pick', [start, end]);
                        }
                    }]
                },
                defaultProps: {
                    children: 'children',
                    label: 'label'
                },
            };
        },
        filters: {
            stateFilter(s) {
                switch (s) {
                    case '10':
                        return '草稿'
                    case '20':
                        return '待审'
                    case '30':
                        return '发布'
                }
            },
            versionFilter(v) {
                if (v) {
                    return v + ".0"
                }
                return '-'
            }
        },
        watch:{
            catalogName(val){
                this.$refs.tree.filter(val);
            }
        },
        created() {
        },
        mounted() {
            this.getList();
            this.getListTemp();
        },
        methods: {
            /** 查询知识列表 */
            getList() {
                this.loading = true;
                listMain(this.queryParams).then(response => {
                    this.mainList = response.rows;
                    this.total = response.total;
                    this.loading = false;
                });
                listCatalog().then(response => {
                    if(response.rows.length > 0){
                        this.catalogOptions = this.handleTree(response.rows, "id");
                        this.catalogList = response.rows;
                        this.getCatalogUsed();
                    }
                });
            },
            getListTemp(){
                listMainTemp(this.queryParams).then(response => {
                    this.mainListTemp = response.rows;
                    this.totalTemp = response.total;
                    this.loading = false;
                });
            },
            getCatalogUsed(){
                const jsonData = this.$cache.local.get("catalog_used");
                if(jsonData){
                    this.buttonCatalogs = JSON.parse(jsonData);
                }
            },
            // 取消按钮
            cancel() {
                this.open = false;
                this.reset();
            },
            // 表单重置
            reset() {
                this.form = {
                    id: null,
                    title: null,
                    catalogId: null,
                    deptId: null,
                    mainContent: null,
                    summary: null,
                    keyword: null,
                    coverImg: null,
                    version: null,
                    state: null,
                    publishDate: null,
                    readCount: null,
                    createUser: null,
                    createTime: null,
                    updateUser: null,
                    updateTime: null,
                    author: {
                        type: 'Internal',
                        inside: [],
                        external: [],
                        check: false,
                        checkText: ''
                    }
                };
                this.resetForm("form");
            },
            /** 搜索按钮操作 */
            handleQuery() {
                this.queryParams.pageNum = 1;
                this.getList();
            },
            /** 重置按钮操作 */
            resetQuery() {
                this.resetForm("queryForm");
                this.handleQuery();
            },
            // 多选框选中数据
            handleSelectionChange(selection) {
                this.ids = selection.map(item => item.id)
                this.single = selection.length !== 1
                this.multiple = !selection.length
            },
            /** 新增按钮操作 */
            handleAdd() {
                this.reset();
                this.open = true;
                this.title = "档案类型";
                treeselect().then(response => {
                    this.catalogTreeSelect = response.data
                })
            },
            handleUpdate(row){
                const ids = row.id || this.ids;
                this.$router.push({
                    name: "attachment-edit",
                    params: {
                        id: ids
                    }
                })
            },
            /** 删除按钮操作 */
            handleDelete(row) {
                const ids = row.id || this.ids;
                this.$modal.confirm('是否确认删除知识编号为"' + ids + '"的数据项？').then(function () {
                    return delMain(ids);
                }).then(() => {
                    this.getList();
                    this.$modal.msgSuccess("删除成功");
                }).catch(() => {});
            },
            /** 导出按钮操作 */
            handleExport() {
                this.download('kms/main/export', {
                    ...this.queryParams
                }, `main_${new Date().getTime()}.xlsx`)
            },
            /** 预览附件操作 */
            handlePreview(row) {
                this.reset();
                const id = row.id || this.ids
                getMain(id).then(response => {
                    const previewData = response.data;
                    const paths = previewData.filePath.split(",")
                    if (paths.length == 1) {
                        this.openFile(previewData.filePath)
                    } else {
                        const names = previewData.fileName.split(",")
                        this.previewFile = []
                        for (let i in names) {
                            this.previewFile.push({
                                name: names[i],
                                path: paths[i]
                            })
                        }
                        this.openPreview = true;
                        this.title = "附件预览";
                    }
                });
            },
            /** 预览附件操作 new */
            handlePreviewAttFile(row) {
                this.reset();
                const id = row.originId || this.ids
                previewAttFile(id).then(response => {
                    const previewData = response.data;
                    this.previewFile = []
                    this.pdfUrl = "";
                    for (let i in previewData) {
                        this.previewFile.push({
                            name: previewData[i].fileName,
                            path: previewData[i].converted,
                            fullPath: this.baseUrl + previewData[i].converted,
                            state: previewData[i].state
                        })
                    }
                    if(this.previewFile.length > 0) {
                        this.pdfUrl = this.previewFile[0].fullPath;
                    }
                    this.openPreview = true;
                    this.titlePreview = "附件预览";
                });
            },
            // 取消按钮
            cancelPreview() {
                this.openPreview = false;
                this.reset();
            },
            /** 查看详情操作 */
            handleView(row){
                this.$router.push({
                    name: "attachment-view",
                    params: {
                        id: row.id
                    }
                })
            },
            handleStartProcess(row){
                startProcess(row.id).then(res => {
                    if (res.msg == null){
                        this.$message.error("启动流程失败");
                    }else{
                        this.$modal.msgSuccess("启动流程成功");
                        this.getList();
                    }
                })
            },
            handleCatalogSelected(catalogId){
                const catalogIds = this.queryParams.catalogId
                if(catalogIds.indexOf(catalogId) == -1){
                    this.queryParams.catalogId.push(catalogId)
                }else {
                    this.queryParams.catalogId.splice(this.queryParams.catalogId.indexOf(catalogId), 1)
                }
                const lastSelected = this.queryParams.catalogId.slice(-1)[0]
                this.catalogSelected = lastSelected
                if(lastSelected === null || lastSelected === undefined) {
                    listCatalog().then(response => {
                        this.catalogOptions = this.handleTree(response.rows,"id")
                    })
                }else{
                    listCatalog().then(response => {
                        if(response.rows != null && response.rows.length){
                            const data = response.rows
                            this.catalogOptions = data.filter(catelog => {
                                return catelog.parentId === lastSelected || catelog.id === lastSelected;
                            })
                        }
                    })
                }
            },
            handleCloseTag(index){
                switch(index){
                    case 1:
                        this.queryParams.catalogId = []
                        this.catalogSelected = null
                        listCatalogForTree().then(response => {
                            this.catalogOptions = this.handleTree(response.data, "id")
                        });
                        break;
                    case 2:
                        this.queryParams.attCode = ""
                        break;
                    case 3:
                        this.queryParams.createTime = ""
                        break;
                }
            },
            handleAttCodeSearch(){
                this.queryParams.attCode = this.attCode
            },
            handleAttCodeInput(input){
                if(input !== null && input !== ""){
                    this.showBtnAttCodeSearch = true;
                }else {
                    this.showBtnAttCodeSearch = false;
                }
            },
            handleCreateTimeChanged(daterange) {
                const start = moment(daterange[0]).format('YYYY-MM-DD')
                const end = moment(daterange[1]).format('YYYY-MM-DD')
                this.queryParams.createTime = start +" ~ "+ end
            },
            filterNode(value, row) {
                if (!value) return true
                return row.label.indexOf(value) > -1
            },
            submitCatalog(){
                if(!this.form.catalogId){
                    this.$message.error("请选择档案类型");
                    return false;
                }
                this.$router.push({
                    name: 'attachment-add',
                    params:{
                        catalogId: this.form.catalogId
                    }
                })
            },
            handleCatalogClick(id){
                this.$router.push({
                    name: 'attachment-add',
                    params:{
                        catalogId: id
                    }
                })
            },
            handleNodeClick(node){
                this.form.catalogId = node.id
            },
            handleTabsClick(tab, event){
                if(tab.index == 1){
                    treeselect().then(response => {
                        this.catalogTreeSelect = response.data
                    })
                }else if(tab.index == 2){
                    this.getListTemp();
                }
            },
            handleAddButtonCatalog(data){
                const listButton = this.buttonCatalogs
                const exist = listButton.some(item => item.id === data.id);
                if(!exist){
                    this.buttonCatalogs.push(data)
                    this.$cache.local.set("catalog_used", JSON.stringify(this.buttonCatalogs));
                }
            },
            handleRemoveButtonCatalog(node,data){
                const listButton = this.buttonCatalogs
                const index = listButton.findIndex(item => item.id === data.id);
                console.log(index)
                if(index !== -1){
                    this.buttonCatalogs.splice(index,1)
                    this.$cache.local.set("catalog_used", JSON.stringify(this.buttonCatalogs));
                }
            },
            handleNewVersion(row){
                const ids = row.id || this.ids;
                this.$router.push({
                    name: 'attachment-newversion',
                    params: {
                        id: ids
                    }
                })
            }
        }
    };
    </script>
    
    <style lang="scss">
    .preview-ul li {
        padding: 5px 10px;
        cursor: pointer;
        font-size: 15px;
        font-weight: 700;
    }
    
    .preview-ul li:hover {
        color: #409eff;
    }
    
    .el-dialog__body {
        height: 90%;
    }

    .search-toolbar {
        display: flex;
        justify-content: start;
        align-items: center;
        margin-bottom: 10px;
        .toolbar-label {
            margin-right: 30px;
            width: 120px;
        }
    }

    .dropdown-list{
        margin-right: 30px;
    }
    .el-card__body{
        padding: 10px;
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

    .input-with-select {
        margin-bottom: 15px;
    }

    .el-descriptions-row th{
        width: 10%;
        text-align: center !important;
    }

    .footer {
        position: fixed;
        bottom: 30px;
        left: 40%;
    }
    .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
        background-color: #d3f0f1;
    }

    .button-catalog {
        // background:#f2feff url(../../../assets/kms/images/folder-bg.png) no-repeat 50% 0px;
        background-image: url(../../../assets/kms/images/folder-bg.png);
        width: 210px;
        height: 114px;
        background-repeat: no-repeat;
        background-size: cover;
        margin: 0 30px;
    }

    .custom-tree-node {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 14px;
        padding-right: 8px;
    }

    .btn-action {
        padding-right: 10px !important;
        padding-left: 10px !important;
    }

    .custom-tag {
        height: 100%;
        background-color: #fff;
        width: 300px;
    }
</style>
    