<template>
    <div class="editor-body">
        <!--<div class="title">
            <i></i>
            <span>知识正文</span>
        </div>-->
      <el-form class="form"
               label-width="85px"
               ref="form"
               label-position="right">
        <div class="content">
          <el-form-item label="正文">
            <vue-tinymce
                    v-model="content"
                    :setting="setting">
            </vue-tinymce>
          </el-form-item>
        </div>
      </el-form>
    </div>
</template>

<script>

export default {
  name: 'TextEditor',
  // props: {
  //   data: Array
  // },
  data() {
    return {
      content: '',
      setting: {
        menubar: false,
        // 工具栏
        toolbar: 'formatselect | fontselect | fontsizeselect | bold italic underline strikethrough | bullist numlist | alignleft aligncenter alignright alignjustify | outdent indent blockquote | undo redo | image media table link | code preview',
        toolbar_drawer: 'sliding',
        quickbars_selection_toolbar: 'removeformat | bold italic underline strikethrough | fontsizeselect forecolor backcolor',
        plugins: 'link image media table lists fullscreen quickbars advlist code preview media',//选择需加载的插件 ['','']
        language_url: '/tinymce/langs/zh_CN.js',//设置中文
        language: 'zh_CN',
        height: 352,
        images_upload_url: '/demo/upimg.php',
        toolbar_mode: 'wrap',// 宽度过小时，工具不隐藏
      },
      resultList: []
    }
  },
  computed: {
  },
  created() {

  },
  methods: {
    checkData(){
      if(!this.content){
        alert('请输入正文');
      }
    }
  }
}
</script>

<style scoped lang="scss">
    .editor-body {
        box-sizing: border-box;
        background: rgba(255, 255, 255, 0.39);
        padding: 0px 0 0 35px;

        .title {
            display: flex;
            align-items: center;
            margin-bottom: 20px;

            > i {
                width: 4px;
                height: 14px;
                background-color: #cdcdcd;
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

        .content {

            width: 95%;
            padding-bottom: 50px;
        }
    }
</style>
