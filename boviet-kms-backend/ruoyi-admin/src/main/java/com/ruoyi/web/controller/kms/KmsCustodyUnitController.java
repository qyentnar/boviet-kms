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
import com.ruoyi.kms.domain.KmsCustodyUnit;
import com.ruoyi.kms.service.IKmsCustodyUnitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 保管单位Controller
 * 
 * @author ruoyi
 * @date 2024-08-06
 */
@RestController
@RequestMapping("/kms/custody-unit")
public class KmsCustodyUnitController extends BaseController
{
    @Autowired
    private IKmsCustodyUnitService kmsCustodyUnitService;

    /**
     * 查询保管单位列表
     */
    @PreAuthorize("@ss.hasPermi('kms:custody-unit:list')")
    @GetMapping("/list")
    public TableDataInfo list(KmsCustodyUnit kmsCustodyUnit)
    {
        startPage();
        List<KmsCustodyUnit> list = kmsCustodyUnitService.selectKmsCustodyUnitList(kmsCustodyUnit);
        return getDataTable(list);
    }

    /**
     * 导出保管单位列表
     */
    @PreAuthorize("@ss.hasPermi('kms:custody-unit:export')")
    @Log(title = "保管单位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KmsCustodyUnit kmsCustodyUnit)
    {
        List<KmsCustodyUnit> list = kmsCustodyUnitService.selectKmsCustodyUnitList(kmsCustodyUnit);
        ExcelUtil<KmsCustodyUnit> util = new ExcelUtil<KmsCustodyUnit>(KmsCustodyUnit.class);
        util.exportExcel(response, list, "保管单位数据");
    }

    /**
     * 获取保管单位详细信息
     */
    @PreAuthorize("@ss.hasPermi('kms:custody-unit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(kmsCustodyUnitService.selectKmsCustodyUnitById(id));
    }

    /**
     * 新增保管单位
     */
    @PreAuthorize("@ss.hasPermi('kms:custody-unit:add')")
    @Log(title = "保管单位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KmsCustodyUnit kmsCustodyUnit)
    {
        return toAjax(kmsCustodyUnitService.insertKmsCustodyUnit(kmsCustodyUnit));
    }

    /**
     * 修改保管单位
     */
    @PreAuthorize("@ss.hasPermi('kms:custody-unit:edit')")
    @Log(title = "保管单位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KmsCustodyUnit kmsCustodyUnit)
    {
        return toAjax(kmsCustodyUnitService.updateKmsCustodyUnit(kmsCustodyUnit));
    }

    /**
     * 删除保管单位
     */
    @PreAuthorize("@ss.hasPermi('kms:custody-unit:remove')")
    @Log(title = "保管单位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(kmsCustodyUnitService.deleteKmsCustodyUnitByIds(ids));
    }
}
