<template>
    <!--input下拉框可选择添加和输入添加-->
    <div class="input-tag" ref="input-tag">
        <div class="input-tag-check" :class="{'input-tag-empty':check}">
            <div class="tag-list">
                <el-tag
                        :key="tag"
                        v-for="tag in tagList"
                        closable
                        :disable-transitions="false"
                        @close="handleCloseTag(tag)"
                        class="tag-item"
                        size="small">
                    {{tag[prop.label] ? tag[prop.label] : tag}}
                </el-tag>
            </div>
            <div class="popover-body" v-clickoutside:popoverBody="hideSearchInput">
                <el-popover v-if="showAutocomplete"
                            ref="popover"
                            popper-class="tag-popover-body"
                            v-model="showSearchThink"
                            trigger="manual"
                            placement="bottom-start"
                            transition="el-zoom-in-top"
                            :append-to-body="false"
                            :popper-options="popperOptions"
                            class="popoverBody">
                    <div class="tag-popover-item" v-if="option.length>0">
                        <el-scrollbar>
                            <ul class="tag-popover-ul popoverBody">
                                <li v-for="(item,index) of option" :key="index"
                                    :title="item[prop.label] ? item[prop.label] : item"
                                    @click="searchTag(item)">{{item[prop.label] ? item[prop.label] : item}}
                                </li>
                            </ul>
                        </el-scrollbar>
                    </div>
                    <div class="tag-popover-item" v-else>
                        <span class="tag-popover-empty">暂无数据</span>
                    </div>
                </el-popover>
                <el-input v-popover:popover
                          v-model="tagInputValue"
                          class="tag-autocomplete popoverBody"
                          :placeholder="placeholder"
                          ref="tag-input"
                          @focus="querySearchAsync"
                          @input="querySearchAsync"
                          @keyup.enter.native="enterTag(tagInputValue)">
                    <i class="el-input__icon" :class="icon"
                       slot="suffix" v-if="icon">
                    </i>
                    <template slot-scope="{ item }">
                        <div class="tag-label">{{ item }}</div>
                    </template>
                </el-input>
            </div>
        </div>
        <transition name="el-zoom-in-top">
            <span v-if="check" class="error-empty">{{checkText}}</span>
        </transition>
    </div>
</template>

<script>
import Clickoutside from 'element-ui/src/utils/clickoutside'

export default {
  name: 'InputTag',
  directives: { Clickoutside },
  props: {
    data: Array,
    icon: String,
    // 是否为el-autocomplete格式
    showAutocomplete: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: '请输入'
    },
    // 是否校验
    check: {
      type: Boolean,
      default: false
    },
    // 校验文本
    checkText: {
      type: String
    },
    option: Array,
    prop: {
      type: Object,
      default () {
        return {
          label: 'label'
        }
      }
    },
    kw: String,
    fixed: {
      type: Boolean,
      default: false
    },
    enter: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      tagInputValue: this.kw,
      tagList: this.data,
      popperOptions: {
        boundariesElement: '.popover-body',
        gpuAcceleration: false,
        positionFixed: true,
        preventOverflow: true
      },
      showSearchThink: false
    }
  },
  watch: {
    data () {
      this.tagList = this.data
    },
    kw () {
      this.tagInputValue = this.kw
    }
  },
  methods: {
    handleCloseTag (tag) {
      this.tagList.splice(this.tagList.indexOf(tag), 1)
    },
    /**
     * 输入的内容转化为标签
     */
    searchTag (inputValue) {
      let value = inputValue[this.prop.label] || inputValue
      const v = value.replace(/\s*/g, '')
      if (value && v !== '') {
        if (this.tagList.indexOf(v) === -1) {
          // 新增标签
          this.tagList.push(v)
          this.tagInputValue = ''
        } else {
          // 已存在同名标签
          this.handleCloseTag(v)
          this.searchTag(v)
        }
      }
      this.$emit('update:data', this.tagList)
      this.$emit('update:kw', this.tagInputValue)
    },
    // 知识地图 我的订阅里的特殊情况，enter键不准添加
    enterTag (inputValue) {
      if (!this.enter) return
        console.log(inputValue)
        this.searchTag(inputValue)
    },
    querySearchAsync () {
      this.showSearchThink = true
      this.$emit('update:kw', this.tagInputValue)
    },
    hideSearchInput () {
      this.showSearchThink = false
    }
  },
  mounted () {

  }
}
</script>

<style scoped lang="scss">
  $--color-primary: #1890ff;

    .input-tag {
        position: relative;

        .error-empty {
            position: absolute;
            color: #F56C6C;
            font-size: 12px;
            line-height: 1;
            padding-top: 4px;
            top: 100%;
        }
    }

    .input-tag-check {
        border-radius: 4px;
        border: 1px solid #DCDFE6;
        padding: 2px 0;
        position: relative;

        .tag-list {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
        }

        ::v-deep .el-input__inner {
            border: none;
            height: 24px;
            line-height: 24px;
        }

        ::v-deep .el-tag {
            margin: 4px 5px;
            background-color: #F4F4F5;
            border: 1px solid #E9E9EB;
            white-space: pre-wrap;
            height: auto;

            + .el-tag {
                margin: 4px 5px;
            }
        }

        .popover-body {
            position: relative;
        }

        .tag-autocomplete {
            flex-grow: 1;
            line-height: 24px;
            margin: 3px 0;
            width: 100%;
            display: flex;
        }

        ::v-deep .el-input__icon {
            line-height: 24px;
        }
    }

    .input-tag:focus {
        border-color: $--color-primary;
        outline: 0;
    }

    .input-tag-empty {
        border-color: #f56c6c;
    }

    .tag-popover-item {
        .tag-popover-ul {
            list-style: none;
            margin: 0;
            max-height: 200px;
            display: block;
            padding: 0;

            > li {
                font-size: 14px;
                color: #606266;
                padding: 5px 0 5px 15px;
                cursor: pointer;
                transition: all .3s;
                text-align: left;
                width: 100%;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
                line-height: 21px;
                box-sizing: border-box;

                &:hover {
                    color: $--color-primary;
                }
            }
        }

        .tag-popover-empty {
            color: #C0C4CC;
            padding: 5px 0 5px 15px;
        }
    }
</style>

<style lang="scss">
    .tag-popover-body {
        padding: 10px 0;
        width: 100%;

        .popper__arrow {
            left: 3% !important;
        }
    }
</style>
