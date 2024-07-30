package com.ruoyi.kms.mapper;

import java.util.List;
import com.ruoyi.kms.domain.KmsCatalog;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-30
 */
public interface KmsCatalogMapper
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
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteKmsCatalogById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKmsCatalogByIds(Long[] ids);
}
