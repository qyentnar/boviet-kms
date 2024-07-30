export let xmlStr = `<?xml version="1.0" encoding="UTF-8"?>
<bpmn2:definitions xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" id="sample-diagram" targetNamespace="http://bpmn.io/schema/bpmn" xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd">
  <bpmn2:process id="process-kms" name="通用知识审批流程">
    <bpmn2:documentation>描述</bpmn2:documentation>
    <bpmn2:startEvent id="StartEvent_01ydzqe" name="开始">
      <bpmn2:outgoing>SequenceFlow_1qw929z</bpmn2:outgoing>
    </bpmn2:startEvent>
    <bpmn2:sequenceFlow id="SequenceFlow_1qw929z" sourceRef="StartEvent_01ydzqe" targetRef="Task_apply" />
    <bpmn2:userTask id="Task_apply" name="知识提交申请" activiti:assignee="ry">
      <bpmn2:incoming>SequenceFlow_1qw929z</bpmn2:incoming>
      <bpmn2:outgoing>Flow_1dpb6et</bpmn2:outgoing>
    </bpmn2:userTask>
    <bpmn2:userTask id="Task_approve" name="知识审批" activiti:assignee="admin">
      <bpmn2:incoming>Flow_1dpb6et</bpmn2:incoming>
      <bpmn2:outgoing>Flow_0d42uhc</bpmn2:outgoing>
    </bpmn2:userTask>
    <bpmn2:sequenceFlow id="Flow_1dpb6et" sourceRef="Task_apply" targetRef="Task_approve" />
    <bpmn2:endEvent id="Event_1wy3nbc">
      <bpmn2:incoming>Flow_0d42uhc</bpmn2:incoming>
    </bpmn2:endEvent>
    <bpmn2:sequenceFlow id="Flow_0d42uhc" sourceRef="Task_approve" targetRef="Event_1wy3nbc" />
  </bpmn2:process>
  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="process-kms">
      <bpmndi:BPMNEdge id="Flow_0d42uhc_di" bpmnElement="Flow_0d42uhc">
        <di:waypoint x="550" y="360" />
        <di:waypoint x="550" y="412" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_1dpb6et_di" bpmnElement="Flow_1dpb6et">
        <di:waypoint x="550" y="230" />
        <di:waypoint x="550" y="280" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="SequenceFlow_1qw929z_di" bpmnElement="SequenceFlow_1qw929z">
        <di:waypoint x="550" y="108" />
        <di:waypoint x="550" y="150" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNShape id="StartEvent_01ydzqe_di" bpmnElement="StartEvent_01ydzqe">
        <dc:Bounds x="532" y="72" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="539" y="53" width="22" height="14" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="UserTask_1qxjy46_di" bpmnElement="Task_apply">
        <dc:Bounds x="500" y="150" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="UserTask_1j0us24_di" bpmnElement="Task_approve">
        <dc:Bounds x="500" y="280" width="100" height="80" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Event_1wy3nbc_di" bpmnElement="Event_1wy3nbc">
        <dc:Bounds x="532" y="412" width="36" height="36" />
      </bpmndi:BPMNShape>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</bpmn2:definitions>



      `
