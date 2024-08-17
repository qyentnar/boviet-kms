package com.ruoyi.kms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kms.mapper.KmsStorageTimeMapper;
import com.ruoyi.kms.domain.KmsStorageTime;
import com.ruoyi.kms.service.IKmsStorageTimeService;

/**
 * 保管期限Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-08-07
 */
@Service
public class KmsStorageTimeServiceImpl implements IKmsStorageTimeService 
{
    @Autowired
    private KmsStorageTimeMapper kmsStorageTimeMapper;

    /**
     * 查询保管期限
     * 
     * @param id 保管期限主键
     * @return 保管期限
     */
    @Override
    public KmsStorageTime selectKmsStorageTimeById(Long id)
    {
        return kmsStorageTimeMapper.selectKmsStorageTimeById(id);
    }

    /**
     * 查询保管期限列表
     * 
     * @param kmsStorageTime 保管期限
     * @return 保管期限
     */
    @Override
    public List<KmsStorageTime> selectKmsStorageTimeList(KmsStorageTime kmsStorageTime)
    {
        return kmsStorageTimeMapper.selectKmsStorageTimeList(kmsStorageTime);
    }

    /**
     * 新增保管期限
     * 
     * @param kmsStorageTime 保管期限
     * @return 结果
     */
    @Override
    public int insertKmsStorageTime(KmsStorageTime kmsStorageTime)
    {
        kmsStorageTime.setCreateTime(DateUtils.getNowDate());
        return kmsStorageTimeMapper.insertKmsStorageTime(kmsStorageTime);
    }

    /**
     * 修改保管期限
     * 
     * @param kmsStorageTime 保管期限
     * @return 结果
     */
    @Override
    public int updateKmsStorageTime(KmsStorageTime kmsStorageTime)
    {
        kmsStorageTime.setUpdateTime(DateUtils.getNowDate());
        return kmsStorageTimeMapper.updateKmsStorageTime(kmsStorageTime);
    }

    /**
     * 批量删除保管期限
     * 
     * @param ids 需要删除的保管期限主键
     * @return 结果
     */
    @Override
    public int deleteKmsStorageTimeByIds(Long[] ids)
    {
        return kmsStorageTimeMapper.deleteKmsStorageTimeByIds(ids);
    }

    /**
     * 删除保管期限信息
     * 
     * @param id 保管期限主键
     * @return 结果
     */
    @Override
    public int deleteKmsStorageTimeById(Long id)
    {
        return kmsStorageTimeMapper.deleteKmsStorageTimeById(id);
    }
}
