<template>
  <div class="bpmnHome">
    <div class="containers">
      <div class="canvas" ref="canvas"></div>
      <div id="js-properties-panel" class="panel"></div>
      <div class="toolbar">
        <a ref="saveDiagram" href="javascript:" title="download BPMN diagram">下载BPMN</a>
        <a ref="saveSvg" href="javascript:" title="download as SVG image">下载SVG</a>
        <a href="javascript:" @click="previewProcessXML">预览XML</a>
        <!--<a href="javascript:" @click="previewProcessJson">预览JSON</a>-->
        <a href="javascript:" @click="deployProcessXML">部署</a>
        <a href="javascript:" @click="startProcess">开启流程</a>
      </div>
    </div>
    <el-dialog title="预览" width="60%" :visible.sync="previewModelVisible" append-to-body destroy-on-close>
      <div v-highlightjs>
        <pre>
          <code>
            {{previewResult}}
          </code>
        </pre>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import BpmnModeler from 'bpmn-js/lib/Modeler'
  import propertiesPanelModule from 'bpmn-js-properties-panel'
  import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda'
  import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda'
  import customTranslate from "../../utils/customTranslate/customTranslate";
  let customTranslateModule = {
    translate:['value',customTranslate]
  }
  import { xmlStr } from "./myLeave"
  // 引入json转换与高亮
  //import X2JS from "x2js"
  import { deploy,startProcess } from "@/api/activiti/index";

  export default {
    data() {
      return {
        bpmnModeler: null,
        container: null,
        canvas: null,
        previewModelVisible: false,
        previewResult: "",
        previewType: "xml",
        xmlData:xmlStr
      }
    },
    watch: {
      xmlData: function (newValue) { // 在 xmlString 发生变化时，重新创建，从而绘制流程图
        this.createNewDiagram(newValue);
      }
    },
    mounted() {
      this.init()
    },
    methods: {
      init() {
        const canvas = this.$refs.canvas
        this.bpmnModeler = new BpmnModeler({
          container: canvas,
          propertiesPanel: {
            parent: '#js-properties-panel'
          },
          additionalModules: [
            // 右边的属性栏
            propertiesProviderModule,
            propertiesPanelModule,
            customTranslateModule
          ],
          moddleExtensions: {
            camunda: camundaModdleDescriptor
          }
        })

        // 获取a标签dom节点
        const downloadLink = this.$refs.saveDiagram;
        const downloadSvgLink = this.$refs.saveSvg;
        // 监听流程图改变事件
        const _this = this;
        this.bpmnModeler.on("commandStack.changed", function() {
          console.log('commandStack.changed------------------')
          _this.saveSVG(function(err, svg) {
            _this.setEncoded(downloadSvgLink, "diagram.svg", err ? null : svg);
          });
          _this.saveDiagram(function(err, xml) {
            _this.setEncoded(downloadLink, "diagram.bpmn", err ? null : xml);
          });
        });

        this.createNewDiagram()
      },
      createNewDiagram(val) {
        //var xmlStr = ``
        let xml = val || this.xmlData
        console.log('createNewDiagram-------',val,xml)
        this.bpmnModeler.importXML(xml, (err) => {
          if (err) {
            // console.error(err)
          } else {
            // 这里是成功之后的回调, 可以在这里做一系列事情
            this.success()
          }
        })
      },
      success() {
        console.log('创建成功!')
      },
      // 当图发生改变的时候会调用这个函数，这个data就是图的xml
      setEncoded(link, name, data) {
        // 把xml转换为URI，下载要用到的
        const encodedData = encodeURIComponent(data);
        // 获取到图的xml，保存就是把这个xml提交给后台
        this.xmlData = data;
        // 下载图的具体操作,改变a的属性，className令a标签可点击，href令能下载，download是下载的文件的名字
        if (data) {
          link.className = "active";
          link.href = "data:application/bpmn20-xml;charset=UTF-8," + encodedData;
          link.download = name;
        }
      },
      // 下载为SVG格式,done是个函数，调用的时候传入的
      saveSVG(done) {
        // 把传入的done再传给bpmn原型的saveSVG函数调用
        this.bpmnModeler.saveSVG(done);
      },
      saveDiagram(done) {
        // 把传入的done再传给bpmn原型的saveXML函数调用
        this.bpmnModeler.saveXML({ format: true }, function(err, xml) {
          done(err, xml);
        });
      },
      previewProcessXML() {
        this.bpmnModeler.saveXML({ format: true }).then(({ xml }) => {
          this.previewResult = xml;
          console.log('previewProcessXML-------------',this.previewResult,xml)
          this.previewType = "xml";
          this.previewModelVisible = true;
        });
      },
      previewProcessJson() {
        /*const newConvert = new X2JS();
        this.bpmnModeler.saveXML({ format: true }).then(({ xml }) => {
          const { definitions } = newConvert.xml2js(xml);
          if (definitions) {
            this.previewResult = JSON.stringify(definitions, null, 4);
          } else {
            this.previewResult = "";
          }

          this.previewType = "json";
          this.previewModelVisible = true;
        });*/
      },
      deployProcessXML() {
        this.bpmnModeler.saveXML({ format: true }).then(({ xml }) => {
          const data = {
            flowName:'申请流程',
            xmlString:xml
          }
          deploy(data).then(response => {
            this.$modal.msgSuccess("部署成功");
          });
        });
      },
      startProcess() {
        this.bpmnModeler.saveXML({ format: true }).then(({ xml }) => {
          const data = {
            flowName:'申请流程',
            xmlString:xml
          }
          startProcess(data).then(response => {
            this.$modal.msgSuccess("部署成功");
          });
        });
      }
    }
  }
</script>

<style scoped lang="scss">
  .containers {
    position: absolute;
    background-color: #ffffff;
    width: 100%;
    height: 100%;

    .canvas {
      width: 100%;
      height: 100%;
    }

    .panel {
      position: absolute;
      right: 0;
      top: 0;
      width: 300px;
    }

    .toolbar {
      position: absolute;
      top: 20px;
      right: 350px;

      a {
        text-decoration: none;
        margin: 5px;
        color: #409eff;
      }
    }
  }
</style>
