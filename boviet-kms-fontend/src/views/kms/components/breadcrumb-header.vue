<template>
    <div class="breadcrumb-header" :style="{'background':background}">
        <div class="breadcrumb-header-left" v-if="existBack">
            <span class="breadcrumb-header-back" @click="goBack">返回</span>
            <span class="breadcrumb-header-line">|</span>
        </div>
        <el-breadcrumb separator-class="el-icon-arrow-right" class="the-breadcrumb">
            <el-breadcrumb-item v-for="(item,index) of breadcrumbList" :to="item.to" :key="index">{{item.name}}
            </el-breadcrumb-item>
        </el-breadcrumb>
        <div class="breadcrumb-header-right">
            <slot name="right"></slot>
        </div>
    </div>
</template>

<script>
export default {
  name: 'breadcrumbHeader',
  props: {
    existBack: { //是否显示返回按钮
      type: Boolean,
      default: false
    },
    breadcrumbList: Array,  //面包屑数组
    routerName: String, // 点击返回可以跳转的指定路径名称
    background: String // 面包屑头部背景色
  },
  methods: {
    goBack () {
      if (this.routerName) {
        this.$router.push({ name: this.routerName })
      } else {
        window.history.length > 1 ? this.$router.go(-1) : this.$router.push('/')
      }
    }
  }
}
</script>

<style scoped lang="scss">
  $--color-primary:#cdcdcd;

    .breadcrumb-header {
        display: flex;
        border-bottom: 1px solid #EBEEF5;
        align-items: center;
        font-size: 14px;
        padding: 0 20px;

        .breadcrumb-header-right {
            margin-left: auto;

            .el-button + .el-button {
                margin-left: 16px;
            }
        }

        .breadcrumb-header-left {
            display: flex;
            align-items: center;

            .breadcrumb-header-back {
                color: #606266;
                display: block;
                height: 20px;
                line-height: 20px;
                cursor: pointer;

                &:hover {
                    color: $--color-primary;
                }
            }

            .breadcrumb-header-line {
                font-size: 20px;
                color: #E5E7ED;
                width: 1px;
                height: 20px;
                display: block;
                margin: 0 14px;
                line-height: 19px;
            }
        }

        .the-breadcrumb {
            height: 47px;
            display: flex;
            align-items: center;

            ::v-deep .el-breadcrumb__item {
                .el-breadcrumb__inner {
                    &.is-link {
                        font-weight: normal;
                    }
                }

                &:last-of-type {
                    .el-breadcrumb__inner {
                        color: $--color-primary;

                        &.is-link {
                            cursor: pointer;

                            &:hover {
                                color: $--color-primary;
                                font-weight: bold;
                            }
                        }
                    }
                }
            }
        }
    }
</style>
