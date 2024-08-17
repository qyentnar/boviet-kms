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
import com.ruoyi.kms.domain.KmsStorageTime;
import com.ruoyi.kms.service.IKmsStorageTimeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 保管期限Controller
 * 
 * @author ruoyi
 * @date 2024-08-07
 */
@RestController
@RequestMapping("/kms/storage-time")
public class KmsStorageTimeController extends BaseController
{
    @Autowired
    private IKmsStorageTimeService kmsStorageTimeService;

    /**
     * 查询保管期限列表
     */
    @PreAuthorize("@ss.hasPermi('kms:storage-time:list')")
    @GetMapping("/list")
    public TableDataInfo list(KmsStorageTime kmsStorageTime)
    {
        startPage();
        List<KmsStorageTime> list = kmsStorageTimeService.selectKmsStorageTimeList(kmsStorageTime);
        return getDataTable(list);
    }

    /**
     * 导出保管期限列表
     */
    @PreAuthorize("@ss.hasPermi('kms:storage-time:export')")
    @Log(title = "保管期限", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KmsStorageTime kmsStorageTime)
    {
        List<KmsStorageTime> list = kmsStorageTimeService.selectKmsStorageTimeList(kmsStorageTime);
        ExcelUtil<KmsStorageTime> util = new ExcelUtil<KmsStorageTime>(KmsStorageTime.class);
        util.exportExcel(response, list, "保管期限数据");
    }

    /**
     * 获取保管期限详细信息
     */
    @PreAuthorize("@ss.hasPermi('kms:storage-time:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(kmsStorageTimeService.selectKmsStorageTimeById(id));
    }

    /**
     * 新增保管期限
     */
    @PreAuthorize("@ss.hasPermi('kms:storage-time:add')")
    @Log(title = "保管期限", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KmsStorageTime kmsStorageTime)
    {
        return toAjax(kmsStorageTimeService.insertKmsStorageTime(kmsStorageTime));
    }

    /**
     * 修改保管期限
     */
    @PreAuthorize("@ss.hasPermi('kms:storage-time:edit')")
    @Log(title = "保管期限", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KmsStorageTime kmsStorageTime)
    {
        return toAjax(kmsStorageTimeService.updateKmsStorageTime(kmsStorageTime));
    }

    /**
     * 删除保管期限
     */
    @PreAuthorize("@ss.hasPermi('kms:storage-time:remove')")
    @Log(title = "保管期限", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(kmsStorageTimeService.deleteKmsStorageTimeByIds(ids));
    }
}
