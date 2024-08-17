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
import com.ruoyi.kms.domain.KmsAttachmentType;
import com.ruoyi.kms.service.IKmsAttachmentTypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 档案类型Controller
 * 
 * @author ruoyi
 * @date 2024-08-07
 */
@RestController
@RequestMapping("/kms/attachment-type")
public class KmsAttachmentTypeController extends BaseController
{
    @Autowired
    private IKmsAttachmentTypeService kmsAttachmentTypeService;

    /**
     * 查询档案类型列表
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-type:list')")
    @GetMapping("/list")
    public TableDataInfo list(KmsAttachmentType kmsAttachmentType)
    {
        startPage();
        List<KmsAttachmentType> list = kmsAttachmentTypeService.selectKmsAttachmentTypeList(kmsAttachmentType);
        return getDataTable(list);
    }

    /**
     * 导出档案类型列表
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-type:export')")
    @Log(title = "档案类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KmsAttachmentType kmsAttachmentType)
    {
        List<KmsAttachmentType> list = kmsAttachmentTypeService.selectKmsAttachmentTypeList(kmsAttachmentType);
        ExcelUtil<KmsAttachmentType> util = new ExcelUtil<KmsAttachmentType>(KmsAttachmentType.class);
        util.exportExcel(response, list, "档案类型数据");
    }

    /**
     * 获取档案类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-type:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(kmsAttachmentTypeService.selectKmsAttachmentTypeById(id));
    }

    /**
     * 新增档案类型
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-type:add')")
    @Log(title = "档案类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KmsAttachmentType kmsAttachmentType)
    {
        return toAjax(kmsAttachmentTypeService.insertKmsAttachmentType(kmsAttachmentType));
    }

    /**
     * 修改档案类型
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-type:edit')")
    @Log(title = "档案类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KmsAttachmentType kmsAttachmentType)
    {
        return toAjax(kmsAttachmentTypeService.updateKmsAttachmentType(kmsAttachmentType));
    }

    /**
     * 删除档案类型
     */
    @PreAuthorize("@ss.hasPermi('kms:attachment-type:remove')")
    @Log(title = "档案类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(kmsAttachmentTypeService.deleteKmsAttachmentTypeByIds(ids));
    }
}
