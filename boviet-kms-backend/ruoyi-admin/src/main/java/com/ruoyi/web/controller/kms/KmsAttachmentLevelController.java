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
import com.ruoyi.kms.domain.KmsAttachmentLevel;
import com.ruoyi.kms.service.IKmsAttachmentLevelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 密级程度Controller
 * 
 * @author ruoyi
 * @date 2024-08-07
 */
@RestController
@RequestMapping("/kms/attachment-level")
public class KmsAttachmentLevelController extends BaseController
{
    @Autowired
    private IKmsAttachmentLevelService kmsAttachmentLevelService;

    /**
     * 查询密级程度列表
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-level:list')")
    @GetMapping("/list")
    public TableDataInfo list(KmsAttachmentLevel kmsAttachmentLevel)
    {
        startPage();
        List<KmsAttachmentLevel> list = kmsAttachmentLevelService.selectKmsAttachmentLevelList(kmsAttachmentLevel);
        return getDataTable(list);
    }

    /**
     * 导出密级程度列表
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-level:export')")
    @Log(title = "密级程度", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KmsAttachmentLevel kmsAttachmentLevel)
    {
        List<KmsAttachmentLevel> list = kmsAttachmentLevelService.selectKmsAttachmentLevelList(kmsAttachmentLevel);
        ExcelUtil<KmsAttachmentLevel> util = new ExcelUtil<KmsAttachmentLevel>(KmsAttachmentLevel.class);
        util.exportExcel(response, list, "密级程度数据");
    }

    /**
     * 获取密级程度详细信息
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-level:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(kmsAttachmentLevelService.selectKmsAttachmentLevelById(id));
    }

    /**
     * 新增密级程度
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-level:add')")
    @Log(title = "密级程度", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KmsAttachmentLevel kmsAttachmentLevel)
    {
        return toAjax(kmsAttachmentLevelService.insertKmsAttachmentLevel(kmsAttachmentLevel));
    }

    /**
     * 修改密级程度
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-level:edit')")
    @Log(title = "密级程度", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KmsAttachmentLevel kmsAttachmentLevel)
    {
        return toAjax(kmsAttachmentLevelService.updateKmsAttachmentLevel(kmsAttachmentLevel));
    }

    /**
     * 删除密级程度
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-level:remove')")
    @Log(title = "密级程度", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(kmsAttachmentLevelService.deleteKmsAttachmentLevelByIds(ids));
    }
}
