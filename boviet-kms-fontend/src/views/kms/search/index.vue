<template>
    <section class="wrapper kms-search">
      <div class="content" style="padding-top: 60px;height: 100%;">
        
        <div class="mainwidth">
          <div class="logoimg" style=" margin-bottom: 0px;">
            <div
              style="float:left;margin-left:30%;margin-right:0px; font-size: 64px;color:#ec6c00; font-weight: bold; line-height: 60px;">
              <a href="/index" target="_balnk" style="text-decoration:none;color:#ec6c00;">Boviet</a>
            </div>
            <div style="float:left;color:#ec6c00; margin-left: 20px;">
              <div
                style="font-size: 25px;color:#ec6c00; font-weight: bold; margin-left: 30px; line-height: 25px;margin-top: 10px; ">
                KMS 知 识 平 台 检 索
              </div>
              <div style="color:#ec6c00;font-style:oblique;  margin-top: 0px; padding-top: 0px;">
                Total branch retrieval knowledge platform
              </div>
            </div>
            <div style="clear: both;"></div>
          </div>
          <!-- logo  end-->
  
          <!--mian    Start-->
          <div style="margin: 0 auto;width: 80%;">
            <el-form ref="form" :model="form" label-width="90px">
              <div class="literature_index">
                <div>
                  <div class="searchbox">
                    <el-form-item label="检索条件">
                      <el-col :span="6">
                        <el-select v-model="form.searchKey" placeholder="请选择" size="small">
                          <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                        </el-select>
                      </el-col>
                      <el-col :span="13">
                        <el-input v-model="form.searchValue" placeholder="请输入关键字" style="width:96%" size="small"></el-input>
                      </el-col>
                      <el-col :span="5">
                        <input value="检索" class="vipsearch" type="button" @click="searchList">
                        <input type="button" onclick="java:alert('还未建设，你有兴趣参与吗')" class="vipsearch" value="高级检索 ">
                      </el-col>
                    </el-form-item>
                  </div>
  
                  <div class="searchbox">
                    <el-row :gutter="15">
                      <el-col :span="6">
                        <el-form-item label="每页显示">
                          <el-select v-model="form.pageSize" placeholder="请选择" size="small">
                            <el-option
                              v-for="item in pageOpt"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="排序选项">
                          <el-select v-model="form.sortBy" placeholder="请选择" size="small">
                            <el-option
                              v-for="item in sortByOpt"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="排序方式">
                          <el-select v-model="form.sortType" placeholder="请选择" size="small">
                            <el-option
                              v-for="item in sortTypeOpt"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="检索方式">
                        <el-select v-model="form.searchBy" placeholder="请选择" size="small">
                          <el-option
                            v-for="item in searchByOpt"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
  
                </div>
              </div>
            </el-form>
          </div>
          <list :list="mainList" v-if="mainList"></list>
          </div>
          <!-- mian  end-->
        </div>
    </section>
  </template>
  
  <script>
    import { listForEs } from "@/api/kms/main";
    import "@/assets/kms/styles/style.css";
    import List from "../components/List";
  
    export default {
      name: "Search",
      components: {List},
      data(){
        return {
          radius: 140,
          d: 360,
          dtr: Math.PI / 180,
          sa: 0,
          ca: 0,
          sb: 0,
          cb: 0,
          sc: 0,
          cc: 0,
          mcList: [],
          lasta: 1,
          lastb: 1,
          distr: true,
          tspeed: 8,
          size: 250,
          mouseX: 0,
          mouseY: 10,
          howElliptical: 1,
  
          tagList: [],
          oList: null,
          oA: null,
  
          form:{
            searchKey:'title',
            searchValue:'',
            pageSize:10,
            sortBy:'createTime',
            sortType:1,
            searchBy:2
          },
          searchKey:'',
          options: [
            {
              value: 'title',
              label: '知识标题'
            },
            {
              value: 'mainContent',
              label: '正文'
            },
            {
              value: 'author',
              label: '作者'
            }
          ],
          pageOpt:[
            {
              value: 10,
              label: '10'
            },
            {
              value: 20,
              label: '20'
            },
            {
              value: 30,
              label: '30'
            },
            {
              value: 50,
              label: '50'
            }
          ],
          sortByOpt:[
            {
              value: 'publishDate',
              label: '发布时间'
            },
            {
              value: 'createTime',
              label: '创建时间'
            },
          ],
          sortTypeOpt: [
            {
              value: 1,
              label: '降序排列'
            },
            {
              value: 2,
              label: '升序排列'
            }
          ],
          searchByOpt: [
            {
              value: 1,
              label: '精确查询'
            },
            {
              value: 2,
              label: '模糊匹配'
            }
          ],
          mainList:[],
          total :0,
          loading:false
        }
      },
      created() {
        this.$nextTick(() => {
          this.query();
        })
      },
      methods:{
        // 生成标签云
        onReady() {
        },
        // 网络请求 拿到tagList
        query() {
          // 假装从接口拿回来的数据
          let tagListOrg = [];
          this.tagList = tagListOrg.slice(0,50);
          this.onReady();
        },
  
        /** 查询知识列表 */
        searchList() {
          this.loading = true;
          listForEs(this.form).then(response => {
            console.log(response)
            this.mainList = response.rows;
            this.total = response.total;
            this.loading = false;
          });
        },
      }
    }
  </script>
  
  <style scoped>
    /* 机器人样式 */
    .iyunwen_js_class {
      top: -100px;
      position: fixed !important;
    }
  
    .MoveRight {
      width: 766px;
      float: left;
    }
  
    .title1 em {
      font-size: 16px;
      color: #1f95df;
      border-bottom: solid 2px #EAEAEA;
      text-align: left;
    }
  
    .floatTxt {
      position: relative;
      font-size: 16px;
      margin-top: 4px;
      height: 100px;
      margin-bottom: 20px;
    }
  
    .floatTxt a {
      display: inline-block;
      color: #333;
      position: absolute;
      z-index: 1;
    }
  
    .floatTxt a:hover {
      color: #000
    }
  
    .floatTxtNoSupport {
      position: relative;
      font-size: 16px;
      margin-top: 15px;
      height: 180px;
    }
  
    .floatTxtNoSupport a {
      display: inline-block;
      color: #222;
    }
  
    /* tagscloud */
    #tagscloud {
      width: 500px;
      height: 260px;
      position: relative;
      font-size: 14px;
      color: #333;
      margin: 20px auto 0;
      text-align: center;
    }
  
    #tagscloud a {
      position: absolute;
      top: 0px;
      left: 0px;
      color: #333;
      font-family: Arial;
      text-decoration: none;
      margin: 0 10px 15px 0;
      line-height: 22px;
      text-align: center;
      font-size: 14px;
      padding: 1px 5px;
      display: inline-block;
      border-radius: 3px;
    }
  
    #tagscloud a:hover {
      background: #fff;
      border: 1px solid #1f95df;
    }
  
    .literature_index {
      width: 100%;
    }
  
    .literature_index .searchbox .label, .literature_index .radiobox .label, .literature_index .time_publish .label, .literature_index .sortleft .label {
      display: inline-block;
      margin: 0 10px 0 0;
      width: 20%;
      text-align: right;
    }
  
    .literature_index .label_1 {
      width: 8% !important;
    }
  
    .searchbox .input {
      height: 30px;
      line-height: 30px;
      color: #333;
      border: 1px solid #809ec5;
      background: #f5fdff;
      font-size: 12px;
      width: 30%;
      padding: 0 15px;
      margin: 0 10px;
    }
  
    .index_showinfo {
      margin-left: 15%;
    }
  
    .content {
      padding-bottom: 0;
    }
  
    .footer {
      margin-top: 20px;
    }
  
  
    .tagcloud-all {
      position: relative;
    }
  
    .tagcloud-all a {
      position: absolute;
      top: 0px;
      left: 0px;
      color: #fff;
      font-weight: bold;
      text-decoration: none;
      padding: 3px 6px;
    }
  
  </style>
  
  <style scoped lang="scss">
    .searchbox {
      ::v-deep .el-input--small {
        .el-input__inner {
          border: 1px solid #809ec5;
          background:#f5fdff;
        }
      }
    }
  </style>
  