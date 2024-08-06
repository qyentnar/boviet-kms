import Vue from 'vue'

import Cookies from 'js-cookie'
import axios from 'axios'

import Element from 'element-ui'
import VForm from 'vform-builds'
import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/ruoyi.scss' // ruoyi css

// 以下为bpmn工作流绘图工具的样式
import 'bpmn-js/dist/assets/diagram-js.css' // 左边工具栏以及编辑节点的样式
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-codes.css'
import 'bpmn-js/dist/assets/bpmn-font/css/bpmn-embedded.css'
import 'bpmn-js-properties-panel/dist/assets/bpmn-js-properties-panel.css' // 右边工具栏样式

import 'element-ui/lib/theme-chalk/index.css'  //引入element-ui样式
import 'vform-builds/dist/VFormDesigner.css'  //引入VForm样式

import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive
import plugins from './plugins' // plugins
import { download } from '@/utils/request'

import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from "@/utils/ruoyi";
// 分页组件
import Pagination from "@/components/Pagination";
// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar"
// 富文本组件
import Editor from "@/components/Editor"
// 文件上传组件
import FileUpload from "@/components/FileUpload"
// 图片上传组件
import ImageUpload from "@/components/ImageUpload"
// 图片预览组件
import ImagePreview from "@/components/ImagePreview"
// 字典标签组件
import DictTag from '@/components/DictTag'
// 头部标签组件
import VueMeta from 'vue-meta'
// 字典数据组件
import DictData from '@/components/DictData'

import VueHighlightJS from 'vue-highlightjs'
import 'highlight.js/styles/atom-one-dark-reasonable.css'
Vue.use(VueHighlightJS)

import VueBpmnModeler from "vue-bpmn-modeler";
import "vue-bpmn-modeler/lib/vue-bpmn-modeler.css";
Vue.use(VueBpmnModeler);

import Process from "@/components/Process/index.js";
Vue.use(Process);
import "@/components/Process/theme/index.scss";

import VueQrcodeReader from "vue-qrcode-reader";
Vue.use(VueQrcodeReader);

//import "@/package/theme/element-variables.scss";

// 全局方法挂载
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree

// 全局组件挂载
Vue.component('DictTag', DictTag)
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.component('Editor', Editor)
Vue.component('FileUpload', FileUpload)
Vue.component('ImageUpload', ImageUpload)
Vue.component('ImagePreview', ImagePreview)

Vue.use(directive)
Vue.use(plugins)
Vue.use(VueMeta)
DictData.install()

import moment from 'moment'
Vue.filter('timeFilter', function (value, formatString) {
  if (!value) {
    return '--'
  }
  formatString = formatString || 'YYYY-MM-DD'
  return moment(value).format(formatString)
})

Vue.filter('chinaTimeFilter', function (value,formatString) {
  if (!value) {
    return '--'
  }
  //获取当前时间
  let m1 = moment();
  //获取需要对比的时间
  let m2 = moment(value);
  //计算相差多少天 day可以是second minute
  let day = m1.diff(m2, 'day');
  if(day===0||day===1) {
    return moment(value).locale('zh-cn').calendar(null, {
      lastDay: '[昨天]  H:mm',
      sameDay: '[今天]  H:mm'
    });
  }else if(day===2){
    return '前天 '+moment(value).format("HH:mm")
  }else{
    formatString = formatString || 'YYYY-MM-DD'
    return moment(value).format(formatString)
  }
})


/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium' // set element-ui default size
})
Vue.use(VForm) 
window.axios = axios

Vue.config.productionTip = false


import tinymce from 'tinymce'
import VueTinymce from '@packy-tang/vue-tinymce'

Vue.prototype.$tinymce = tinymce
Vue.use(VueTinymce)

/*new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})*/

new Vue({
  router,
  store,
  render: (h) => h(App)
}).$mount('#app')

