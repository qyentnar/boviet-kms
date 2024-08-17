package com.ruoyi.kms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kms.mapper.KmsAttachmentTypeMapper;
import com.ruoyi.kms.domain.KmsAttachmentType;
import com.ruoyi.kms.service.IKmsAttachmentTypeService;

/**
 * 档案类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-08-07
 */
@Service
public class KmsAttachmentTypeServiceImpl implements IKmsAttachmentTypeService 
{
    @Autowired
    private KmsAttachmentTypeMapper kmsAttachmentTypeMapper;

    /**
     * 查询档案类型
     * 
     * @param id 档案类型主键
     * @return 档案类型
     */
    @Override
    public KmsAttachmentType selectKmsAttachmentTypeById(Long id)
    {
        return kmsAttachmentTypeMapper.selectKmsAttachmentTypeById(id);
    }

    /**
     * 查询档案类型列表
     * 
     * @param kmsAttachmentType 档案类型
     * @return 档案类型
     */
    @Override
    public List<KmsAttachmentType> selectKmsAttachmentTypeList(KmsAttachmentType kmsAttachmentType)
    {
        return kmsAttachmentTypeMapper.selectKmsAttachmentTypeList(kmsAttachmentType);
    }

    /**
     * 新增档案类型
     * 
     * @param kmsAttachmentType 档案类型
     * @return 结果
     */
    @Override
    public int insertKmsAttachmentType(KmsAttachmentType kmsAttachmentType)
    {
        kmsAttachmentType.setCreateTime(DateUtils.getNowDate());
        return kmsAttachmentTypeMapper.insertKmsAttachmentType(kmsAttachmentType);
    }

    /**
     * 修改档案类型
     * 
     * @param kmsAttachmentType 档案类型
     * @return 结果
     */
    @Override
    public int updateKmsAttachmentType(KmsAttachmentType kmsAttachmentType)
    {
        kmsAttachmentType.setUpdateTime(DateUtils.getNowDate());
        return kmsAttachmentTypeMapper.updateKmsAttachmentType(kmsAttachmentType);
    }

    /**
     * 批量删除档案类型
     * 
     * @param ids 需要删除的档案类型主键
     * @return 结果
     */
    @Override
    public int deleteKmsAttachmentTypeByIds(Long[] ids)
    {
        return kmsAttachmentTypeMapper.deleteKmsAttachmentTypeByIds(ids);
    }

    /**
     * 删除档案类型信息
     * 
     * @param id 档案类型主键
     * @return 结果
     */
    @Override
    public int deleteKmsAttachmentTypeById(Long id)
    {
        return kmsAttachmentTypeMapper.deleteKmsAttachmentTypeById(id);
    }
}
