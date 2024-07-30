export let xmlStr = `<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:activiti="http://activiti.org/bpmn" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC" xmlns:omgdi="http://www.omg.org/spec/DD/20100524/DI" xmlns:tns="http://www.activiti.org/test" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" expressionLanguage="http://www.w3.org/1999/XPath" id="m1636512109807" name="" targetNamespace="http://www.activiti.org/test" typeLanguage="http://www.w3.org/2001/XMLSchema">
  <process id="myLeave-pp" isClosed="false" isExecutable="true" name="czh审批流程" processType="None">
    <startEvent id="_2" name="StartEvent"/>
    <userTask activiti:assignee="ry" activiti:exclusive="true" id="_33" name="提交请假申请"/>
    <userTask activiti:assignee="admin" activiti:exclusive="true" id="_44" name="部门经理审批"/>
    <endEvent id="_55" name="EndEvent"/>
    <sequenceFlow id="_6" sourceRef="_2" targetRef="_33"/>
    <sequenceFlow id="_7" sourceRef="_33" targetRef="_44"/>
    <sequenceFlow id="_8" sourceRef="_44" targetRef="_55"/>
  </process>
  <bpmndi:BPMNDiagram documentation="background=#FFFFFF;count=1;horizontalcount=1;orientation=0;width=842.4;height=1195.2;imageableWidth=832.4;imageableHeight=1185.2;imageableX=5.0;imageableY=5.0" id="Diagram-_1" name="New Diagram">
    <bpmndi:BPMNPlane bpmnElement="myLeave-pp">
      <bpmndi:BPMNShape bpmnElement="_2" id="Shape-_2">
        <omgdc:Bounds height="32.0" width="32.0" x="185.0" y="50.0"/>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="32.0" width="32.0" x="0.0" y="0.0"/>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape bpmnElement="_33" id="Shape-_33">
        <omgdc:Bounds height="55.0" width="85.0" x="165.0" y="160.0"/>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="55.0" width="85.0" x="0.0" y="0.0"/>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape bpmnElement="_44" id="Shape-_44">
        <omgdc:Bounds height="55.0" width="85.0" x="170.0" y="270.0"/>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="55.0" width="85.0" x="0.0" y="0.0"/>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape bpmnElement="_55" id="Shape-_55">
        <omgdc:Bounds height="32.0" width="32.0" x="190.0" y="400.0"/>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="32.0" width="32.0" x="0.0" y="0.0"/>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNEdge bpmnElement="_6" id="BPMNEdge__6" sourceElement="_2" targetElement="_33">
        <omgdi:waypoint x="201.0" y="82.0"/>
        <omgdi:waypoint x="201.0" y="160.0"/>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="0.0" width="0.0" x="0.0" y="0.0"/>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="_7" id="BPMNEdge__7" sourceElement="_33" targetElement="_44">
        <omgdi:waypoint x="210.0" y="215.0"/>
        <omgdi:waypoint x="210.0" y="270.0"/>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="0.0" width="0.0" x="0.0" y="0.0"/>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge bpmnElement="_8" id="BPMNEdge__8" sourceElement="_44" targetElement="_55">
        <omgdi:waypoint x="206.0" y="325.0"/>
        <omgdi:waypoint x="206.0" y="400.0"/>
        <bpmndi:BPMNLabel>
          <omgdc:Bounds height="0.0" width="0.0" x="0.0" y="0.0"/>
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</definitions>`
