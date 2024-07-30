package com.ruoyi.web.controller.activiti;

import com.ruoyi.activiti.dto.ActivitiDto;
import com.ruoyi.activiti.dto.SysActivitiTemplateDto;
import com.ruoyi.activiti.service.ISysActivitiService;
import com.ruoyi.activiti.service.ISysActivitiTemplateService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 流程
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/sys/activiti")
public class SysActivitiController extends BaseController{

    @Resource
    private ISysActivitiService sysActivitiService;

    @Autowired
    private ISysActivitiTemplateService sysActivitiTemplateService;

    @PostMapping("/deploy")
    public void deployment(@RequestBody ActivitiDto activitiDto){
        sysActivitiService.deployment(activitiDto);
        //sysActivitiService.testDeployment();
    }

    /**
     * 新增流程模板
     */
    @PreAuthorize("@ss.hasPermi('system:activiti:template:add')")
    @Log(title = "流程模板", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SysActivitiTemplateDto sysActivitiTemplateDto)
    {
        sysActivitiTemplateService.insertSysActivitiTemplate(sysActivitiTemplateDto);
        return AjaxResult.success();
    }

    @PostMapping("/startProcess")
    public void startProcess(){
        sysActivitiService.startProcess();
        //sysActivitiService.testStartProcess();
    }

}
