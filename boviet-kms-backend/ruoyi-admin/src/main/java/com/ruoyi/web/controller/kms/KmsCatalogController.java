package com.ruoyi.web.controller.kms;

import java.util.Iterator;
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
import com.ruoyi.kms.domain.KmsCatalog;
import com.ruoyi.kms.service.IKmsCatalogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;


/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2022-05-30
 */
@RestController
@RequestMapping("/kms/catalog")
public class KmsCatalogController extends BaseController
{
    @Autowired
    private IKmsCatalogService kmsCatalogService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('kms:catalog:list')")
    @GetMapping("/list")
    public TableDataInfo list(KmsCatalog kmsCatalog)
    {
        startPage();
        List<KmsCatalog> list = kmsCatalogService.selectKmsCatalogList(kmsCatalog);
        return getDataTable(list);
    }

    /**
     * 获取树列表
     */
    @GetMapping("/listForTree")
    public AjaxResult listForTree(KmsCatalog kmsCatalog)
    {
        List<KmsCatalog> list = kmsCatalogService.selectKmsCatalogList(kmsCatalog);
        return AjaxResult.success(list);
    }

    /**
     * 查询列表（排除节点）
     */
    @GetMapping("/list/exclude/{catalogId}")
    public AjaxResult excludeChild(@PathVariable(value = "catalogId", required = false) Long catalogId)
    {
        List<KmsCatalog> list = kmsCatalogService.selectKmsCatalogList(new KmsCatalog());
        Iterator<KmsCatalog> it = list.iterator();
        while (it.hasNext())
        {
            KmsCatalog d = (KmsCatalog) it.next();
            if (d.getId().intValue() == catalogId)
            {
                it.remove();
            }
        }
        return AjaxResult.success(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('kms:catalog:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KmsCatalog kmsCatalog)
    {
        List<KmsCatalog> list = kmsCatalogService.selectKmsCatalogList(kmsCatalog);
        ExcelUtil<KmsCatalog> util = new ExcelUtil<KmsCatalog>(KmsCatalog.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('kms:catalog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(kmsCatalogService.selectKmsCatalogById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('kms:catalog:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KmsCatalog kmsCatalog)
    {
        kmsCatalog.setCreateBy(getUsername());
        kmsCatalog.setCreateUserId(getUserId());
        return toAjax(kmsCatalogService.insertKmsCatalog(kmsCatalog));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('kms:catalog:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KmsCatalog kmsCatalog)
    {
        kmsCatalog.setUpdateBy(getUsername());
        kmsCatalog.setUpdateUserId(getUserId());
        return toAjax(kmsCatalogService.updateKmsCatalog(kmsCatalog));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('kms:catalog:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(kmsCatalogService.deleteKmsCatalogByIds(ids));
    }

    @GetMapping("/tree-select")
    public AjaxResult treeSelect(KmsCatalog catalog) {
        List<KmsCatalog> list = kmsCatalogService.selectKmsCatalogList(catalog);
        return AjaxResult.success(kmsCatalogService.buildCatalogTreeSelect(list)); //
    }
    
}
