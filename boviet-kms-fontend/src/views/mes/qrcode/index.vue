<template>
  <div class="app-container">
      <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px" label-position="top">
          <el-form-item label="卡号" prop="carrier">
              <el-input v-model="formData.carrier" placeholder="请输入卡号" clearable :style="{width: '100%'}">
              </el-input>
          </el-form-item>
          <el-form-item label="工序" prop="operation">
              <el-select v-model="formData.operation" placeholder="请选择工序" clearable :style="{width: '100%'}">
                  <el-option v-for="(item, index) in operationOptions" :key="index" :label="item.label" :value="item.value" :disabled="item.disabled"></el-option>
              </el-select>
          </el-form-item>
          <el-form-item label="操作" prop="operator">
              <el-row>
                  <el-col :span="24" align="center">
                      <el-radio-group v-model="formData.operator" size="medium">
                          <el-radio v-for="(item, index) in operatorOptions" :key="index" :label="item.value" :disabled="item.disabled" border>
                              {{item.label}}
                          </el-radio>
                      </el-radio-group>
                  </el-col>
              </el-row>
          </el-form-item>
          <el-form-item label=" 人工碎片  Người làm vỡ" prop="artificialDebris">
              <el-row>
                  <el-col :span="24" align="center">
                      <el-input-number v-model="formData.artificialDebris"></el-input-number>
                  </el-col>
              </el-row>
          </el-form-item>
          <el-form-item label=" 设备碎片  Máy làm vỡ" prop="eqptDebris">
              <el-row>
                  <el-col :span="24" align="center">
                      <el-input-number v-model="formData.eqptDebris"></el-input-number>
                  </el-col>
              </el-row>
          </el-form-item>
          <el-form-item label=" 不良数量  Số hàng NG" prop="defectWafer">
              <el-row>
                  <el-col :span="24" align="center">
                      <el-input-number v-model="formData.defectWafer"></el-input-number>
                  </el-col>
              </el-row>
          </el-form-item>
          <el-row>
              <el-col :span="24" align="center">
                  <el-row>
                      <el-col :span="24" align="center">
                          <el-button type="primary" @click="submitForm">提交</el-button>
                          <el-button @click="resetForm">重置</el-button>
                      </el-col>
                  </el-row>
              </el-col>
          </el-row>
      </el-form>
  </div>
  </template>
  
  <script>
  export default {
      components: {},
      props: [],
      data() {
          return {
              formData: {
                  carrier: this.$route.params.content,
                  operation: 1,
                  operator: 1,
                  artificialDebris: 0,
                  eqptDebris: 0,
                  defectWafer: 0,
              },
              rules: {
                  carrier: [{
                      required: true,
                      message: '请输入卡号',
                      trigger: 'blur'
                  }],
                  operation: [{
                      required: true,
                      message: '请选择工序',
                      trigger: 'change'
                  }],
                  operator: [{
                      required: true,
                      message: '操作不能为空',
                      trigger: 'change'
                  }],
                  artificialDebris: [],
                  eqptDebris: [],
                  defectWafer: [],
              },
              operationOptions: [{
                  "label": "选项一",
                  "value": 1
              }, {
                  "label": "选项二",
                  "value": 2
              }],
              operatorOptions: [{
                  "label": "上料刷卡",
                  "value": 1
              }, {
                  "label": "下料刷卡",
                  "value": 2
              }],
          }
      },
      computed: {},
      watch: {},
      created() {},
      mounted() {},
      methods: {
          submitForm() {
              this.$refs['elForm'].validate(valid => {
                  if (!valid) return
                  // TODO 提交表单
              })
          },
          resetForm() {
              this.$refs['elForm'].resetFields()
          },
      }
  }
  </script>
  
  <style scoped>
  .app-container {
      padding: 20px;
  
  }
  </style>
  