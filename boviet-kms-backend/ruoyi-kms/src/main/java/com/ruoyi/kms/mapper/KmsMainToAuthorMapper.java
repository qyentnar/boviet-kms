package com.ruoyi.kms.mapper;

import java.util.List;
import com.ruoyi.kms.domain.KmsMainToAuthor;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-25
 */
public interface KmsMainToAuthorMapper
{
    /**
     * 查询【请填写功能名称】
     *
     * @param kId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public List<KmsMainToAuthor> selectKmsMainToAuthorByKId(String kId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param kmsMainToAuthor 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<KmsMainToAuthor> selectKmsMainToAuthorList(KmsMainToAuthor kmsMainToAuthor);

    /**
     * 新增【请填写功能名称】
     *
     * @param kmsMainToAuthor 【请填写功能名称】
     * @return 结果
     */
    public int insertKmsMainToAuthor(KmsMainToAuthor kmsMainToAuthor);

    /**
     * 修改【请填写功能名称】
     *
     * @param kmsMainToAuthor 【请填写功能名称】
     * @return 结果
     */
    public int updateKmsMainToAuthor(KmsMainToAuthor kmsMainToAuthor);

    /**
     * 删除【请填写功能名称】
     *
     * @param kId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteKmsMainToAuthorByKId(String kId);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param kIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKmsMainToAuthorByKIds(String[] kIds);
}
