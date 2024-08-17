package com.ruoyi.activiti.service.impl;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.activiti.domain.SysActivitiTemplate;
import com.ruoyi.activiti.dto.SysActivitiApprove;
import com.ruoyi.activiti.dto.SysActivitiTemplateDto;
import com.ruoyi.common.utils.DateUtils;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.activiti.mapper.SysActivitiTemplateMapper;
import com.ruoyi.activiti.service.ISysActivitiTemplateService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * 流程模板Service业务层处理
 *
 * @author ruoyi
 * @date 2022-09-30
 */
@Service
public class SysActivitiTemplateServiceImpl implements ISysActivitiTemplateService {
    @Autowired
    private SysActivitiTemplateMapper sysActivitiTemplateMapper;

    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;

    /**
     * 查询流程模板
     *
     * @param id 流程模板主键
     * @return 流程模板
     */
    @Override
    public SysActivitiTemplate selectSysActivitiTemplateById(Long id) {
        return sysActivitiTemplateMapper.selectSysActivitiTemplateById(id);
    }

    /**
     * 查询流程模板列表
     *
     * @param sysActivitiTemplate 流程模板
     * @return 流程模板
     */
    @Override
    public List<SysActivitiTemplate> selectSysActivitiTemplateList(SysActivitiTemplate sysActivitiTemplate) {
        return sysActivitiTemplateMapper.selectSysActivitiTemplateList(sysActivitiTemplate);
    }

    /**
     * 新增流程模板
     *
     * @param sysActivitiTemplateDto 流程模板
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSysActivitiTemplate(SysActivitiTemplateDto sysActivitiTemplateDto) {
        sysActivitiTemplateDto.setCreateTime(DateUtils.getNowDate());

        // 1、保存流程模板
        SysActivitiTemplate sysActivitiTemplate = new SysActivitiTemplate();
        BeanUtils.copyProperties(sysActivitiTemplateDto, sysActivitiTemplate);
        sysActivitiTemplateMapper.insertSysActivitiTemplate(sysActivitiTemplate);

        /// 2、部署流程
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = deployProcess(sysActivitiTemplate);

        // 3、更新流程模板信息
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        sysActivitiTemplate.setProcessDefId(processDefinition.getId());
        sysActivitiTemplate.setProcessDeploymentId(deployment.getId());
        sysActivitiTemplate.setProcessKey(processDefinition.getKey());
        sysActivitiTemplateMapper.updateSysActivitiTemplate(sysActivitiTemplate);
    }

    public Deployment deployProcess(SysActivitiTemplate sysActivitiTemplate) {
        Long templateId = sysActivitiTemplate.getId();
        String templateName = sysActivitiTemplate.getName();
        String xml = sysActivitiTemplate.getProcessDefXml();

        XMLInputFactory factory = XMLInputFactory.newInstance();

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        XMLStreamReader reader = null;
        try {
            reader = factory.createXMLStreamReader(new StringReader(xml));
        } catch (XMLStreamException e) {
            throw new RuntimeException("Error parsing XML: " + e.getMessage(), e);
        }
        BpmnModel bpmnModel = bpmnXMLConverter.convertToBpmnModel(reader);
        Process process = bpmnModel.getMainProcess();
        Collection<FlowElement> flowElements = process.getFlowElements();
        Long startEvent = flowElements.stream()
                .filter(flowElement -> flowElement.getClass().getSimpleName().equals("StartEvent")).count();
        if (startEvent != 1) {
            throw new RuntimeException("There must be at least one StartEvent and no more than two StartEvent");
        }
        Long endEvent = flowElements.stream()
                .filter(flowElement -> flowElement.getClass().getSimpleName().equals("EndEvent")).count();
        if (endEvent != 1) {
            throw new RuntimeException("There must be at least one EndEvent and no more than two EndEvent");
        }
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof UserTask) {
                UserTask userTask = (UserTask) flowElement;
                if (userTask.getAssignee() == null || userTask.getAssignee().isEmpty()) {
                    throw new RuntimeException("UserTask not assigned yet");
                }
            }
        }

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addString("key_" + templateId + ".bpmn20.xml", xml)
                .name(templateName).deploy();
        return deployment;
    }

    /**
     * 修改流程模板
     *
     * @param sysActivitiTemplate 流程模板
     * @return 结果
     */
    @Override
    public int updateSysActivitiTemplate(SysActivitiTemplate sysActivitiTemplate) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = deployProcess(sysActivitiTemplate);
        sysActivitiTemplate.setUpdateTime(DateUtils.getNowDate());
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).singleResult();
        sysActivitiTemplate.setProcessDefId(processDefinition.getId());
        sysActivitiTemplate.setProcessDeploymentId(deployment.getId());
        sysActivitiTemplate.setProcessKey(processDefinition.getKey());
        return sysActivitiTemplateMapper.updateSysActivitiTemplate(sysActivitiTemplate);
    }

    /**
     * 批量删除流程模板
     *
     * @param ids 需要删除的流程模板主键
     * @return 结果
     */
    @Override
    public int deleteSysActivitiTemplateByIds(Long[] ids) {
        for (Long id : ids) {
            SysActivitiTemplate sysActivitiTemplate = selectSysActivitiTemplateById(id);
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
            RuntimeService runtimeService = processEngine.getRuntimeService();
            RepositoryService repositoryService = processEngine.getRepositoryService();
            List<ProcessInstance> processInstancesRunning = runtimeService.createProcessInstanceQuery()
                    .processDefinitionId(sysActivitiTemplate.getProcessDefId())
                    .list();
            if (processInstancesRunning.size() > 0) {
                throw new RuntimeException("Không thể xoá lưu trình này! Hãy hoàn thành các nhiệm vụ trước.");
            }

            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(sysActivitiTemplate.getProcessDefId())
                    .latestVersion()
                    .singleResult();
            if (processDefinition == null) {
                throw new RuntimeException("Không tìm thấy lưu trình này!");
            }
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
        }
        return sysActivitiTemplateMapper.deleteSysActivitiTemplateByIds(ids);
    }

    /**
     * 删除流程模板信息
     *
     * @param id 流程模板主键
     * @return 结果
     */
    @Override
    public int deleteSysActivitiTemplateById(Long id) {
        SysActivitiTemplate sysActivitiTemplate = selectSysActivitiTemplateById(id);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessInstance> processInstancesRunning = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey(sysActivitiTemplate.getProcessDefId())
                .list();
        if (processInstancesRunning.size() > 0) {
            throw new RuntimeException("Không thể xoá lưu trình này! Hãy hoàn thành các nhiệm vụ trước.");
        }

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(sysActivitiTemplate.getProcessDefId())
                .latestVersion()
                .singleResult();
        if (processDefinition == null) {
            throw new RuntimeException("Không tìm thấy lưu trình này!");
        }
        repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
        return sysActivitiTemplateMapper.deleteSysActivitiTemplateById(id);
    }

    public ProcessInstance startProcessInstanceByKey(String key) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key);
        System.out.println("流程定义ID:" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例ID=getId:" + processInstance.getId());
        System.out.println("当前活动ID:" + processInstance.getActivityId());
        System.out.println("流程实例ID=getProcessInstanceId:" + processInstance.getProcessInstanceId());
        return processInstance;
    }

    public List<Map<String, Object>> findMyTaskList(String processInstanceId, String assignee) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery()
                // .processDefinitionKey(key)
                .processInstanceId(processInstanceId)
                .taskAssignee(assignee)
                .list();

        List<Map<String, Object>> list = new ArrayList<>();
        for (Task task : taskList) {
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务Name:" + task.getName());
            System.out.println("提交人:" + task.getAssignee());
            Map<String, Object> map = new HashMap<>();
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("taskId", task.getId());
            map.put("taskName", task.getName());
            map.put("assignee", task.getAssignee());
            list.add(map);
        }
        return list;
    }

    @Override
    public Boolean completeTask(SysActivitiApprove sysActivitiApprove) {
        try{
            String processInstanceId = sysActivitiApprove.getProcessInstanceId();
            String assignee = sysActivitiApprove.getAssignee();
            String comment = sysActivitiApprove.getComment();
            String operator = sysActivitiApprove.getOperator();
            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
            TaskService taskService = processEngine.getTaskService();
    
            Task task = taskService.createTaskQuery()
                    // .processDefinitionKey(key)
                    .processInstanceId(processInstanceId)
                    .taskAssignee(assignee)
                    .singleResult();
    
            // taskService.complete(task.getId());
            Map<String, Object> variables = new HashMap<>();
            variables.put("comment", comment);
            taskService.addComment(task.getId(), processInstanceId, comment);
            if(operator.equals("reject")){
                taskService.deleteTask(task.getId(), comment);
                return true;
            }else {
                taskService.complete(task.getId(), variables);
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Map<String, Object>> findHistory(String processInstanceId) {
        List<Map<String, Object>> rtnList = new ArrayList<>();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        HistoryService historyService = processEngine.getHistoryService();
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        instanceQuery.processInstanceId(processInstanceId);
        instanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        List<HistoricActivityInstance> list = instanceQuery.list();

        for (HistoricActivityInstance hi : list) {
            if (hi.getEndTime() != null) {
                List<Comment> comments = taskService.getTaskComments(hi.getTaskId(), "comment");
                System.out.println(hi.getActivityId());
                System.out.println(hi.getActivityName());
                System.out.println(hi.getProcessDefinitionId());
                System.out.println(hi.getProcessInstanceId());
                System.out.println("============================");
                Map<String, Object> map = new HashMap<>();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String endTime = format.format(hi.getEndTime());
                String startTime = format.format(hi.getStartTime());
                map.put("endTime", endTime);
                map.put("startTime", startTime);
                map.put("completed", true);
                map.put("key", hi.getActivityId());
                map.put("activityType", hi.getActivityType());
                if ("startevent".equalsIgnoreCase(hi.getActivityType())) {
                    map.put("activityName", "开始节点");
                    map.put("assignee", "系统");
                    map.put("operator", "提交文档");
                } else if ("endevent".equalsIgnoreCase(hi.getActivityType())) {
                    map.put("activityName", "结束节点");
                    map.put("assignee", "系统");
                    map.put("operator", "结束流程");
                } else {
                    map.put("activityName", hi.getActivityName());
                    map.put("assignee", hi.getAssignee());
                    map.put("operator", "批准流程");
                }
                map.put("comments", CollUtil.isNotEmpty(comments) ? comments.get(0).getFullMessage() : null);
                rtnList.add(map);
            }
        }
        return rtnList;
    }

    public ProcessInstance getProcessInstanceByProcessInstanceId(String processInstanceId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        return processInstance;
    }

    public ProcessInstance getProcessInstanceByProcessDefinitionId(String processDefinitionId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        return processInstance;
    }

    public List<Map<String, Object>> findAllNodes(String processInstanceId) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // Lấy RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        ProcessInstance processInstance = getProcessInstanceByProcessInstanceId(processInstanceId);
        String processDefinitionId = processInstance.getProcessDefinitionId();
        // Lấy ProcessDefinition từ key của quy trình
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);

        // Lấy mô hình BPMN của quy trình
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinition.getId());

        // Lấy quy trình từ mô hình BPMN
        Process process = bpmnModel.getMainProcess();

        // Lấy toàn bộ các FlowElements (nodes) trong quy trình
        Collection<FlowElement> flowElements = process.getFlowElements();

        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        
        for (FlowElement flowElement : flowElements) {
            if(flowElement instanceof UserTask){
                UserTask userTask = (UserTask) flowElement;
                System.out.println("===================findAllNodes===================");
                System.out.println("Node ID     : " + userTask.getId());
                System.out.println("Node Name   : " + userTask.getName());
                System.out.println("Node Assignee   : " + userTask.getAssignee());
                System.out.println("Node Class  : " + userTask.getClass().getSimpleName());
                System.out.println("==================================================");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("NodeID", userTask.getId());
                map.put("NodeName", userTask.getName());
                map.put("NodeAssignee", userTask.getAssignee());
                map.put("NodeClass", userTask.getClass().getSimpleName());
                results.add(map);
            }
        }
        return results;
    }

    public List<Map<String, Object>> findAllTasks() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().list();

        List<Map<String, Object>> list = new ArrayList<>();
        for (Task task : taskList) {
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务Name:" + task.getName());
            System.out.println("提交人:" + task.getAssignee());
            Map<String, Object> map = new HashMap<>();
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("taskId", task.getId());
            map.put("taskName", task.getName());
            map.put("assignee", task.getAssignee());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> currentProcess(String processInstanceId) {
        // Lấy danh sách các tác vụ hiện tại
        List<Task> tasks = taskService.createTaskQuery()
                                      .processInstanceId(processInstanceId)
                                      .list();

        List<Map<String, Object>> historyList = findHistory(processInstanceId);
        Map<String, Object> map = new HashMap<>();
        for (Task task : tasks) {
            map.put("assignee", task.getAssignee());
            map.put("activityName", task.getName());
            map.put("completed", false);
            map.put("key", task.getTaskDefinitionKey());
            map.put("endTime", null);
            map.put("startTime", task.getCreateTime());
            map.put("comments", null);
            map.put("activityType", "userTask");
            map.put("operator", "批准流程");
            historyList.add(map);
        }
        return historyList;
    }

    @Override
    public SysActivitiTemplate getTemplate(Long id){
        SysActivitiTemplate sysActivitiTemplate = selectSysActivitiTemplateById(id);
        return sysActivitiTemplate;
    }

    public Map<String, Object> getProcessStatus(String processInstanceId){
        List<Map<String, Object>> allUserTask = findAllNodes(processInstanceId);
        List<Map<String, Object>> processHistory = findHistory(processInstanceId);

        Integer totalNode = allUserTask.size();


        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();


        Map<String, Object> map = new HashMap<>();
        map.put("totalNode", totalNode);
        return map;
    }
}
