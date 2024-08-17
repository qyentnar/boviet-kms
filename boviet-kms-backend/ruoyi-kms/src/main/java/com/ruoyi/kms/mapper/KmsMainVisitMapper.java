package com.ruoyi.kms.mapper;

import java.util.List;
import com.ruoyi.kms.domain.KmsMainVisit;
import com.ruoyi.kms.vo.KmsMainVisitVo;

/**
 * 访问统计Mapper接口
 * 
 * @author ruoyi
 * @date 2024-08-09
 */
public interface KmsMainVisitMapper 
{
    /**
     * 查询访问统计
     * 
     * @param id 访问统计主键
     * @return 访问统计
     */
    public KmsMainVisit selectKmsMainVisitById(Long id);

    /**
     * 
     * @param userName
     * @return
     */
    public KmsMainVisitVo selectKmsMainVisitByUserVisited(KmsMainVisit kmsMainVisit);

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
     * 删除访问统计
     * 
     * @param id 访问统计主键
     * @return 结果
     */
    public int deleteKmsMainVisitById(Long id);

    /**
     * 批量删除访问统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKmsMainVisitByIds(Long[] ids);
}
