package com.ruoyi.kms.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kms.mapper.KmsCustodyUnitMapper;
import com.ruoyi.kms.domain.KmsCustodyUnit;
import com.ruoyi.kms.service.IKmsCustodyUnitService;

/**
 * 保管单位Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-08-06
 */
@Service
public class KmsCustodyUnitServiceImpl implements IKmsCustodyUnitService 
{
    @Autowired
    private KmsCustodyUnitMapper kmsCustodyUnitMapper;

    /**
     * 查询保管单位
     * 
     * @param id 保管单位主键
     * @return 保管单位
     */
    @Override
    public KmsCustodyUnit selectKmsCustodyUnitById(Long id)
    {
        return kmsCustodyUnitMapper.selectKmsCustodyUnitById(id);
    }

    /**
     * 查询保管单位列表
     * 
     * @param kmsCustodyUnit 保管单位
     * @return 保管单位
     */
    @Override
    public List<KmsCustodyUnit> selectKmsCustodyUnitList(KmsCustodyUnit kmsCustodyUnit)
    {
        return kmsCustodyUnitMapper.selectKmsCustodyUnitList(kmsCustodyUnit);
    }

    /**
     * 新增保管单位
     * 
     * @param kmsCustodyUnit 保管单位
     * @return 结果
     */
    @Override
    public int insertKmsCustodyUnit(KmsCustodyUnit kmsCustodyUnit)
    {
        kmsCustodyUnit.setCreateTime(DateUtils.getNowDate());
        return kmsCustodyUnitMapper.insertKmsCustodyUnit(kmsCustodyUnit);
    }

    /**
     * 修改保管单位
     * 
     * @param kmsCustodyUnit 保管单位
     * @return 结果
     */
    @Override
    public int updateKmsCustodyUnit(KmsCustodyUnit kmsCustodyUnit)
    {
        kmsCustodyUnit.setUpdateTime(DateUtils.getNowDate());
        return kmsCustodyUnitMapper.updateKmsCustodyUnit(kmsCustodyUnit);
    }

    /**
     * 批量删除保管单位
     * 
     * @param ids 需要删除的保管单位主键
     * @return 结果
     */
    @Override
    public int deleteKmsCustodyUnitByIds(Long[] ids)
    {
        return kmsCustodyUnitMapper.deleteKmsCustodyUnitByIds(ids);
    }

    /**
     * 删除保管单位信息
     * 
     * @param id 保管单位主键
     * @return 结果
     */
    @Override
    public int deleteKmsCustodyUnitById(Long id)
    {
        return kmsCustodyUnitMapper.deleteKmsCustodyUnitById(id);
    }
}
