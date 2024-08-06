package com.ruoyi.kms.mapper;

import java.util.List;
import com.ruoyi.kms.domain.KmsCustodyUnit;

/**
 * 保管单位Mapper接口
 * 
 * @author ruoyi
 * @date 2024-08-06
 */
public interface KmsCustodyUnitMapper 
{
    /**
     * 查询保管单位
     * 
     * @param id 保管单位主键
     * @return 保管单位
     */
    public KmsCustodyUnit selectKmsCustodyUnitById(Long id);

    /**
     * 查询保管单位列表
     * 
     * @param kmsCustodyUnit 保管单位
     * @return 保管单位集合
     */
    public List<KmsCustodyUnit> selectKmsCustodyUnitList(KmsCustodyUnit kmsCustodyUnit);

    /**
     * 新增保管单位
     * 
     * @param kmsCustodyUnit 保管单位
     * @return 结果
     */
    public int insertKmsCustodyUnit(KmsCustodyUnit kmsCustodyUnit);

    /**
     * 修改保管单位
     * 
     * @param kmsCustodyUnit 保管单位
     * @return 结果
     */
    public int updateKmsCustodyUnit(KmsCustodyUnit kmsCustodyUnit);

    /**
     * 删除保管单位
     * 
     * @param id 保管单位主键
     * @return 结果
     */
    public int deleteKmsCustodyUnitById(Long id);

    /**
     * 批量删除保管单位
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKmsCustodyUnitByIds(Long[] ids);
}
