package com.ruoyi.kms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kms.mapper.KmsMainVisitMapper;
import com.ruoyi.kms.domain.KmsMainVisit;
import com.ruoyi.kms.service.IKmsMainVisitService;

/**
 * 访问统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-08-09
 */
@Service
public class KmsMainVisitServiceImpl implements IKmsMainVisitService 
{
    @Autowired
    private KmsMainVisitMapper kmsMainVisitMapper;

    /**
     * 查询访问统计
     * 
     * @param id 访问统计主键
     * @return 访问统计
     */
    @Override
    public KmsMainVisit selectKmsMainVisitById(Long id)
    {
        return kmsMainVisitMapper.selectKmsMainVisitById(id);
    }

    /**
     * 查询访问统计列表
     * 
     * @param kmsMainVisit 访问统计
     * @return 访问统计
     */
    @Override
    public List<KmsMainVisit> selectKmsMainVisitList(KmsMainVisit kmsMainVisit)
    {
        return kmsMainVisitMapper.selectKmsMainVisitList(kmsMainVisit);
    }

    /**
     * 新增访问统计
     * 
     * @param kmsMainVisit 访问统计
     * @return 结果
     */
    @Override
    public int insertKmsMainVisit(KmsMainVisit kmsMainVisit)
    {
        kmsMainVisit.setCreateTime(DateUtils.getNowDate());
        return kmsMainVisitMapper.insertKmsMainVisit(kmsMainVisit);
    }

    /**
     * 修改访问统计
     * 
     * @param kmsMainVisit 访问统计
     * @return 结果
     */
    @Override
    public int updateKmsMainVisit(KmsMainVisit kmsMainVisit)
    {
        kmsMainVisit.setUpdateTime(DateUtils.getNowDate());
        return kmsMainVisitMapper.updateKmsMainVisit(kmsMainVisit);
    }

    /**
     * 批量删除访问统计
     * 
     * @param ids 需要删除的访问统计主键
     * @return 结果
     */
    @Override
    public int deleteKmsMainVisitByIds(Long[] ids)
    {
        return kmsMainVisitMapper.deleteKmsMainVisitByIds(ids);
    }

    /**
     * 删除访问统计信息
     * 
     * @param id 访问统计主键
     * @return 结果
     */
    @Override
    public int deleteKmsMainVisitById(Long id)
    {
        return kmsMainVisitMapper.deleteKmsMainVisitById(id);
    }
}
