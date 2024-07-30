package com.ruoyi.activiti.mapper;

import java.util.List;
import com.ruoyi.activiti.domain.SysActivitiTemplate;

/**
 * 流程模板Mapper接口
 *
 * @author ruoyi
 * @date 2022-09-30
 */
public interface SysActivitiTemplateMapper
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
     * @param sysActivitiTemplate 流程模板
     * @return 结果
     */
    public int insertSysActivitiTemplate(SysActivitiTemplate sysActivitiTemplate);

    /**
     * 修改流程模板
     *
     * @param sysActivitiTemplate 流程模板
     * @return 结果
     */
    public int updateSysActivitiTemplate(SysActivitiTemplate sysActivitiTemplate);

    /**
     * 删除流程模板
     *
     * @param id 流程模板主键
     * @return 结果
     */
    public int deleteSysActivitiTemplateById(Long id);

    /**
     * 批量删除流程模板
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysActivitiTemplateByIds(Long[] ids);
}
