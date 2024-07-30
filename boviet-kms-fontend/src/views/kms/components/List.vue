<template>
    <div>
      <abstract-group :checkAll="kg.checkAll"
                      :abstractList="kg.list"
                      :indeterminate="kg.indeterminate"
                      :searchCheckList="kg.checkKg"
                      @changeCheckAll="changeCheckAllTrue"
                      class="abstract-group-list"
                      v-loading="loading"></abstract-group>
    </div>
</template>

<script>
  import AbstractGroup from './search/abstract-group'

  export default {
    name: "List",
    components:{
      AbstractGroup
    },
    props:{
      list:Array
    },
    data(){
      return{
        loading:false,
        kg: {
          list: [],
          mode: 'table',
          checkAll: false,
          indeterminate: false,
          checkKg: []//已经选中的知识
        },
      }
    },
    watch:{
      list:{
        handler(){
          this.kg.list = this.list
          console.log("kg=====:",this.kg.list)
        }
      }
    },
    methods:{
      // 单选摘要列表时，全选的状态
      changeCheckAllTrue (val, data) {
        this.kg.indeterminate = true
        this.kg.checkAll = val
        this.kg.checkKg = data
      },
    }
  }
</script>

<style scoped>

</style>
