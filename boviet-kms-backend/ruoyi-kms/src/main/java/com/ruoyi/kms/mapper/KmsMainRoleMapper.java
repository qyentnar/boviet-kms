package com.ruoyi.kms.mapper;

import java.util.List;
import com.ruoyi.kms.domain.KmsMainRole;

/**
 * demoMapper接口
 * 
 * @author ruoyi
 * @date 2024-08-10
 */
public interface KmsMainRoleMapper 
{
    /**
     * 查询demo
     * 
     * @param id demo主键
     * @return demo
     */
    public KmsMainRole selectKmsMainRoleById(Long id);

    /**
     * 查询demo列表
     * 
     * @param kmsMainRole demo
     * @return demo集合
     */
    public List<KmsMainRole> selectKmsMainRoleList(KmsMainRole kmsMainRole);

    /**
     * 新增demo
     * 
     * @param kmsMainRole demo
     * @return 结果
     */
    public int insertKmsMainRole(KmsMainRole kmsMainRole);

    /**
     * 修改demo
     * 
     * @param kmsMainRole demo
     * @return 结果
     */
    public int updateKmsMainRole(KmsMainRole kmsMainRole);

    /**
     * 删除demo
     * 
     * @param id demo主键
     * @return 结果
     */
    public int deleteKmsMainRoleById(Long id);

    /**
     * 批量删除demo
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKmsMainRoleByIds(Long[] ids);
}
