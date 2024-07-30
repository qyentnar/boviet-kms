<template>
  <div style="margin-top: 16px">
    <el-form-item label="处理用户">
      <!-- <el-select > 
        <el-select v-model="userTaskForm.assignee">  -->
      <!--  <el-option v-for="ak in mockData" :key="'ass-' + ak" :label="`用户${ak}`" :value="`user${ak}`" />  
        
      </el-select>-->
      <el-input
        v-model="userTaskForm.assignee"
        clearable
        disabled
        @click="updateElementTask('assignee')"
      />
      <el-button @click="updateElementTask('assignee')" type="primary"
        >选择</el-button
      >
      <el-button @click="clearAssignee('assignee')" type="primary">清空</el-button>
    </el-form-item>
    <!-- <el-form-item label="候选用户">
      <el-select v-model="userTaskForm.candidateUsers" multiple collapse-tags @change="updateElementTask('candidateUsers')">
        <el-option v-for="uk in mockData" :key="'user-' + uk" :label="`用户${uk}`" :value="`user${uk}`" />
      </el-select>
    </el-form-item>
    <el-form-item label="候选分组">

      <el-select v-model="userTaskForm.candidateGroups" multiple collapse-tags @change="updateElementTask('candidateGroups')">
        <el-option v-for="gk in mockData" :key="'ass-' + gk" :label="`分组${gk}`" :value="`group${gk}`" />
      </el-select>
    </el-form-item>
    <el-form-item label="到期时间">
      <el-input v-model="userTaskForm.dueDate" clearable @change="updateElementTask('dueDate')" />
    </el-form-item>
    <el-form-item label="跟踪时间">
      <el-input v-model="userTaskForm.followUpDate" clearable @change="updateElementTask('followUpDate')" />
    </el-form-item>
    <el-form-item label="优先级">
      <el-input v-model="userTaskForm.priority" clearable @change="updateElementTask('priority')" />
    </el-form-item> -->
    <!-- 弹出框 -->
    <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" style="margin-top: auto;">
      <el-table :data="tableData" @row-click="cellclick" style="width: 100%">
        <el-table-column prop="userName" label="用户名称" width="180" />
        <el-table-column prop="nickName" label="用户昵称">
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { listUser } from "@/api/system/user";

export default {
  name: "UserTask",
  props: {
    id: String,
    type: String,
  },
  data() {
    return {
      defaultTaskForm: {
        assignee: "",
        candidateUsers: [],
        candidateGroups: [],
        dueDate: "",
        followUpDate: "",
        priority: "",
        propertyForm: [],
      },
      checktype: "",
      dialogVisible: false,
      tableData: [],
      userTaskForm: {},
      mockData: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
    };
  },
  watch: {
    id: {
      immediate: true,
      handler() {
        this.bpmnElement = window.bpmnInstances.bpmnElement;
        this.$nextTick(() => this.resetTaskForm());
      },
    },
  },
  methods: {
    resetTaskForm() {
      for (let key in this.defaultTaskForm) {
        let value;
        if (key === "candidateUsers" || key === "candidateGroups") {
          value = this.bpmnElement?.businessObject[key]
            ? this.bpmnElement.businessObject[key].split(",")
            : [];
        } else {
          value =
            this.bpmnElement?.businessObject[key] || this.defaultTaskForm[key];
         
        }
        this.$set(this.userTaskForm, key, value);
      }
    },
    async updateElementTask(key) {
      // await axios
      //   .get("http://127.0.0.1:8090/getOrgUser?pageSize=10&pageNum=1")
      //   .then((res) => {
      //     this.tableData = res.data;
      //     console.log("设置完");
      //   });
      listUser().then((res) => {
        console.log(res.rows);
        this.tableData = res.rows;
      });
      this.checktype = key;
      this.dialogVisible = true;
      /* let taskAttr = Object.create(null);
      console.log(taskAttr)
      if (key === "candidateUsers" || key === "candidateGroups") {
        taskAttr[key] = this.userTaskForm[key] && this.userTaskForm[key].length ? this.userTaskForm[key].join() : null;
      } else {
        taskAttr[key] = this.userTaskForm[key] || null;
        console.log(taskAttr[key]);
      }
      window.bpmnInstances.modeling.updateProperties(this.bpmnElement, taskAttr); */
    },
    cellclick(row) {
      var key = this.checktype;
      let taskAttr = Object.create(null);
      this.userTaskForm.assignee = row.userName;
      this.dialogVisible = false;
      if (key === "candidateUsers" || key === "candidateGroups") {
        taskAttr[key] =
          this.userTaskForm[key] && this.userTaskForm[key].length
            ? this.userTaskForm[key].join()
            : null;
      } else {
        taskAttr[key] = this.userTaskForm[key] || null;
        console.log(taskAttr[key]);
      }
      window.bpmnInstances.modeling.updateProperties(
        this.bpmnElement,
        taskAttr
      );
    },
    clearAssignee(key) {
      let value;
      let taskAttr = Object.create(null);
      if (key === "candidateUsers" || key === "candidateGroups") {
        console.log(key)
      } else {
      taskAttr[key] = "";//this.bpmnElement?.businessObject[key] || this.defaultTaskForm[key];
      }
      this.$set(this.userTaskForm, key, "");
       window.bpmnInstances.modeling.updateProperties(
        this.bpmnElement,
        taskAttr
      );
    },
  },
  beforeDestroy() {
    this.bpmnElement = null;
  },
};
</script>
