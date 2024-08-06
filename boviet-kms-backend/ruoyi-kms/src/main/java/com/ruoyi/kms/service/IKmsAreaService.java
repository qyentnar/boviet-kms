package com.ruoyi.kms.service;

import java.util.List;
import com.ruoyi.kms.domain.KmsArea;

/**
 * 所属区域Service接口
 * 
 * @author ruoyi
 * @date 2024-08-05
 */
public interface IKmsAreaService 
{
    /**
     * 查询所属区域
     * 
     * @param id 所属区域主键
     * @return 所属区域
     */
    public KmsArea selectKmsAreaById(Long id);

    /**
     * 查询所属区域列表
     * 
     * @param kmsArea 所属区域
     * @return 所属区域集合
     */
    public List<KmsArea> selectKmsAreaList(KmsArea kmsArea);

    /**
     * 新增所属区域
     * 
     * @param kmsArea 所属区域
     * @return 结果
     */
    public int insertKmsArea(KmsArea kmsArea);

    /**
     * 修改所属区域
     * 
     * @param kmsArea 所属区域
     * @return 结果
     */
    public int updateKmsArea(KmsArea kmsArea);

    /**
     * 批量删除所属区域
     * 
     * @param ids 需要删除的所属区域主键集合
     * @return 结果
     */
    public int deleteKmsAreaByIds(Long[] ids);

    /**
     * 删除所属区域信息
     * 
     * @param id 所属区域主键
     * @return 结果
     */
    public int deleteKmsAreaById(Long id);
}
