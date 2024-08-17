package com.ruoyi.kms.service;

import java.util.List;
import com.ruoyi.kms.domain.KmsMainVisit;

/**
 * 访问统计Service接口
 * 
 * @author ruoyi
 * @date 2024-08-09
 */
public interface IKmsMainVisitService 
{
    /**
     * 查询访问统计
     * 
     * @param id 访问统计主键
     * @return 访问统计
     */
    public KmsMainVisit selectKmsMainVisitById(Long id);

    /**
     * 查询访问统计列表
     * 
     * @param kmsMainVisit 访问统计
     * @return 访问统计集合
     */
    public List<KmsMainVisit> selectKmsMainVisitList(KmsMainVisit kmsMainVisit);

    /**
     * 新增访问统计
     * 
     * @param kmsMainVisit 访问统计
     * @return 结果
     */
    public int insertKmsMainVisit(KmsMainVisit kmsMainVisit);

    /**
     * 修改访问统计
     * 
     * @param kmsMainVisit 访问统计
     * @return 结果
     */
    public int updateKmsMainVisit(KmsMainVisit kmsMainVisit);

    /**
     * 批量删除访问统计
     * 
     * @param ids 需要删除的访问统计主键集合
     * @return 结果
     */
    public int deleteKmsMainVisitByIds(Long[] ids);

    /**
     * 删除访问统计信息
     * 
     * @param id 访问统计主键
     * @return 结果
     */
    public int deleteKmsMainVisitById(Long id);
}
