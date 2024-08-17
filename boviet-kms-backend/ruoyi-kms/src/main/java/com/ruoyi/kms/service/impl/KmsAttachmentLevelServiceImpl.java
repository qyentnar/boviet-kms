package com.ruoyi.kms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kms.mapper.KmsAttachmentLevelMapper;
import com.ruoyi.kms.domain.KmsAttachmentLevel;
import com.ruoyi.kms.service.IKmsAttachmentLevelService;

/**
 * 密级程度Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-08-07
 */
@Service
public class KmsAttachmentLevelServiceImpl implements IKmsAttachmentLevelService 
{
    @Autowired
    private KmsAttachmentLevelMapper kmsAttachmentLevelMapper;

    /**
     * 查询密级程度
     * 
     * @param id 密级程度主键
     * @return 密级程度
     */
    @Override
    public KmsAttachmentLevel selectKmsAttachmentLevelById(Long id)
    {
        return kmsAttachmentLevelMapper.selectKmsAttachmentLevelById(id);
    }

    /**
     * 查询密级程度列表
     * 
     * @param kmsAttachmentLevel 密级程度
     * @return 密级程度
     */
    @Override
    public List<KmsAttachmentLevel> selectKmsAttachmentLevelList(KmsAttachmentLevel kmsAttachmentLevel)
    {
        return kmsAttachmentLevelMapper.selectKmsAttachmentLevelList(kmsAttachmentLevel);
    }

    /**
     * 新增密级程度
     * 
     * @param kmsAttachmentLevel 密级程度
     * @return 结果
     */
    @Override
    public int insertKmsAttachmentLevel(KmsAttachmentLevel kmsAttachmentLevel)
    {
        kmsAttachmentLevel.setCreateTime(DateUtils.getNowDate());
        return kmsAttachmentLevelMapper.insertKmsAttachmentLevel(kmsAttachmentLevel);
    }

    /**
     * 修改密级程度
     * 
     * @param kmsAttachmentLevel 密级程度
     * @return 结果
     */
    @Override
    public int updateKmsAttachmentLevel(KmsAttachmentLevel kmsAttachmentLevel)
    {
        kmsAttachmentLevel.setUpdateTime(DateUtils.getNowDate());
        return kmsAttachmentLevelMapper.updateKmsAttachmentLevel(kmsAttachmentLevel);
    }

    /**
     * 批量删除密级程度
     * 
     * @param ids 需要删除的密级程度主键
     * @return 结果
     */
    @Override
    public int deleteKmsAttachmentLevelByIds(Long[] ids)
    {
        return kmsAttachmentLevelMapper.deleteKmsAttachmentLevelByIds(ids);
    }

    /**
     * 删除密级程度信息
     * 
     * @param id 密级程度主键
     * @return 结果
     */
    @Override
    public int deleteKmsAttachmentLevelById(Long id)
    {
        return kmsAttachmentLevelMapper.deleteKmsAttachmentLevelById(id);
    }
}
