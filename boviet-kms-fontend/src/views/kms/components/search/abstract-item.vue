<template>
      <div >
        <div>
          <el-row class="abstract-item">
              <el-col :span="12" class="abstract-item-header">
                <div class="abstract-item-title">
                    <i class="abstract-item-icon"
                      v-if="isFileF(abstract.title)"
                      :class="abstract.title|fileName" 
                      @click="openFile(abstract)"/>
                    <span v-html="abstract.title" :title="(abstract.title).replace(/<[^>]+>/g,'')" />
                    
                </div>
                
                <div class="abstract-time">发布日期：{{abstract.publishDate | timeFilter}}</div>
                <!--<div class="abstract-item-search" v-if="searchSimilar"><i class="el-icon-search"></i>找相似知识</div>-->
                <div class="abstract-item-handle" v-if="showCollect" @click.stop="openCollect(abstract)"><i
                        class="ic-shoucang1"></i>收藏
                </div>
                <div class="abstract-item-handle" @click="goInfo(abstract,from)"><i class="el-icon-view"/>View</div>
                <div class="abstract-item-handle" @click="goInfo(abstract,from)"><i class="el-icon-document"/>申请</div>
                <!-- <div class="abstract-item-handle" v-if="abstract.filePath" @click="download(abstract.filePath)"><i class="el-icon-download"></i>下载</div> -->
                <!-- <div class="abstract-item-handle" v-if="abstract.filePath">主文档</div> -->
              </el-col>
              <el-col :span="12" align="right">
                <div class="abstract-rate">
                  <el-rate v-model="rate"></el-rate>
                </div>
              </el-col>
            </el-row>
        </div>
        <div class="abstract-item-info">
            <span :title="abstract.author">作者：<i>{{abstract.author}}</i></span>
            <span v-if="abstract.type" :title="abstract.type">分类：<i>{{abstract.type}}</i></span>
        </div>
        <div class="abstract-item-desc" v-html="abstract.summary"></div>
        <div class="abstract-item-footer">
            <div class="abstract-item-tag">
                <span>标签：</span>
                <span v-for="(tag,t) of abstract.labels" :key="t" class="tag" v-html="tag"
                      :title="abstract.labels|filterTag"></span>
                <span v-if="!abstract.labels || abstract.labels.length===0" class="tag">暂无标签</span>
            </div>
            <div class="abstract-item-num">
                <span>浏览（{{abstract.readCount?abstract.readCount:0}}）</span>
                <template v-if="!searchSimilar">
                    <i></i>
                    <span @click.prevent="handleComment(abstract)">评论（{{abstract.commentCount?abstract.commentCount:0}}）</span>
                </template>
                <!--<i></i>-->
                <!--<span>下载（{{abstract.downloadCount?abstract.downloadCount:0}}）</span>-->
            </div>
        </div>
        <div class="abstract-item-comment" v-show="showComment && rowSelected == abstract.id">
          <el-collapse>
            <el-collapse-item title="Consistency" name="1">
              <div>Consistent with real life: in line with the process and logic of real life, and comply with languages and habits that the users are used to;</div>
            </el-collapse-item>
            <el-collapse-item title="Feedback" name="2">
              <div>Operation feedback: enable the users to clearly perceive their operations by style updates and interactive effects;</div>
            </el-collapse-item>
            <el-collapse-item title="Efficiency" name="3">
              <div>Simplify the process: keep operating process simple and intuitive;</div>
              <div>Definite and clear: enunciate your intentions clearly so that the users can quickly understand and make decisions;</div>
            </el-collapse-item>
            <el-collapse-item title="Controllability" name="4">
              <div>Decision making: giving advices about operations is acceptable, but do not make decisions for the users;</div>
            </el-collapse-item>
          </el-collapse>
        </div>
        <!--<div class="abstract-item-footer">
            <div class="abstract-item-tag">
                <span>关键词：</span>
                <span v-for="(tag,t) of abstract.keywords" :key="t" class="tag" v-html="tag"
                      :title="abstract.keywords|filterTag"></span>
                <span v-if="!abstract.keywords || abstract.keywords.length===0" class="tag">暂无关键词</span>
            </div>
        </div>-->
    </div>
</template>

<script>
import { previewAttFile, kmsReader } from "@/api/kms/main"

export default {
  name: 'AbstractItem',
  props: {
    abstract: Object,
    searchSimilar: { // 相似知识功能
      type: Boolean,
      default: false
    },
    checkbox: {
      type: Boolean,
      default: true
    },
    showCollect: {
      type: Boolean,
      default: false
    },
    from:String
  },
  data() {
    return {
      rate: 3,
      showComment: false,
      rowSelected: null,
    }
  },
  filters: {
    filterTag(val) {
      return val.join(' ').replace(/<[^>]+>/g, '')
    },
    fileName(file) {
      return file
    }
  },
  computed: {
    isFileF() {
      return (v) => {
        return false
      }
    }
  },
  methods: {
    goInfo(row) {
      kmsReader(row).then(res => {
        this.$router.push({
          path: '/kms/attachment/view/' + row.id
        })
      })
    },
    openCollect(data) {
      this.$emit('open-collect', data)
    },
    download(path){
      let baseUrl =  process.env.VUE_APP_BASE_API //"http://localhost:9090"  //window.origin
      let fullPath = baseUrl + path
      window.open(fullPath, '_blank')
    },
    openFile(row) {
      previewAttFile(row.id).then(res => {
        console.log("previewAttFile======:",res)
      })
    },
    handleComment(abstract){
      if(this.showComment){
        this.showComment = false
      } else {
        this.showComment = true
      }
      this.rowSelected = abstract.id
    }
    
  }
}
</script>

<style scoped lang="scss">
  $--color-primary: #1890ff;

    .abstract-item {
        /*height: 153px;*/
        //cursor: pointer;

        .abstract-item-header {
            display: flex;
            align-items: center;
            margin-bottom: 5px;

            .abstract-item-title {
                font-size: 16px;
                color: #303133;
                height: 24px;
                line-height: 24px;
                font-weight: 400;
                white-space: pre-wrap;
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 1;
                -webkit-box-orient: vertical;

                > .abstract-item-icon {
                    margin-right: 8px;
                }
            }

            .abstract-time {
                font-size: 12px;
                color: #909399;
                margin-left: 20px;

                > i {
                    font-style: normal;
                    color: #303133;
                }
            }

            .abstract-item-search {
                width: 88px;
                height: 22px;
                border: 1px solid $--color-primary;
                border-radius: 2px;
                color: $--color-primary;
                font-weight: 400;
                font-size: 12px;
                line-height: 22px;
                text-align: center;
                margin-left: 15px;
            }

            .abstract-item-handle {
                cursor: pointer;
                font-size: 12px;
                line-height: 16px;
                height: 16px;
                color: $--color-primary;
                margin-left: 30px;

                > i {
                    margin-right: 3px;
                }
            }

            .abstract-rate {
              float: right;
              margin-left: 10px;
            }

            .clearfix::after {
            content: "";
            clear: both;
            display: table;
        }
        }

        .abstract-item-info {
            margin-top: 5px;
            font-size: 12px;
            height: 15px;
            line-height: 15px;
            white-space: pre-wrap;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;

            > span {
                color: #909399;
                margin-right: 30px;

                > i {
                    font-style: normal;
                    color: #303133;
                }
            }
        }

        .abstract-item-desc {
            font-size: 14px;
            color: #606266;
            white-space: pre-wrap;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            margin-bottom: 11px;
        }

        .abstract-item-comment {
          margin-bottom: 20px;
          .el-collapse {
            background-color: #fff;
            border-radius: 20px;
            padding: 20px;
          }
          
        }

        .abstract-item-footer {
            display: flex;
            justify-content: space-between;
            font-size: 12px;
            height: 15px;
            line-height: 15px;
            color: #909399;
            margin-bottom: 19px;
            .abstract-item-tag {
                white-space: pre-wrap;
                overflow: hidden;
                text-overflow: ellipsis;

                > span.tag {
                    margin-right: 8px;
                }
            }

            .abstract-item-num {
                display: flex;
                align-items: center;

                > i {
                    display: inline-block;
                    width: 1px;
                    height: 12px;
                    background-color: #C0C4CC;
                    margin-right: 8px;
                }
                > span:last-of-type {
                  cursor: pointer;
                }
            }
        }
    }
</style>
