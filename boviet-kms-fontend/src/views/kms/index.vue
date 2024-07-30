<template>
<div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="知识标题" prop="title">
            <el-input v-model="queryParams.title" placeholder="请输入知识标题" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="状态" prop="state">
            <el-select v-model="queryParams.state" placeholder="知识状态" clearable style="width: 240px">
                <el-option v-for="dict in dict.type.kms_main_state" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
        </el-form-item>
        <el-form-item label="创建者" prop="createBy">
            <el-input v-model="queryParams.createBy" placeholder="请输入创建者" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['kms:main:add']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['kms:main:edit']">修改</el-button>
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
        <el-table-column label="主键ID" align="center" prop="id" width="60" />
        <el-table-column label="知识标题" align="center" prop="title" />
        <el-table-column label="作者" align="center" prop="author" width="130">
            <template slot-scope="scope">
                <span>{{scope.row.extAuthor.join('、')}}</span>
            </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="state" width="60">
            <template slot-scope="scope">
                <span>{{scope.row.state | stateFilter}}</span>
            </template>
        </el-table-column>
        <el-table-column label="版本" align="center" prop="version" width="50">
            <template slot-scope="scope">
                <span>{{scope.row.version | versionFilter}}</span>
            </template>
        </el-table-column>
        <!--<el-table-column label="所属目录" align="center" prop="catalogId" />-->
        <!-- <el-table-column label="所属部门" align="center" prop="deptId" />-->
        <!--<el-table-column label="知识正文" align="center" prop="mainContent" />-->
        <!--<el-table-column label="知识摘要" align="center" prop="summary" />
      <el-table-column label="关键字" align="center" prop="keyword" />
      <el-table-column label="知识封面" align="center" prop="coverImg" />-->
        <!--<el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="发布时间" align="center" prop="publishDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="阅读量" align="center" prop="readCount" />-->
        <el-table-column label="创建者" align="center" prop="createBy" width="80" />
        <el-table-column label="创建时间" align="center" prop="createTime" />
        <!--<el-table-column label="更新人" align="center" prop="updateBy" />-->
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
                <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['kms:main:edit']" v-if="!scope.row.processInstanceId">编辑</el-button>
                <el-button size="mini" type="text" icon="el-icon-position" @click="handleStartProcess(scope.row)" v-hasPermi="['kms:main:start']" v-if="!scope.row.processInstanceId">发布</el-button>
                <!-- <el-button size="mini" type="text" icon="el-icon-edit" @click="handleNewVersion(scope.row)" v-hasPermi="['kms:main:edit']" v-if="scope.row.state==='30'">新版本</el-button> -->
                <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['kms:main:remove']" v-if="!scope.row.processInstanceId">删除</el-button>
                <el-button size="mini" type="text" icon="el-icon-preview" @click="handlePreviewAttFile(scope.row)" v-if="scope.row.fileName!=null">预览附件</el-button>
                <!-- <el-button size="mini" type="text" icon="el-icon-preview" @click="handleView(scope.row)">查看详情</el-button> -->
            </template>
        </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改知识对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body fullscreen>
        <el-row style="height: 85%;">
            <el-col :span="24">
                <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                    <el-steps :space="200" :active="active" simple style="margin-bottom: 30px;">
                        <el-step title="Step 1" icon="el-icon-edit" />
                        <el-step title="Step 2" icon="el-icon-upload" />
                        <el-step title="Step 3" icon="el-icon-picture" />
                    </el-steps>
                    <div class="step1" v-if="active == 1">
                        <el-form-item label="知识标题" prop="title">
                            <el-input v-model="form.title" placeholder="请输入知识标题" />
                        </el-form-item>
                        <el-form-item label="所属目录" prop="catalogId">
                            <treeselect v-model="form.catalogId" :options="catalogOptions" :normalizer="normalizer" placeholder="选择目录" @select="handleTreeSelect"/>
                        </el-form-item>
                        <el-form-item label="作者" prop="author">
                            <input-tag :data.sync="form.author.external" placeholder="输入作者名称，点击enter键添加" :check="form.author.check" :check-text="form.author.checkText"></input-tag>
                        </el-form-item>
                        <el-form-item label="知识摘要" prop="summary">
                            <el-input v-model="form.summary" type="textarea" placeholder="请输入内容" />
                        </el-form-item>
                        <el-form-item label="知识正文">
                            <editor v-model="form.mainContent" :min-height="192" />
                        </el-form-item>
                    </div>
                    <div class="step2" v-if="active == 2">
                        <el-row>
                            <el-col :span="24" align="center">
                                <el-form-item>
                                    <file-upload @input="getFileList" :drag="true" :directory="directory" :value="form.filePath"></file-upload>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="step3" v-if="active == 3">
                        <el-row type="flex" justify="center">
                            <el-col :span="12" :xs="24">
                                <el-descriptions :column="1" border>
                                    <el-descriptions-item label="知识标题"> {{form.title}} </el-descriptions-item>
                                    <el-descriptions-item label="所属目录">
                                        <div v-for="item in catalogOptions" :key="item.id">
                                            <div v-if="item.id == form.catalogId">
                                                {{item.title}}
                                            </div>
                                        </div>
                                    </el-descriptions-item>
                                    <el-descriptions-item label="作者">
                                        <el-tag type="plain" v-for="item in form.author.external" :key="item" style="margin-left: 10px;">
                                            {{item}}
                                        </el-tag>
                                    </el-descriptions-item>
                                    <el-descriptions-item label="知识摘要"> {{form.summary}} </el-descriptions-item>
                                    <el-descriptions-item label="知识正文"> {{form.mainContent}} </el-descriptions-item>
                                    <el-descriptions-item label="上传附件">
                                        <div v-if="form.fileName">
                                            <el-tag type="plain" v-for="item in form.fileName.split(',')" :key="item">
                                                {{item}}
                                            </el-tag>
                                        </div>
                                    </el-descriptions-item>
                                </el-descriptions>
                            </el-col>
                        </el-row>
                    </div>
                </el-form>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" align="center">
                <el-button @click="prev" icon="el-icon-arrow-left" :disabled="active == 1">Previous</el-button>
                <el-button @click="next" icon="el-icon-arrow-right" :disabled="active == 3">Next</el-button>
                <el-button type="primary" @click="submitForm(1)" :disabled="active < 3 || loading">提交</el-button>
                <el-button @click="submitForm(0)" v-if="form.state!=30" :disabled="active < 3">暂存</el-button>
                <el-button type="danger" @click="cancel">取 消</el-button>
            </el-col>
        </el-row>
    </el-dialog>

    <!-- 附件预览对话框 -->
    <el-dialog :title="titlePreview" :visible.sync="openPreview" width="960px" append-to-body>
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

    <!-- 新版本-添加或修改知识对话框 -->
    <el-dialog :title="title" :visible.sync="openNew" width="800px" append-to-body fullscreen>
        <el-row style="height: 85%;">
            <el-col :span="24">
                <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                    <el-steps :space="200" :active="active" simple style="margin-bottom: 30px;">
                        <el-step title="Step 1" icon="el-icon-edit" />
                        <el-step title="Step 2" icon="el-icon-upload" />
                        <el-step title="Step 3" icon="el-icon-picture" />
                    </el-steps>
                    <div class="step1" v-if="active == 1">
                        <el-form-item label="知识标题" prop="title">
                            <el-input v-model="form.title" placeholder="请输入知识标题" />
                        </el-form-item>
                        <el-form-item label="所属目录" prop="catalogId">
                            <treeselect v-model="form.catalogId" :options="catalogOptions" :normalizer="normalizer" placeholder="选择目录" />
                        </el-form-item>
                        <el-form-item label="作者" prop="author">
                            <input-tag :data.sync="form.author.external" placeholder="输入作者名称，点击enter键添加" :check="form.author.check" :check-text="form.author.checkText"></input-tag>
                        </el-form-item>
                        <el-form-item label="知识摘要" prop="summary">
                            <el-input v-model="form.summary" type="textarea" placeholder="请输入内容" />
                        </el-form-item>
                        <el-form-item label="知识正文">
                            <editor v-model="form.mainContent" :min-height="192" />
                        </el-form-item>
                    </div>
                    <div class="step2" v-if="active == 2">
                        <el-row>
                            <el-col :span="24" align="center">
                                <el-form-item>
                                    <file-upload @input="getFileList" :drag="true" :value="form.filePath"></file-upload>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="step3" v-if="active == 3">
                        <el-row type="flex" justify="center">
                            <el-col :span="12" :xs="24">
                                <el-descriptions :column="1" border>
                                    <el-descriptions-item label="知识标题"> {{form.title}} </el-descriptions-item>
                                    <el-descriptions-item label="所属目录">
                                        <div v-for="item in catalogOptions" :key="item.id">
                                            <div v-if="item.id == form.catalogId">
                                                {{item.title}}
                                            </div>
                                        </div>
                                    </el-descriptions-item>
                                    <el-descriptions-item label="作者">
                                        <el-tag type="plain" v-for="item in form.author.external" :key="item" style="margin-left: 10px;">
                                            {{item}}
                                        </el-tag>
                                    </el-descriptions-item>
                                    <el-descriptions-item label="知识摘要"> {{form.summary}} </el-descriptions-item>
                                    <el-descriptions-item label="知识正文"> {{form.mainContent}} </el-descriptions-item>
                                    <el-descriptions-item label="上传附件">
                                        <div v-if="form.fileName">
                                            <el-tag type="plain" v-for="item in form.fileName.split(',')" :key="item">
                                                {{item}}
                                            </el-tag>
                                        </div>
                                    </el-descriptions-item>
                                </el-descriptions>
                            </el-col>
                        </el-row>
                    </div>
                </el-form>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" align="center">
                <el-button @click="prev" icon="el-icon-arrow-left" :disabled="active == 1">Previous</el-button>
                <el-button @click="next" icon="el-icon-arrow-right" :disabled="active == 3">Next</el-button>
                <el-button type="primary" @click="submitForm(3)">提交</el-button>
                <el-button @click="submitForm(2)">暂存</el-button>
                <el-button type="danger" @click="cancel('new')">取 消</el-button>
            </el-col>
        </el-row>
    </el-dialog>
</div>
</template>

<script>
import {
    listMain,
    getMain,
    delMain,
    addMain,
    updateMain,
    previewAttFile,
    startProcess
} from "@/api/kms/main";
import {
    listCatalogForTree,
    listCatalogExcludeChild
} from "@/api/kms/catalog";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import TyEditor from "./components/TyEditor";
import FileUpload from '@/components/FileUpload'
import InputTag from './components/form/input-tag'
import documentViewer from "./components/document-viewer.vue";

export default {
    name: "Main",
    dicts: ['kms_main_state'],
    components: {
        TyEditor,
        FileUpload,
        Treeselect,
        InputTag,
        documentViewer
    },
    data() {
        return {
            // 遮罩层
            loading: true,
            // 选中数组
            ids: [],
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 显示搜索条件
            showSearch: false,
            // 总条数
            total: 0,
            // 知识表格数据
            mainList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            openNew: false,
            active: 1,
            pdfUrl: "",
            baseUrl: process.env.VUE_APP_BASE_API,
            directory:"",
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
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
                updateUser: null,
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
            setting: {
                menubar: false,
                // 工具栏
                toolbar: 'formatselect | fontselect | fontsizeselect | bold italic underline strikethrough | bullist numlist | alignleft aligncenter alignright alignjustify | outdent indent blockquote | undo redo | image media table link | code preview',
                toolbar_drawer: 'sliding',
                quickbars_selection_toolbar: 'removeformat | bold italic underline strikethrough | fontsizeselect forecolor backcolor',
                plugins: 'link image media table lists fullscreen quickbars advlist code preview media', //选择需加载的插件 ['','']
                language_url: '/tinymce/langs/zh_CN.js', //设置中文
                language: 'zh_CN',
                height: 352,
                images_upload_url: '/demo/upimg.php',
                toolbar_mode: 'wrap', // 宽度过小时，工具不隐藏
            },
            titlePreview: '',
            // 是否显示弹出层
            openPreview: false,
            previewFile: [],
            // 目录树选项
            catalogOptions: [],
            xmlData: "",
            taskList: [{
                // 任务定义的key
                "key": "",
                // 任务是否完成
                "completed": true
            }]
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
    created() {
        this.getList();
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
        },
        // 取消按钮
        cancel(v) {
            if (v === 'new') {
                this.openNew = false;
                this.reset();
            } else {
                this.open = false;
                this.reset();
            }
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
            this.active = 1;
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
            this.title = "添加知识";
            listCatalogForTree().then(response => {
                this.catalogOptions = this.handleTree(response.data, "id");
            });
        },
        handleTreeSelect(selected){
            this.directory  = selected.title
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            const id = row.id || this.ids
            getMain(id).then(response => {
                this.form = response.data;
                this.form.author = {
                    type: 'Internal',
                    inside: [],
                    external: response.data.extAuthor,
                    check: false,
                    checkText: ''
                }
                const isNewVersion = this.form.isNewVersion
                const version = this.form.version
                if (isNewVersion === 0 && version > 1) {
                    this.openNew = true;
                    this.title = "修改知识新版本";
                } else {
                    this.open = true;
                    this.title = "修改知识";
                }
                listCatalogExcludeChild(row.id).then(response => {
                    this.catalogOptions = this.handleTree(response.data, "id");
                });
            });
        },
        /** 新版本按钮操作 */
        handleNewVersion(row) {
            this.reset();
            const id = row.id || this.ids
            getMain(id).then(response => {
                this.form = response.data;
                this.form.author = {
                    type: 'Internal',
                    inside: [],
                    external: response.data.extAuthor,
                    check: false,
                    checkText: ''
                }
                this.openNew = true;
                this.title = "创建知识新版本";
                this.form.currentVersionId = this.form.id
                console.log(this.form.currentVersionId)
                this.form.id = null
                listCatalogExcludeChild(row.id).then(response => {
                    this.catalogOptions = this.handleTree(response.data, "id");
                });
            });
        },
        next() {
            if (this.active < 3 && this.validateStep()) {
                if (this.form.id != null) {
                    this.active = 3;
                } else {
                    this.active++;
                }
            }
        },
        prev() {
            if (this.active > 1) {
                if (this.form.id != null) {
                    this.active = 1;
                } else {
                    this.active--;
                }
            }
        },
        validateStep() {
            if (this.active === 1 && !this.form.title) {
                this.$message.error('请填写知识标题')
                return false
            } else if (this.form.title.length < 5 || this.form.title.length > 30) {
                this.$message.error('长度在 5 到 30 个字符')
                return false
            }

            if (this.active === 1 && !this.form.catalogId) {
                this.$message.error('请选择所属目录')
                return false
            }
            if (this.active === 1 && this.form.author.external.length == 0) {
                this.$message.error('请选择作者')
                return false
            }
            if (this.active === 1 && !this.form.summary) {
                this.$message.error('请填写知识摘要')
                return false
            } else if (this.form.summary.length < 10 || this.form.summary.length > 100) {
                this.$message.error('长度在 10 到 100 个字符')
                return false
            }

            if (this.active === 2 && !this.form.fileName) {
                this.$message.error('请上传知识图片')
                return false
            }
            return true
        },
        /** 提交按钮 */
        submitForm(oper) {
            this.$refs["form"].validate(valid => {
                if (valid) {
                    this.form.extAuthor = this.form.author.external || []
                    this.form.state = oper === 1 || oper === 3 ? '30' : '10'
                    this.form.operation = oper
                    this.form.publishDate = null
                    this.form.updateTime = null
                    this.form.updateBy = null
                    this.form.updateUserId = null
                    if (this.form.id != null) {
                        updateMain(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.openNew = false;
                            this.getList();
                        });
                    } else {
                        addMain(this.form).then(response => {
                            this.$modal.msgSuccess("新增成功");
                            this.open = false;
                            this.openNew = false;
                            this.getList();
                        });
                    }
                }
            });
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
        //获取附件列表
        getFileList(list, names, attMainIds) {
            this.form.filePath = list
            this.form.fileName = names
            this.form.attMainIds = attMainIds
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
                    this.titlePreview = "附件预览";
                }
            });
        },
        /** 预览附件操作 new */
        handlePreviewAttFile(row) {
            this.reset();
            const id = row.id || this.ids
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
                console.log(this.previewFile);
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
        /** 查看详情操作 */
        handleView: function (row) {
            const id = row.id;
            //this.$router.push("/kms/view");
            this.$router.push({
                name: 'kms-detail',
                query: {
                    id: id
                }
            })
        },
        /** 转换目录数据结构 */
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
        handleStartProcess(row){
            startProcess(row.id).then(res => {
                this.$modal.msgSuccess("启动流程成功");
                this.getList();
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
    height: 100%;
}

.el-descriptions-row th {
    &:first-of-type {
        width: 30%;
    }
}
</style>
