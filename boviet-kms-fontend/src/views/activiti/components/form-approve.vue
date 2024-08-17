<template>
<div class="activiti-approve-main">
    <el-tabs v-model="activeName">
        <el-tab-pane label="任务" name="task" v-if="isCurrentAssignee">
            <el-form ref="form" :rules="rules" label-position="left" :model="form" label-width="110px" class="activiti-approve-form">
                <el-form-item label="当前节点">
                    <el-col :span="11">
                        {{currentNode}}
                    </el-col>
                </el-form-item>
                <el-form-item label="操作" prop="handle">
                    <el-radio-group v-model="form.handle" style="padding-left: 0px;">
                        <el-radio label="通过" name=""></el-radio>
                        <el-radio label="驳回"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <!-- <el-form-item label="驳回到" style="margin-bottom: 20px" v-if="refuseTo" prop="nodeValue">
                    <el-select v-model="form.nodeValue" placeholder="请选择" size="small">
                        <el-option v-for="(item,index) in rejectApprovalNode" :key="index" :label="item.approvalNodeName" :value="item.approvalNodeId">
                        </el-option>
                    </el-select>
                </el-form-item> -->
                <el-form-item label="处理意见" prop="desc">
                    <el-col :span="18" class="activiti-desc-height">
                        <el-input type="textarea" v-model="form.desc"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
        </el-tab-pane>
        <el-tab-pane label="审批记录" name="history">
            <div class="block">
                <el-timeline v-for="history in historyData" :key="history.activityName" :reverse="true">
                    <el-timeline-item :timestamp="history.endTime" placement="top" v-if="history.completed">
                        <el-card>
                            <el-tag type="success">
                                {{history.activityName}}
                            </el-tag>
                            <div style="margin-left: 20px;">
                                <p>Assignee: {{history.assignee}}</p>
                                <p>Comment: {{history.comments}}</p>
                            </div>
                        </el-card>
                    </el-timeline-item>
                </el-timeline>
            </div>
        </el-tab-pane>
        <el-tab-pane label="任务图" name="process-map">
            <process-viewer :processData="processData" />
        </el-tab-pane>
        <el-tab-pane label="版本记录" name="list-version">
                <el-row type="flex" justify="center" v-for="item in versionList" :key="item.id">
                  <el-col :span="12">
                    <el-alert class="version" 
                        :title="`${item.title} - v${item.version}`" 
                        type="info" 
                        :description="item.createTime" 
                        :closable="false" 
                        show-icon effect="dark"/>
                  </el-col>
                </el-row>
        </el-tab-pane>
    </el-tabs>
</div>
</template>

<script>
import {
    mapGetters
} from 'vuex'
import {
    completeTask
} from '@/api/activiti/template';

import ProcessViewer from './process-viewer';

export default {
    name: "form-approve",
    components: {
        completeTask,
        ProcessViewer,
    },
    props: {
        processInstance: {
            type: Object,
            default: function () {
                return {}
            }
        },
        list: {
            type: Array,
            default: function () {
                return []
            }
        },
        rejectApprovalNode: {
            type: Array,
            default: function () {
                return []
            }
        },
        historyData: {
            type: Array,
            default: function () {
                return []
            }
        },
        versionList: {
            type: Array,
            default: function () {
                return []
            }
        },
        processData: {
            type: Object,
            default: function () {
                return {}
            }
        },
    },
    data() {
        return {
            loading: true,
            activitData: [],
            currentNode: "",
            isCurrentAssignee: false,
            activeName: "history",
            form: {
                handle: '通过',
                desc: '同意',
                nodeValue: '',
            },
            rules: {
                handle: [{
                    required: true,
                    message: '请选择操作',
                    trigger: 'change'
                }],
                desc: [{
                        required: true,
                        message: '请输入处理意见',
                        trigger: 'blur'
                    },
                    {
                        required: true,
                        trigger: ['blur', 'change'],
                        validator: (rule, value, callback) => {
                            const reg = /^[0-9].*$/
                            if (value) {
                                if (value.replace(/\s*/g, '') === '') {
                                    return callback(new Error('请输入处理意见'))
                                } else {
                                    return callback()
                                }
                            }
                        }
                    }
                ],
                nodeValue: [{
                    required: true,
                    message: '请选择驳回到的节点',
                    trigger: 'change'
                }]
            },
            refuseNodes: [{
                value: '1',
                label: '审批节点1'
            }, {
                value: '2',
                label: '审批节点2'
            }],
            refuseTo: false
        }
    },
    computed: {
        ...mapGetters(['name'])
    },
    watch: {
        list: {
            handler() {
                this.activitData = this.list
            },
            deep: true
        },
        'form.handle'(n, o) {
            if (n === '驳回') {
                this.refuseTo = true
                this.form.desc = ''
            } else {
                this.refuseTo = false
                this.form.desc = '同意'
                this.form.nodeValue = ''
            }
        },
        activitData: {
            handler() {
                //查找当前节点
                if (this.activitData.length > 0) {
                        for (let i = 0; i < this.activitData.length > 0; i++) {
                            let item = this.activitData[0];
                            this.currentNode = item.taskName
                            if (item.assignee === this.name) {
                                this.isCurrentAssignee = true
                                this.activeName = "task"
                                this.setSubmit(false);
                            } else {
                                this.isCurrentAssignee = false
                                this.activeName = "history"
                                this.setSubmit(true);
                            }
                        }
                    }
            },
            deep: true
        },
        refuseTo() {
            if (this.rejectApprovalNode.length > 0 && this.refuseTo) {
                this.form.nodeValue = this.rejectApprovalNode[0].approvalNodeId
            }
        }
    },
    created() {
        this.activitData = this.list
        setTimeout(() => {
            if (this.activitData.length > 0) {
                for (let i = 0; i < this.activitData.length > 0; i++) {
                    let item = this.activitData[0];
                    this.currentNode = item.taskName
                    if (item.assignee === this.name) {
                        this.isCurrentAssignee = true
                        this.activeName = "task"
                        this.setSubmit(false);
                    } else {
                        this.isCurrentAssignee = false
                        this.activeName = "history"
                        this.setSubmit(true);
                    }
                }
            }
        }, 3000)
    },
    methods: {
        onSubmit() {
            const data = {
                processInstanceId: this.processInstance.processInstanceId,
                assignee: this.name,
                comment: this.form.desc
            }
            completeTask(data).then(response => {
                if (response.msg == undefined || response.msg == "") {
                    this.$modal.msgError(response.msg)
                } else {
                    this.$emit('task-completed');
                    this.$modal.msgSuccess("批准成功");
                }
            })
        },
        setSubmit(isCurrentAssignee) {
            this.$emit('is-submit', isCurrentAssignee);
        }
    }
}
</script>

<style lang="scss">
.version {
    margin-bottom: 10px;
}
</style>
