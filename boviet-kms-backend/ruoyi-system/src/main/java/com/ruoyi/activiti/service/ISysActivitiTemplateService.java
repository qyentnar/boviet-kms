package com.ruoyi.activiti.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.activiti.domain.SysActivitiTemplate;
import com.ruoyi.activiti.dto.SysActivitiApprove;
import com.ruoyi.activiti.dto.SysActivitiTemplateDto;
import com.ruoyi.common.core.domain.entity.SysUser;

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

    /**
     * 
     * @param key
     * @return
     */
    public ProcessInstance startProcessInstanceByKey(String key);

    /**
     * 
     * @param processInstanceId
     * @param assignee
     * @return
     */
    public List<Map<String,Object>> getTaskList(String processInstanceId,String assignee);

    /**
     * 
     * @param sysActivitiApprove
     * @return
     */
    public Boolean completeTask(SysActivitiApprove sysActivitiApprove);

    /**
     * 
     * @param processInstanceId
     * @return
     */
    public List<Map<String,Object>> getHistoryByProcessInstanceId(String processInstanceId);

    /**
     * 
     * @param processInstanceId
     * @return
     */
    public List<Map<String, Object>> getNodeByProcessInstanceId(String processInstanceId);

    /**
     * 
     * @return
     */

    public List<Map<String, Object>> getAllTasks();

    /**
     * 
     * @param processInstanceId
     * @return
     */
    public List<Map<String,Object>> currentProcess(String processInstanceId);

    /**
     * 
     * @param id
     * @return
     */
    public SysActivitiTemplate getTemplateById(Long id);

    /**
     * 
     * @return
     */
    public List<Map<String, Object>> getHistoryByUser(SysUser sysUser);
}
