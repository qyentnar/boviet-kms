package com.ruoyi.kms.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.kms.domain.KmsCatalog;
import com.ruoyi.kms.dto.KmsTreeSelect;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2022-05-30
 */
public interface IKmsCatalogService
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public KmsCatalog selectKmsCatalogById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param kmsCatalog 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<KmsCatalog> selectKmsCatalogList(KmsCatalog kmsCatalog);

    /**
     * 新增【请填写功能名称】
     *
     * @param kmsCatalog 【请填写功能名称】
     * @return 结果
     */
    public int insertKmsCatalog(KmsCatalog kmsCatalog);

    /**
     * 修改【请填写功能名称】
     *
     * @param kmsCatalog 【请填写功能名称】
     * @return 结果
     */
    public int updateKmsCatalog(KmsCatalog kmsCatalog);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteKmsCatalogByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteKmsCatalogById(Long id);


    public List<KmsTreeSelect> buildCatalogTreeSelect(List<KmsCatalog> catalogs);

    public List<KmsCatalog> buildCatalogTree(List<KmsCatalog> catalogs);
}
