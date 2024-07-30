package com.ruoyi.activiti.service.impl;

import com.ruoyi.activiti.dto.ActivitiDto;
import com.ruoyi.activiti.service.ISysActivitiService;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;
import org.activiti.bpmn.model.Process;

import java.util.Collection;
import java.util.List;


@Service
public class SysActivitiServiceImpl implements ISysActivitiService {

    public void deployment(ActivitiDto activitiDto){
        System.out.println(activitiDto.getXmlString());
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().addString("czh.bpmn20.xml",activitiDto.getXmlString()).name(activitiDto.getFlowName()).deploy();
        System.out.println("流程部署id:"+deployment.getId());
        System.out.println("流程部署Name:"+deployment.getName());
    }

    public void startProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myLeave-pp");
        System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());
        System.out.println("流程实例ID=getId:"+processInstance.getId());
        System.out.println("当前活动ID:"+processInstance.getActivityId());
        System.out.println("流程实例ID=getProcessInstanceId:"+processInstance.getProcessInstanceId());
    }

    public void testDeployment(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/leave.bpmn")
                .addClasspathResource("bpmn/leave.png")
                .name("请假申请流程")
                .deploy();

        System.out.println("流程部署id:"+deployment.getId());
        System.out.println("流程部署Name:"+deployment.getName());
    }


    public void testStartProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myLeave");

        System.out.println("流程定义ID:"+processInstance.getProcessDefinitionId());
        System.out.println("流程实例ID=getId:"+processInstance.getId());
        System.out.println("当前活动ID:"+processInstance.getActivityId());
        System.out.println("流程实例ID=getProcessInstanceId:"+processInstance.getProcessInstanceId());
    }

    public void findPersonalTaskList(){
        String assignee = "manager";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey("myLeave")
                .taskAssignee(assignee)
                .list();

        for(Task task:list){
            System.out.println("流程实例ID:"+task.getProcessInstanceId());
            System.out.println("任务ID:"+task.getId());
            System.out.println("任务Name:"+task.getName());
            System.out.println("提交人:"+task.getAssignee());

        }
    }

    public void completeTask(){
        String assignee = "worker";
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        Task task = taskService.createTaskQuery()
                .processDefinitionKey("myLeave")
                .taskAssignee(assignee)
                .singleResult();

        taskService.complete(task.getId());
    }

    public void findHistory(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService =  processEngine.getHistoryService();
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        instanceQuery.processInstanceId("101");
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        List<HistoricActivityInstance> list = instanceQuery.list();

        for(HistoricActivityInstance hi:list){
            System.out.println(hi.getActivityId());
            System.out.println(hi.getActivityName());
            System.out.println(hi.getProcessDefinitionId());
            System.out.println(hi.getProcessInstanceId());
            System.out.println("============================");
        }

    }

    public void findAllNodes(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // Lấy RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // Lấy ProcessDefinition từ key của quy trình
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
            .processDefinitionKey("myLeave")
            .latestVersion()
            .singleResult();

        // Lấy mô hình BPMN của quy trình
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

        // Lấy quy trình từ mô hình BPMN
        Process process = bpmnModel.getMainProcess();

        // Lấy toàn bộ các FlowElements (nodes) trong quy trình
        Collection<FlowElement> flowElements = process.getFlowElements();

        // In ra thông tin các nodes
        for (FlowElement flowElement : flowElements) {
            System.out.println("Node ID: " + flowElement.getId());
            System.out.println("Node Name: " + flowElement.getName());
            System.out.println("Node Class: " + flowElement.getClass().getSimpleName());
            System.out.println();
        }
    }
}
