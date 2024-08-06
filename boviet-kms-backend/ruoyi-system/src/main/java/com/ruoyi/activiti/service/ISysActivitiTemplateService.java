package com.ruoyi.activiti.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.activiti.domain.SysActivitiTemplate;
import com.ruoyi.activiti.dto.SysActivitiApprove;
import com.ruoyi.activiti.dto.SysActivitiTemplateDto;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * 流程模板Service接口
 *
 * @author ruoyi
 * @date 2022-09-30
 */
public interface ISysActivitiTemplateService
{
    /**
     * 查询流程模板
     *
     * @param id 流程模板主键
     * @return 流程模板
     */
    public SysActivitiTemplate selectSysActivitiTemplateById(Long id);

    /**
     * 查询流程模板列表
     *
     * @param sysActivitiTemplate 流程模板
     * @return 流程模板集合
     */
    public List<SysActivitiTemplate> selectSysActivitiTemplateList(SysActivitiTemplate sysActivitiTemplate);

    /**
     * 新增流程模板
     *
     * @param sysActivitiTemplateDto 流程模板
     * @return 结果
     */
    public void insertSysActivitiTemplate(SysActivitiTemplateDto sysActivitiTemplateDto);

    /**
     * 修改流程模板
     *
     * @param sysActivitiTemplate 流程模板
     * @return 结果
     */
    public int updateSysActivitiTemplate(SysActivitiTemplate sysActivitiTemplate);

    /**
     * 批量删除流程模板
     *
     * @param ids 需要删除的流程模板主键集合
     * @return 结果
     */
    public int deleteSysActivitiTemplateByIds(Long[] ids);

    /**
     * 删除流程模板信息
     *
     * @param id 流程模板主键
     * @return 结果
     */
    public int deleteSysActivitiTemplateById(Long id);

    public ProcessInstance startProcessInstanceByKey(String key);

    public List<Map<String,Object>> findMyTaskList(String processInstanceId,String assignee);

    public void completeTask(SysActivitiApprove sysActivitiApprove);

    public List<Map<String,Object>> findHistory(String processInstanceId);

    public List<Map<String, Object>> findAllNodes(String processInstanceId);

    public List<Map<String, Object>> findAllTasks();

    public List<Map<String,Object>> processHistory(String processInstanceId);

    public SysActivitiTemplate getTemplate(Long id);
}
