package com.ruoyi.kms.mapper;

import java.util.List;
import com.ruoyi.kms.domain.KmsRole;

/**
 * 档案权限Mapper接口
 * 
 * @author ruoyi
 * @date 2024-08-12
 */
public interface KmsRoleMapper 
{
    /**
     * 查询档案权限
     * 
     * @param id 档案权限主键
     * @return 档案权限
     */
    public KmsRole selectKmsRoleById(Long id);

    /**
     * 
     * @param kId
     * @return
     */
    public KmsRole selectKmsRoleByKmsId(String kId);


    /**
     * 查询档案权限列表
     * 
     * @param kmsRole 档案权限
     * @return 档案权限集合
     */
    public List<KmsRole> selectKmsRoleList(KmsRole kmsRole);

    /**
     * 新增档案权限
     * 
     * @param kmsRole 档案权限
     * @return 结果
     */
    public int insertKmsRole(KmsRole kmsRole);

    /**
     * 修改档案权限
     * 
     * @param kmsRole 档案权限
     * @return 结果
     */
    public int updateKmsRole(KmsRole kmsRole);

    /**
     * 删除档案权限
     * 
     * @param id 档案权限主键
     * @return 结果
     */
    public int deleteKmsRoleById(Long id);

    /**
     * 批量删除档案权限
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKmsRoleByIds(Long[] ids);
}
