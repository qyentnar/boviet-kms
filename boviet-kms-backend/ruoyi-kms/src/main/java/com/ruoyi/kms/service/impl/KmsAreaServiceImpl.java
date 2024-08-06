package com.ruoyi.kms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kms.mapper.KmsAreaMapper;
import com.ruoyi.kms.domain.KmsArea;
import com.ruoyi.kms.service.IKmsAreaService;

/**
 * 所属区域Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-08-05
 */
@Service
public class KmsAreaServiceImpl implements IKmsAreaService 
{
    @Autowired
    private KmsAreaMapper kmsAreaMapper;

    /**
     * 查询所属区域
     * 
     * @param id 所属区域主键
     * @return 所属区域
     */
    @Override
    public KmsArea selectKmsAreaById(Long id)
    {
        return kmsAreaMapper.selectKmsAreaById(id);
    }

    /**
     * 查询所属区域列表
     * 
     * @param kmsArea 所属区域
     * @return 所属区域
     */
    @Override
    public List<KmsArea> selectKmsAreaList(KmsArea kmsArea)
    {
        return kmsAreaMapper.selectKmsAreaList(kmsArea);
    }

    /**
     * 新增所属区域
     * 
     * @param kmsArea 所属区域
     * @return 结果
     */
    @Override
    public int insertKmsArea(KmsArea kmsArea)
    {
        kmsArea.setCreateTime(DateUtils.getNowDate());
        return kmsAreaMapper.insertKmsArea(kmsArea);
    }

    /**
     * 修改所属区域
     * 
     * @param kmsArea 所属区域
     * @return 结果
     */
    @Override
    public int updateKmsArea(KmsArea kmsArea)
    {
        kmsArea.setUpdateTime(DateUtils.getNowDate());
        return kmsAreaMapper.updateKmsArea(kmsArea);
    }

    /**
     * 批量删除所属区域
     * 
     * @param ids 需要删除的所属区域主键
     * @return 结果
     */
    @Override
    public int deleteKmsAreaByIds(Long[] ids)
    {
        return kmsAreaMapper.deleteKmsAreaByIds(ids);
    }

    /**
     * 删除所属区域信息
     * 
     * @param id 所属区域主键
     * @return 结果
     */
    @Override
    public int deleteKmsAreaById(Long id)
    {
        return kmsAreaMapper.deleteKmsAreaById(id);
    }
}
