package com.ruoyi.kms.service;

import java.util.List;
import com.ruoyi.kms.domain.KmsAttachmentType;

/**
 * 档案类型Service接口
 * 
 * @author ruoyi
 * @date 2024-08-07
 */
public interface IKmsAttachmentTypeService 
{
    /**
     * 查询档案类型
     * 
     * @param id 档案类型主键
     * @return 档案类型
     */
    public KmsAttachmentType selectKmsAttachmentTypeById(Long id);

    /**
     * 查询档案类型列表
     * 
     * @param kmsAttachmentType 档案类型
     * @return 档案类型集合
     */
    public List<KmsAttachmentType> selectKmsAttachmentTypeList(KmsAttachmentType kmsAttachmentType);

    /**
     * 新增档案类型
     * 
     * @param kmsAttachmentType 档案类型
     * @return 结果
     */
    public int insertKmsAttachmentType(KmsAttachmentType kmsAttachmentType);

    /**
     * 修改档案类型
     * 
     * @param kmsAttachmentType 档案类型
     * @return 结果
     */
    public int updateKmsAttachmentType(KmsAttachmentType kmsAttachmentType);

    /**
     * 批量删除档案类型
     * 
     * @param ids 需要删除的档案类型主键集合
     * @return 结果
     */
    public int deleteKmsAttachmentTypeByIds(Long[] ids);

    /**
     * 删除档案类型信息
     * 
     * @param id 档案类型主键
     * @return 结果
     */
    public int deleteKmsAttachmentTypeById(Long id);
}
