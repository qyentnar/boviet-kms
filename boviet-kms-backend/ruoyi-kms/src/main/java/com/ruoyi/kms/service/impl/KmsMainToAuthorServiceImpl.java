package com.ruoyi.kms.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kms.mapper.KmsMainToAuthorMapper;
import com.ruoyi.kms.domain.KmsMainToAuthor;
import com.ruoyi.kms.service.IKmsMainToAuthorService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-25
 */
@Service
public class KmsMainToAuthorServiceImpl implements IKmsMainToAuthorService
{
    @Autowired
    private KmsMainToAuthorMapper kmsMainToAuthorMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param kId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public List<KmsMainToAuthor> selectKmsMainToAuthorByKId(String kId)
    {
        return kmsMainToAuthorMapper.selectKmsMainToAuthorByKId(kId);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param kmsMainToAuthor 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<KmsMainToAuthor> selectKmsMainToAuthorList(KmsMainToAuthor kmsMainToAuthor)
    {
        return kmsMainToAuthorMapper.selectKmsMainToAuthorList(kmsMainToAuthor);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param kmsMainToAuthor 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertKmsMainToAuthor(KmsMainToAuthor kmsMainToAuthor)
    {
        return kmsMainToAuthorMapper.insertKmsMainToAuthor(kmsMainToAuthor);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param kmsMainToAuthor 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateKmsMainToAuthor(KmsMainToAuthor kmsMainToAuthor)
    {
        return kmsMainToAuthorMapper.updateKmsMainToAuthor(kmsMainToAuthor);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param kIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteKmsMainToAuthorByKIds(String[] kIds)
    {
        return kmsMainToAuthorMapper.deleteKmsMainToAuthorByKIds(kIds);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param kId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteKmsMainToAuthorByKId(String kId)
    {
        return kmsMainToAuthorMapper.deleteKmsMainToAuthorByKId(kId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchAdd(String kId, List<String> authors) {
        deleteAuthor(kId);

        KmsMainToAuthor authorEntity = new KmsMainToAuthor();
        authorEntity.setkId(kId);

        for (String author : authors) {
            authorEntity.setAuthorName(author);
            authorEntity.setAuthorId(null);
            // 保存作者信息并于知识关联
            kmsMainToAuthorMapper.insertKmsMainToAuthor(authorEntity);
        }
    }

    @Override
    public void deleteAuthor(String kId) {
        kmsMainToAuthorMapper.deleteKmsMainToAuthorByKId(kId);
    }
}
