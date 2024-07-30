package com.ruoyi.web.controller.activiti;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.activiti.dto.SysActivitiApprove;
import com.ruoyi.activiti.dto.SysActivitiTemplateDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.activiti.domain.SysActivitiTemplate;
import com.ruoyi.activiti.service.ISysActivitiTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 流程模板Controller
 *
 * @author ruoyi
 * @date 2022-09-30
 */
@RestController
@RequestMapping("/sys/activiti/template")
public class SysActivitiTemplateController extends BaseController
{
    @Autowired
    private ISysActivitiTemplateService sysActivitiTemplateService;

    /**
     * 查询流程模板列表
     */
    @PreAuthorize("@ss.hasPermi('sys:activiti:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysActivitiTemplate sysActivitiTemplate)
    {
        startPage();
        List<SysActivitiTemplate> list = sysActivitiTemplateService.selectSysActivitiTemplateList(sysActivitiTemplate);
        return getDataTable(list);
    }

    /**
     * 导出流程模板列表
     */
    @PreAuthorize("@ss.hasPermi('sys:activiti:template:export')")
    @Log(title = "流程模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysActivitiTemplate sysActivitiTemplate)
    {
        List<SysActivitiTemplate> list = sysActivitiTemplateService.selectSysActivitiTemplateList(sysActivitiTemplate);
        ExcelUtil<SysActivitiTemplate> util = new ExcelUtil<SysActivitiTemplate>(SysActivitiTemplate.class);
        util.exportExcel(response, list, "流程模板数据");
    }

    /**
     * 获取流程模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('sys:activiti:template:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysActivitiTemplateService.selectSysActivitiTemplateById(id));
    }

    /**
     * 新增流程模板
     */
    @PreAuthorize("@ss.hasPermi('sys:activiti:template:add')")
    @Log(title = "流程模板", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SysActivitiTemplateDto sysActivitiTemplateDto)
    {
        sysActivitiTemplateDto.setCreateUserId(getUserId());
        sysActivitiTemplateDto.setCreateBy(getUsername());
        sysActivitiTemplateService.insertSysActivitiTemplate(sysActivitiTemplateDto);
        return AjaxResult.success();
    }

    /**
     * 修改流程模板
     */
    @PreAuthorize("@ss.hasPermi('sys:activiti:template:edit')")
    @Log(title = "流程模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysActivitiTemplate sysActivitiTemplate)
    {
        sysActivitiTemplate.setUpdateUserId(getUserId());
        sysActivitiTemplate.setUpdateBy(getUsername());
        sysActivitiTemplateService.updateSysActivitiTemplate(sysActivitiTemplate);
        return AjaxResult.success();
    }

    /**
     * 删除流程模板
     */
    @PreAuthorize("@ss.hasPermi('sys:activiti:template:remove')")
    @Log(title = "流程模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysActivitiTemplateService.deleteSysActivitiTemplateByIds(ids));
    }

    @GetMapping(value = "/findTask/{processInstanceId}/{assignee}")
    public AjaxResult findTask(@PathVariable("processInstanceId") String processInstanceId,@PathVariable("assignee") String assignee)
    {
        return AjaxResult.success(sysActivitiTemplateService.findMyTaskList(processInstanceId,assignee));
    }

    @PostMapping(value = "/completeTask")
    public AjaxResult completeTask(@RequestBody SysActivitiApprove sysActivitiApprove)
    {
        sysActivitiTemplateService.completeTask(sysActivitiApprove);
        return AjaxResult.success();
    }

    @GetMapping(value = "/findHistory/{processInstanceId}")
    public AjaxResult findHistory(@PathVariable("processInstanceId") String processInstanceId)
    {
        return AjaxResult.success(sysActivitiTemplateService.findHistory(processInstanceId));
    }

    @GetMapping("/findAllNodes/{processInstanceId}")
    public AjaxResult findAllNodes(@PathVariable("processInstanceId") String processInstanceId) {
        return AjaxResult.success(sysActivitiTemplateService.findAllNodes(processInstanceId));
    }
    
}
