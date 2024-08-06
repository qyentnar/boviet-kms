package com.ruoyi.web.controller.kms;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.KmsScope;
import com.ruoyi.kms.dto.KmsMainDto;
import com.ruoyi.kms.dto.KmsSearchDto;
import com.ruoyi.kms.vo.KmsMainVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.kms.domain.KmsMain;
import com.ruoyi.kms.service.IKmsMainService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;


/**
 * 知识Controller
 *
 * @author ruoyi
 * @date 2022-05-25
 */
@RestController
@RequestMapping("/kms/main")
public class KmsMainController extends BaseController
{
    @Autowired
    private IKmsMainService kmsMainService;

    /**
     * 查询知识列表
     */
    @KmsScope
    @GetMapping("/list")
    public TableDataInfo list(KmsMainDto kmsMain)
    {
        startPage();
        List<KmsMainVo> list = kmsMainService.selectKmsMainList(kmsMain);
        return getDataTable(list);
    }

    /**
     * 查询知识列表
     */
    @KmsScope
    @GetMapping("/listTemp")
    public TableDataInfo listTemp(KmsMainDto kmsMain)
    {
        startPage();
        List<KmsMainVo> list = kmsMainService.selectKmsMainListTemp(kmsMain);
        return getDataTable(list);
    }

    @GetMapping("/listKmsTasks")
    public TableDataInfo listKmsTasks() {
        startPage();
        List<KmsMainVo> list = kmsMainService.selectKmsTaskRunning(getUsername());
        return getDataTable(list);
    }
    
    /**
     * 查询知识列表
     */
    @GetMapping("/listForEs")
    public TableDataInfo listForEs(KmsSearchDto kmsSearchDto)
    {
        startPage();
        List<KmsMain> list = kmsMainService.selectForEs(kmsSearchDto);
        return getDataTable(list);
    }

    /**
     * 导出知识列表
     */
    @Log(title = "导出知识列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, KmsMainDto kmsMain)
    {
        List<KmsMainVo> list = kmsMainService.selectKmsMainList(kmsMain);
        ExcelUtil<KmsMainVo> util = new ExcelUtil<KmsMainVo>(KmsMainVo.class);
        util.exportExcel(response, list, "知识数据");
    }

    /**
     * 获取知识详细信息
     */
    @GetMapping(value = "/get/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(kmsMainService.selectKmsMainById(id));
    }

    /**
     * 新增知识
     */
    @Log(title = "新增知识", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody KmsMainDto kmsMainDto) throws IOException {
        kmsMainDto.setCreateBy(getUsername());
        kmsMainDto.setCreateUserId(getUserId());
        return toAjax(kmsMainService.insertKmsMain(kmsMainDto));
    }

    /**
     * 修改知识
     */
    @Log(title = "修改知识", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody KmsMainDto kmsMainDto)
    {
        kmsMainDto.setUpdateBy(getUsername());
        kmsMainDto.setUpdateUserId(getUserId());
        return toAjax(kmsMainService.updateKmsMain(kmsMainDto));
    }

       /**
     * 修改知识
     */
    @Log(title = "发布知识", businessType = BusinessType.UPDATE)
    @PostMapping("/startProcess")
    public AjaxResult startProcess(@RequestBody Long id)
    {
        return toAjax(kmsMainService.startProcess(id));
    }


    /**
     * 删除知识
     */
    @Log(title = "删除知识", businessType = BusinessType.DELETE)
	@DeleteMapping("/delete/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(kmsMainService.deleteKmsMainByIds(ids));
    }

    /**
     * 获取版本列表
     */
    @GetMapping("/listVersion")
    public AjaxResult listVersion(@RequestParam Long kmsId)
    {
        List<KmsMain> list = kmsMainService.listVerson(kmsId);
        return AjaxResult.success(list);
    }
}
