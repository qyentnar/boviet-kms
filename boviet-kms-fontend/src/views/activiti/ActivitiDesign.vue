<template>
  <div class="activiti-design" :style="wdStyle" v-loading="loading">
    <breadcrumb-header
      :breadcrumbList="breadcrumbList"
      background="#ffffff"
      exist-back
      :router-name="routerName"
    >
      <template slot="right">
        <el-button type="primary" size="small" @click="saveSet">
          提交
        </el-button>
      </template>
    </breadcrumb-header>
    <div class="activiti-overflow">
      <div class="form-model" v-show="first">
        <!-- <div class="title">
                    <i></i>
                    <span>流程模板新建</span>
                </div>-->
        <el-form
          ref="base"
          :model="base"
          :rules="rules"
          label-width="80px"
          class="activiti-design-form"
        >
          <el-form-item label="流程标题" prop="name">
            <el-input v-model="base.name"></el-input>
          </el-form-item>
          <el-form-item label="流程说明" prop="desc">
            <el-input type="textarea" v-model="base.desc"></el-input>
          </el-form-item>
        </el-form>
        <!--<el-button type="primary"
                           size="small"
                           @click="first=false">
                  去画图
                </el-button>-->
        <process-designer @change="getXML" :xmlString="processDefXml" />
      </div>

      <!--画图-->
      <!--画图-->
      <div v-show="!first">
        <!--<el-button type="primary"
                         size="small"
                         @click="first=true">
                返回上一步
              </el-button>-->
      </div>
    </div>
  </div>
</template>
<script>
import BreadcrumbHeader from "../kms/components/breadcrumb-header";
import processDesigner from "./components/process-designer";
import { addTemplate, updateTemplate } from "../../api/activiti/template";
import { deploy, startProcess, add } from "@/api/activiti/index";
import { getTemplate } from "@/api/activiti/template";
import processViewer from './components/process-viewer'

export default {
  name: "ActivitiDesign",
  props: {
    library: String, //查看页面才可以做审批动作
    routerName: String,
  },
  components: {
    BreadcrumbHeader,
    processDesigner,
    processViewer,
  },
  data() {
    return {
      base: {
        name: "",
        desc: "",
      },
      tipList: [],
      tipVisible: false,
      nowVal: 100,
      processConfig: {},
      nodeConfig: {},
      workFlowDef: {},
      flowPermission: [],
      directorMaxLevel: 0,
      breadcrumbList: [
        {
          name: "流程模板设置",
        },
      ],
      rules: {
        name: [
          { required: true, message: "请输入流程名称", trigger: "blur" },
          /*,{min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur'}*/
        ],
        desc: [{ required: true, message: "请输入流程说明", trigger: "blur" }],
      },
      eventConfig: {},
      id: this.$route.query.id,
      set: this.$route.query.set,
      loading: false,
      first: true,
      processDefXml: "",
      flowData: {},
    };
  },
  computed: {
    wdStyle() {
      return "width: calc(100vw - " + this.toggleNavWd + "px);";
    },
  },
  created() {
    this.getTemplate();
  },
  methods: {
    getTemplate() {
      const templateId = this.$route.params && this.$route.params.templateId;
      if (templateId) {
        getTemplate(templateId).then((res) => {
          const data = res.data;
          this.id = data.id;
          this.base.name = data.name;
          this.base.desc = data.description;
          this.processDefXml = data.processDefXml;
        }).catch((err) => {console.error(err);});
      }
    },
    getXML(xml) {
      this.processDefXml = xml;
    },
    initData() {
      sysAjax(
        { vm: this, path: { id: this.id } },
        apiSysActiviti.findDetail
      ).then((res) => {
        if (res?.message) {
          this.$message.error(res.message);
        } else {
          this.base.name = res.name;
          this.base.desc = res.description;
          this.processConfig = JSON.parse(res.processDefXml);
          let { nodeConfig, flowPermission, directorMaxLevel, workFlowDef } =
            this.processConfig;
          this.nodeConfig = nodeConfig;
          this.flowPermission = flowPermission;
          this.directorMaxLevel = directorMaxLevel;
          this.workFlowDef = workFlowDef;
        }
      });
    },
    saveSet() {
      this.loading = true;
      this.$refs["base"].validate((valid) => {
        if (valid) {
          if (!this.processDefXml) {
            alert("流程图为空");
            return;
          }
          const data = {
            name: this.base.name,
            description: this.base.desc,
            processDefXml: this.processDefXml,
          };
          console.log(data);
          if (this.id) {
            data.id = this.id;
            updateTemplate(data).then((response) => {
              this.$modal.msgSuccess("编辑成功");
              this.$router.push({
                path: "/activiti/activiti",
              });
            });
          } else {
            addTemplate(data).then((response) => {
              this.$modal.msgSuccess("提交成功");
              this.$router.push({
                path: "/activiti/activiti",
              });
            });
          }
          this.loading = false;
        } else {
          this.loading = false;
          return false;
        }
      });
    },
    zoomSize(type) {
      if (type == 1) {
        if (this.nowVal == 50) {
          return;
        }
        this.nowVal -= 10;
      } else {
        if (this.nowVal == 300) {
          return;
        }
        this.nowVal += 10;
      }
    },
  },
};
</script>
<style scoped lang="scss">
$--color-primary: #1890ff;

.error-modal-list {
  width: 455px;
}

.activiti-design {
  // width: calc(100vw - 200px);  //toggleNavWd动态设置宽度
  /*width: 100%;*/
  height: 100%;

  .activiti-overflow {
    padding: 16px 40px 40px;
    //overflow: overlay; // 滚动条不占空间的样式，保留，勿删
    overflow: scroll;
    height: calc(100% - 35px);
    width: 100%;

    .form-model {
      background-color: #fff;
      border-radius: 4px;
      padding: 10px 0 10px 24px;
      width: 100%;
      height: 900px;

      .title {
        display: flex;
        align-items: center;
        margin-bottom: 20px;
        margin-right: 50px;

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

      .activiti-design-form {
        width: 95%;
      }
    }

    .fd-nav-content {
      width: 100%;

      .dingflow-design {
        overflow: scroll;
        width: 100%;
      }
    }
  }
}
</style>
