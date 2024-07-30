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
        // 生成随机数
        getRandomNum() {
          return Math.floor(Math.random() * (255 + 1));
        },
        doPosition() {
          this.$nextTick(() => {            // 注意: 所有的在onReady方法中执行的方法都需要$nextTick确保所有的标签都已经渲染
            var l = this.oList.offsetWidth / 2;
            var t = this.oList.offsetHeight / 2;
            for (var i = 0; i < this.mcList.length; i++) {
              if (this.mcList[i].on) {
                continue;
              }
              var aAs = this.oA[i].style;
              if (this.mcList[i].alpha > 0.1) {
                if (aAs.display != '')
                  aAs.display = '';
              } else {
                if (aAs.display != 'none')
                  aAs.display = 'none';
                continue;
              }
              this.oA[i].style.left = this.mcList[i].cx + l - this.mcList[i].offsetWidth / 2 + 'px';
              this.oA[i].style.top = this.mcList[i].cy + t - this.mcList[i].offsetHeight / 2 + 120 + 'px';
              this.oA[i].filter = "alpha(opacity=" + 100 * this.mcList[i].alpha + ")";
              this.oA[i].zIndex = this.mcList[i].zIndex;
  
              this.oA[i].style.opacity = this.mcList[i].alpha;
            }
  
          })
        },
        update() {
  
          this.$nextTick(() => {           // 注意: 所有的在onReady方法中执行的方法都需要$nextTick确保所有的标签都已经渲染
            var a, b, c = 0;
  
            a = (Math.min(Math.max(-this.mouseY, -this.size), this.size) / this.radius) * this.tspeed;
            b = (-Math.min(Math.max(-this.mouseX, -this.size), this.size) / this.radius) * this.tspeed;
  
            this.lasta = a;
            this.lastb = b;
            if (Math.abs(a) <= 0.01 && Math.abs(b) <= 0.01) {
              return
            }
            this.sineCosine(a, b, c);
            for (var j = 0; j < this.mcList.length; j++) {
              if (this.mcList[j].on) {
                continue;
              }
              var rx1 = this.mcList[j].cx;
              var ry1 = this.mcList[j].cy * this.ca + this.mcList[j].cz * (-this.sa);
              var rz1 = this.mcList[j].cy * this.sa + this.mcList[j].cz * this.ca;
              var rx2 = rx1 * this.cb + rz1 * this.sb;
              var ry2 = ry1;
              var rz2 = rx1 * (-this.sb) + rz1 * this.cb;
              var rx3 = rx2 * this.cc + ry2 * (-this.sc);
              var ry3 = rx2 * this.sc + ry2 * this.cc;
              var rz3 = rz2;
              this.mcList[j].cx = rx3;
              this.mcList[j].cy = ry3;
              this.mcList[j].cz = rz3;
              var per = this.d / (this.d + rz3);
              this.mcList[j].x = (this.howElliptical * rx3 * per) - (this.howElliptical * 2);
              this.mcList[j].y = ry3 * per;
              this.mcList[j].scale = per;
  
              //this.mcList[j].alpha = per;
              //this.mcList[j].alpha = (this.mcList[j].alpha - 0.6) * (10 / 6);
  
              var alpha = per;
              alpha = (alpha - 0.6) * (10 / 6);
              this.mcList[j].alpha = alpha * alpha * alpha - 0.2;
              this.mcList[j].zIndex = Math.ceil(100 - Math.floor(this.mcList[j].cz));
            }
            this.doPosition();
          })
        },
        positionAll() {
  
          this.$nextTick(() => {      // 注意: 所有的在onReady方法中执行的方法都需要$nextTick确保所有的标签都已经渲染
            var phi = 0;
            var theta = 0;
            var max = this.mcList.length;
            var aTmp = [];
            var oFragment = document.createDocumentFragment();
            // 随机排序
            for (let i = 0; i < this.tagList.length; i++) {
              aTmp.push(this.oA[i]);
            }
            aTmp.sort(() => {
              return Math.random() < 0.5 ? 1 : -1;
            });
            for (let i = 0; i < aTmp.length; i++) {
              oFragment.appendChild(aTmp[i]);
            }
            this.oList.appendChild(oFragment);
  
            for (var i = 0; i < max; i++) {
              if (this.distr) {
                phi = Math.acos(-1 + (2 * (i + 1) - 1) / max);
                theta = Math.sqrt(max * Math.PI) * phi;
              } else {
                phi = Math.random() * (Math.PI);
                theta = Math.random() * (2 * Math.PI);
              }
  
              //坐标变换
              this.mcList[i].cx = this.radius * Math.cos(theta) * Math.sin(phi);
              this.mcList[i].cy = this.radius * Math.sin(theta) * Math.sin(phi);
              this.mcList[i].cz = this.radius * Math.cos(phi);
  
              this.oA[i].style.left = this.mcList[i].cx + this.oList.offsetWidth / 2
                - this.mcList[i].offsetWidth / 2 + 'px';
              this.oA[i].style.top = this.mcList[i].cy + this.oList.offsetHeight / 2
                - this.mcList[i].offsetHeight / 2 + 'px';
            }
          })
        },
        sineCosine(a, b, c) {
          this.sa = Math.sin(a * this.dtr);
          this.ca = Math.cos(a * this.dtr);
          this.sb = Math.sin(b * this.dtr);
          this.cb = Math.cos(b * this.dtr);
          this.sc = Math.sin(c * this.dtr);
          this.cc = Math.cos(c * this.dtr);
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
  
    /* #tagscloud a.tagc1{color:#666;opacity: 0.68293;  padding: 5px;}
    #tagscloud a.tagc2{color:#F16E50;opacity: 0.68293; padding: 5px;}
    #tagscloud a.tagc3{color:#83c325;opacity: 0.68293;  padding: 5px;}
    #tagscloud a.tagc4{color:#0F6098;opacity: 0.68293;  padding: 5px;}
    #tagscloud a.tagc5{color:#006633;opacity: 0.68293;  padding: 5px;} */
    #tagscloud a:hover {
      background: #fff;
      border: 1px solid #1f95df;
    }
  
    /* .label {
        display: inline-block;
        margin: 0 10px 0 0;
        min-width:60px;
        max-width:120px;
        text-align: right;
    } */
  
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
  