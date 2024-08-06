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
import com.ruoyi.kms.domain.KmsArea;
import com.ruoyi.kms.service.IKmsAreaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 所属区域Controller
 * 
 * @author ruoyi
 * @date 2024-08-05
 */
@RestController
@RequestMapping("/kms/area")
public class KmsAreaController extends BaseController
{
    @Autowired
    private IKmsAreaService kmsAreaService;

    /**
     * 查询所属区域列表
     */
    @PreAuthorize("@ss.hasPermi('kms:area:list')")
    @GetMapping("/list")
    public TableDataInfo list(KmsArea kmsArea)
    {
        startPage();
        List<KmsArea> list = kmsAreaService.selectKmsAreaList(kmsArea);
        return getDataTable(list);
    }

    /**
     * 导出所属区域列表
     */
    @PreAuthorize("@ss.hasPermi('kms:area:export')")
    @Log(title = "所属区域", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KmsArea kmsArea)
    {
        List<KmsArea> list = kmsAreaService.selectKmsAreaList(kmsArea);
        ExcelUtil<KmsArea> util = new ExcelUtil<KmsArea>(KmsArea.class);
        util.exportExcel(response, list, "所属区域数据");
    }

    /**
     * 获取所属区域详细信息
     */
    @PreAuthorize("@ss.hasPermi('kms:area:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(kmsAreaService.selectKmsAreaById(id));
    }

    /**
     * 新增所属区域
     */
    @PreAuthorize("@ss.hasPermi('kms:area:add')")
    @Log(title = "所属区域", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KmsArea kmsArea)
    {
        return toAjax(kmsAreaService.insertKmsArea(kmsArea));
    }

    /**
     * 修改所属区域
     */
    @PreAuthorize("@ss.hasPermi('kms:area:edit')")
    @Log(title = "所属区域", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KmsArea kmsArea)
    {
        return toAjax(kmsAreaService.updateKmsArea(kmsArea));
    }

    /**
     * 删除所属区域
     */
    @PreAuthorize("@ss.hasPermi('kms:area:remove')")
    @Log(title = "所属区域", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(kmsAreaService.deleteKmsAreaByIds(ids));
    }
}
