package com.ruoyi.kms.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kms.mapper.KmsCatalogMapper;
import com.ruoyi.kms.domain.KmsCatalog;
import com.ruoyi.kms.service.IKmsCatalogService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-30
 */
@Service
public class KmsCatalogServiceImpl implements IKmsCatalogService
{
    @Autowired
    private KmsCatalogMapper kmsCatalogMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public KmsCatalog selectKmsCatalogById(Long id)
    {
        return kmsCatalogMapper.selectKmsCatalogById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param kmsCatalog 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<KmsCatalog> selectKmsCatalogList(KmsCatalog kmsCatalog)
    {
        return kmsCatalogMapper.selectKmsCatalogList(kmsCatalog);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param kmsCatalog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertKmsCatalog(KmsCatalog kmsCatalog)
    {
        kmsCatalog.setCreateTime(DateUtils.getNowDate());
        return kmsCatalogMapper.insertKmsCatalog(kmsCatalog);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param kmsCatalog 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateKmsCatalog(KmsCatalog kmsCatalog)
    {
        kmsCatalog.setUpdateTime(DateUtils.getNowDate());
        return kmsCatalogMapper.updateKmsCatalog(kmsCatalog);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteKmsCatalogByIds(Long[] ids)
    {
        return kmsCatalogMapper.deleteKmsCatalogByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteKmsCatalogById(Long id)
    {
        return kmsCatalogMapper.deleteKmsCatalogById(id);
    }
}
