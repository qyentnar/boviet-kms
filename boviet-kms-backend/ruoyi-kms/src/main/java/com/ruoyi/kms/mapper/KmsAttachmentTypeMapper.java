package com.ruoyi.kms.mapper;

import java.util.List;
import com.ruoyi.kms.domain.KmsAttachmentType;

/**
 * 档案类型Mapper接口
 * 
 * @author ruoyi
 * @date 2024-08-07
 */
public interface KmsAttachmentTypeMapper 
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
     * 删除档案类型
     * 
     * @param id 档案类型主键
     * @return 结果
     */
    public int deleteKmsAttachmentTypeById(Long id);

    /**
     * 批量删除档案类型
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKmsAttachmentTypeByIds(Long[] ids);
}
