package com.ruoyi.web.controller.kms;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.kms.domain.KmsMainVisit;
import com.ruoyi.kms.service.IKmsMainVisitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 访问统计Controller
 * 
 * @author ruoyi
 * @date 2024-08-09
 */
@RestController
@RequestMapping("/kms/kms-visit")
public class KmsMainVisitController extends BaseController
{
    @Autowired
    private IKmsMainVisitService kmsMainVisitService;

    /**
     * 查询访问统计列表
     */
    @PreAuthorize("@ss.hasPermi('kms:kms-visit:list')")
    @GetMapping("/list")
    public TableDataInfo list(KmsMainVisit kmsMainVisit)
    {
        startPage();
        List<KmsMainVisit> list = kmsMainVisitService.selectKmsMainVisitList(kmsMainVisit);
        return getDataTable(list);
    }

    /**
     * 导出访问统计列表
     */
    @PreAuthorize("@ss.hasPermi('kms:kms-visit:export')")
    @Log(title = "访问统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KmsMainVisit kmsMainVisit)
    {
        List<KmsMainVisit> list = kmsMainVisitService.selectKmsMainVisitList(kmsMainVisit);
        ExcelUtil<KmsMainVisit> util = new ExcelUtil<KmsMainVisit>(KmsMainVisit.class);
        util.exportExcel(response, list, "访问统计数据");
    }

    /**
     * 获取访问统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('kms:kms-visit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(kmsMainVisitService.selectKmsMainVisitById(id));
    }

    /**
     * 新增访问统计
     */
    @PreAuthorize("@ss.hasPermi('kms:kms-visit:add')")
    @Log(title = "访问统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KmsMainVisit kmsMainVisit)
    {
        return toAjax(kmsMainVisitService.insertKmsMainVisit(kmsMainVisit));
    }

    /**
     * 修改访问统计
     */
    @PreAuthorize("@ss.hasPermi('kms:kms-visit:edit')")
    @Log(title = "访问统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KmsMainVisit kmsMainVisit)
    {
        return toAjax(kmsMainVisitService.updateKmsMainVisit(kmsMainVisit));
    }

    /**
     * 删除访问统计
     */
    @PreAuthorize("@ss.hasPermi('kms:kms-visit:remove')")
    @Log(title = "访问统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(kmsMainVisitService.deleteKmsMainVisitByIds(ids));
    }
}
