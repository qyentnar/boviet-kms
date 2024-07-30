<template>
    <div class="activiti-approve-main">
        <div class="main-title">流程处理</div>
        <!--<div class="title">
            <i></i>
            <span>流程处理</span>
        </div>-->
        <div>
          <div class="activiti-approve-title">审批记录</div>
          <el-table
            border class="org-manage-user-table" :data="historyData"
            :header-cell-style="{background:'#F4F7FA',color:'#909399',fontWeight: 'bold'}">
            <el-table-column
              prop="fdEndTime"
              label="时间"
              width="170"
              align="center">
              <template slot-scope="scope">
                <span>{{scope.row.fdEndTime | timeFilter}}</span>
              </template>
            </el-table-column>
            <el-table-column
              prop="fdActivityName"
              label="节点名称"
              width="150"
              align="center" />
            <el-table-column
              prop="fdAssignee"
              label="操作者"
              width="150"
              align="center" />
            <!--<el-table-column
              prop="opertate"
              label="操作"
              width="150">
            </el-table-column>-->
            <el-table-column
              prop="comments"
              label="处理意见"
              width="430"
              align="center" />
          </el-table>
        </div>
        <div class="approve-panel" v-if="isCurrentAssignee && isAssignee">
            <el-form ref="form" :rules="rules" label-position="left" :model="form" label-width="110px" class="activiti-approve-form">
                <el-form-item label="当前节点">
                    <el-col :span="11">
                        {{currentNode}}
                    </el-col>
                </el-form-item>
                <el-form-item label="操作" prop="handle">
                    <el-radio-group v-model="form.handle" style="padding-left: 0px;">
                        <el-radio label="通过"></el-radio>
                        <el-radio label="驳回"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="驳回到" style="margin-bottom: 20px" v-if="refuseTo" prop="nodeValue">
                    <el-select v-model="form.nodeValue" placeholder="请选择" size="small">
                        <el-option
                                v-for="(item,index) in rejectApprovalNode"
                                :key="index"
                                :label="item.approvalNodeName"
                                :value="item.approvalNodeId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="处理意见" prop="desc">
                    <el-col :span="18" class="activiti-desc-height">
                    <el-input type="textarea" v-model="form.desc"></el-input>
                    </el-col>
                    <el-col :span="2" class="activiti-desc-height flex-v-end">
                        <el-button type="primary" size="small" @click="onSubmit">提交</el-button>
                    </el-col>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    name: "activiti-approve",
    components: {},
    props:{
        type:String,   //查看页面才可以做审批动作
        state:String,
        processInstanceId:String,
        isAssignee:Boolean,
        list:{
          type:Array,
          default:function(){return []}
        },
        rejectApprovalNode:{
          type:Array,
          default:function(){return []}
        },
        historyData:{
          type:Array,
          default:function(){return []}
        },
    },
    data() {
      return {
        disabled:false,
        activitData: [],
        currentNode: '',
        currentAssigneeId:'',
        isCurrentAssignee:false,
        form: {
          handle: '通过',
          desc: '同意',
          nodeValue: '',
        },
        rules: {
          handle:[
            {required: true, message: '请选择操作', trigger: 'change'}
          ],
          desc: [
            {required: true, message: '请输入处理意见', trigger: 'blur'},
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
          nodeValue:[
            {required: true, message: '请选择驳回到的节点', trigger: 'change'}
          ]
        },
        refuseNodes: [{
          value: '1',
          label: '审批节点1'
        }, {
          value: '2',
          label: '审批节点2'
        }],
        refuseTo:false
      }
    },
    computed: {
      ...mapGetters(['name'])
    },
    watch:{
      list:{
        handler() {
          this.activitData = this.list
        },
        deep:true
      },
      'form.handle'(n,o){
        if(n==='驳回'){
          this.refuseTo = true
          this.form.desc = ''
        }else{
          this.refuseTo = false
          this.form.desc = '同意'
          this.form.nodeValue = ''
        }
      },
      activitData:{
        handler() {
          //查找当前节点
          if (this.activitData.length > 0) {
            for (let i = 0; i < this.activitData.length > 0; i++) {
              let item = this.activitData[0];
              this.currentNode = item.taskName
              if (item.assignee === this.name) {
                this.isCurrentAssignee = true
              }else{
                this.isCurrentAssignee = false
              }
            }
          }
        },
        deep:true
      },
      refuseTo() {
        if (this.rejectApprovalNode.length > 0 && this.refuseTo) {
          this.form.nodeValue = this.rejectApprovalNode[0].approvalNodeId
        }
      }
    },
    created() {
      this.activitData = this.list
    },
    methods: {
      onSubmit() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.disabled = true
            this.submitActiviti();
          }
        });
      },
      // 提交审批
      submitActiviti () {
        this.$emit("submit",this.form.desc,this.form.nodeValue)
      }
    }
  }
</script>

<style scoped lang="scss">
  $--color-primary:#1890ff;

    .activiti-approve-main{
      background: #FFFFFF;
      -webkit-box-shadow: 0px 2px 18px 1px rgb(0 0 0 / 8%);
      box-shadow: 0px 2px 18px 1px rgb(0 0 0 / 8%);
      border-radius: 4px 4px 4px 4px;
      margin: 15px auto;
      width: 90%;
      padding: 20px;

        .main-title{
            color:$--color-primary;
            font-size: 16px;
            padding: 10px 0px 15px;
        }

        .title {
            display: flex;
            align-items: center;
            margin-bottom: 20px;

            > i {
                width: 4px;
                height: 14px;
                background-color: $--color-primary;
                display: block;
                margin-right: 8px;
            }

            > span {
                color: #303133;
                height: 22px;
                line-height: 22px;
                font-weight: bold;
                font-size: 16px;
            }
        }

        .activiti-approve-title{
            color:#606266;
            font-size: 14px;
            font-weight: 500;
            margin-bottom: 8px;
            margin-top: 20px;
        }

        .approve-panel {
            width: 95%;
            margin: 20px auto 0px;
        }

        ::v-deep .el-table thead.has-gutter th{
            color:#606266;
            font-weight: 400;
            background: #F4F7FA;
        }
        ::v-deep .el-form{
            margin-top:20px;
            margin-bottom:20px;

            .el-form-item {
                margin-bottom: 5px;

                .el-form-item__label{
                    color:#606266;
                    font-weight: 400;
                }
                .activiti-desc-height{
                    height:90px;
                }
                .el-textarea__inner{
                    height:90px!important;
                }
                .flex-v-end{
                    display:flex;
                    align-items: flex-end;
                    margin-left: 20px;
                }

            }

        }
    }

    .theme-color{
        color: $--color-primary;
    }
</style>
