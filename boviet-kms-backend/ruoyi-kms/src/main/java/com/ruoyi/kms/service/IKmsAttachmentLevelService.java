package com.ruoyi.kms.service;

import java.util.List;
import com.ruoyi.kms.domain.KmsAttachmentLevel;

/**
 * 密级程度Service接口
 * 
 * @author ruoyi
 * @date 2024-08-07
 */
public interface IKmsAttachmentLevelService 
{
    /**
     * 查询密级程度
     * 
     * @param id 密级程度主键
     * @return 密级程度
     */
    public KmsAttachmentLevel selectKmsAttachmentLevelById(Long id);

    /**
     * 查询密级程度列表
     * 
     * @param kmsAttachmentLevel 密级程度
     * @return 密级程度集合
     */
    public List<KmsAttachmentLevel> selectKmsAttachmentLevelList(KmsAttachmentLevel kmsAttachmentLevel);

    /**
     * 新增密级程度
     * 
     * @param kmsAttachmentLevel 密级程度
     * @return 结果
     */
    public int insertKmsAttachmentLevel(KmsAttachmentLevel kmsAttachmentLevel);

    /**
     * 修改密级程度
     * 
     * @param kmsAttachmentLevel 密级程度
     * @return 结果
     */
    public int updateKmsAttachmentLevel(KmsAttachmentLevel kmsAttachmentLevel);

    /**
     * 批量删除密级程度
     * 
     * @param ids 需要删除的密级程度主键集合
     * @return 结果
     */
    public int deleteKmsAttachmentLevelByIds(Long[] ids);

    /**
     * 删除密级程度信息
     * 
     * @param id 密级程度主键
     * @return 结果
     */
    public int deleteKmsAttachmentLevelById(Long id);
}
