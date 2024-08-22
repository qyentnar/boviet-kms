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
import com.ruoyi.kms.domain.KmsMainToAuthor;
import com.ruoyi.kms.service.IKmsMainToAuthorService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 *
 * @author ruoyi
 * @date 2022-05-25
 */
@RestController
@RequestMapping("/kms/author")
public class KmsMainToAuthorController extends BaseController
{
    @Autowired
    private IKmsMainToAuthorService kmsMainToAuthorService;

    /**
     * 查询【请填写功能名称】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(KmsMainToAuthor kmsMainToAuthor)
    {
        startPage();
        List<KmsMainToAuthor> list = kmsMainToAuthorService.selectKmsMainToAuthorList(kmsMainToAuthor);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KmsMainToAuthor kmsMainToAuthor)
    {
        List<KmsMainToAuthor> list = kmsMainToAuthorService.selectKmsMainToAuthorList(kmsMainToAuthor);
        ExcelUtil<KmsMainToAuthor> util = new ExcelUtil<KmsMainToAuthor>(KmsMainToAuthor.class);
        util.exportExcel(response, list, "【请填写功能名称】数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @GetMapping(value = "/{kId}")
    public AjaxResult getInfo(@PathVariable("kId") String kId)
    {
        return AjaxResult.success(kmsMainToAuthorService.selectKmsMainToAuthorByKId(kId));
    }

    /**
     * 新增【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody KmsMainToAuthor kmsMainToAuthor)
    {
        return toAjax(kmsMainToAuthorService.insertKmsMainToAuthor(kmsMainToAuthor));
    }

    /**
     * 修改【请填写功能名称】
     */
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody KmsMainToAuthor kmsMainToAuthor)
    {
        return toAjax(kmsMainToAuthorService.updateKmsMainToAuthor(kmsMainToAuthor));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('kms:author:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{kIds}")
    public AjaxResult remove(@PathVariable String[] kIds)
    {
        return toAjax(kmsMainToAuthorService.deleteKmsMainToAuthorByKIds(kIds));
    }
}
