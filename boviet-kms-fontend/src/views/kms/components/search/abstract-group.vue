<template>
    <div class="abstract-group">

        <div class="abstract-group-data">
          <div class="abstract-item" v-for="(item,index) of abstractList" :key="index">
            <abstract-item 
              :abstract="item" 
              :searchSimilar="searchSimilar" 
              :showCollect="showCollect"
              @open-collect="openCollect" 
              :from="from" />
          </div>
        </div>
        <div class="abstract-list" v-if="!checkbox">
            <abstract-item class="abstract" v-for="(item,index) of abstractList" :key="index" :abstract="item"
                           :searchSimilar="searchSimilar" :checkbox="checkbox" :showCollect="showCollect"
                           @open-collect="openCollect" :from="from"></abstract-item>
        </div>
        <div class="abstract-group-empty" v-if="abstractList.length===0">
            <span>暂无数据</span>
        </div>
    </div>
</template>

<script>
import AbstractItem from './abstract-item'

export default {
  name: 'AbstractGroup',
  components: { AbstractItem },
  props: {
    abstractList: {
      type: Array,
      default: () => []
    },
    searchSimilar: Boolean,
    checkAll: Boolean,
    indeterminate: Boolean,
    searchCheckList: Array,
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
  data () {
    return {
      abstract: []
    }
  },
  watch: {
    checkAll () {
      if (this.checkAll) {
        this.abstract = this.abstractList
      } else if (!this.indeterminate) {
        this.abstract = []
      }
    }
  },
  created () {
    if (this.checkAll) {
      this.abstract = this.abstractList
    } else if (!this.indeterminate) {
      this.abstract = []
    }
    if (this.searchCheckList && this.searchCheckList.length > 0) {
      this.abstract = this.searchCheckList
    }
  },
  methods: {
    changeCheckAll (val) {
      if (this.abstract.length === this.abstractList.length) {
        this.$emit('changeCheckAll', true, val)
      } else {
        this.$emit('changeCheckAll', false, val)
      }
    },
    openCollect (data) {
      this.$emit('open-collect', data)
    }
  }
}
</script>

<style scoped lang="scss">
    .abstract-group {
        .abstract-group-data {
            /*padding: 0 4px;*/

            .abstract-item {
                width: 100%;
                border-bottom: 1px solid #ECEEF5;
                /*cursor: auto;*/
                transition: background-color .3s;

                &:hover {
                    background: #e1f3fb;
                }

                ::v-deep .el-checkbox__input {
                    position: absolute;
                    top: 26px;
                    left: 12px;
                }

                ::v-deep .el-checkbox__label {
                    padding: 0;
                    width: 100%;
                }
                padding: 30px 30px 0px 30px;
            }

        }

        .abstract-list {
            .abstract {
                width: 100%;
                border-bottom: 1px solid #ECEEF5;
                /*cursor: auto;*/
                transition: background-color .3s;

                &:hover {
                    background: #f5f7fa;
                }
            }
        }

        .abstract-group-empty {
            text-align: center;
            border-bottom: 1px solid #ECEEF5;
            border-right: 1px solid #ECEEF5;
            border-left: 1px solid #ECEEF5;

            > span {
                color: #909399;
                line-height: 120px;
                font-size: 14px;
            }
        }
    }

</style>
