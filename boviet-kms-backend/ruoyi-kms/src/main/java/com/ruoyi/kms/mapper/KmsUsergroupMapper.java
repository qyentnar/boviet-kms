package com.ruoyi.kms.mapper;

import java.util.List;
import com.ruoyi.kms.domain.KmsUsergroup;

/**
 * demoMapper接口
 * 
 * @author ruoyi
 * @date 2024-08-12
 */
public interface KmsUsergroupMapper 
{
    /**
     * 查询demo
     * 
     * @param id demo主键
     * @return demo
     */
    public KmsUsergroup selectKmsUsergroupById(Long id);

    /**
     * 查询demo列表
     * 
     * @param kmsUsergroup demo
     * @return demo集合
     */
    public List<KmsUsergroup> selectKmsUsergroupList(KmsUsergroup kmsUsergroup);

    /**
     * 新增demo
     * 
     * @param kmsUsergroup demo
     * @return 结果
     */
    public int insertKmsUsergroup(KmsUsergroup kmsUsergroup);

    /**
     * 修改demo
     * 
     * @param kmsUsergroup demo
     * @return 结果
     */
    public int updateKmsUsergroup(KmsUsergroup kmsUsergroup);

    /**
     * 删除demo
     * 
     * @param id demo主键
     * @return 结果
     */
    public int deleteKmsUsergroupById(Long id);

    /**
     * 批量删除demo
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKmsUsergroupByIds(Long[] ids);
}
