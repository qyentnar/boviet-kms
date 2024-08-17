package com.ruoyi.kms.mapper;

import java.util.List;
import com.ruoyi.kms.domain.KmsStorageTime;

/**
 * 保管期限Mapper接口
 * 
 * @author ruoyi
 * @date 2024-08-07
 */
public interface KmsStorageTimeMapper 
{
    /**
     * 查询保管期限
     * 
     * @param id 保管期限主键
     * @return 保管期限
     */
    public KmsStorageTime selectKmsStorageTimeById(Long id);

    /**
     * 查询保管期限列表
     * 
     * @param kmsStorageTime 保管期限
     * @return 保管期限集合
     */
    public List<KmsStorageTime> selectKmsStorageTimeList(KmsStorageTime kmsStorageTime);

    /**
     * 新增保管期限
     * 
     * @param kmsStorageTime 保管期限
     * @return 结果
     */
    public int insertKmsStorageTime(KmsStorageTime kmsStorageTime);

    /**
     * 修改保管期限
     * 
     * @param kmsStorageTime 保管期限
     * @return 结果
     */
    public int updateKmsStorageTime(KmsStorageTime kmsStorageTime);

    /**
     * 删除保管期限
     * 
     * @param id 保管期限主键
     * @return 结果
     */
    public int deleteKmsStorageTimeById(Long id);

    /**
     * 批量删除保管期限
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKmsStorageTimeByIds(Long[] ids);
}
