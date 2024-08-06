package com.ruoyi.kms.service;

import java.io.IOException;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.kms.domain.KmsMain;
import com.ruoyi.kms.vo.KmsMainVo;
import com.ruoyi.kms.dto.KmsMainDto;
import com.ruoyi.kms.dto.KmsSearchDto;

/**
 * 知识Service接口
 *
 * @author ruoyi
 * @date 2022-05-25
 */
public interface IKmsMainService extends IService<KmsMain>
{
    /**
     * 查询知识
     *
     * @param id 知识主键
     * @return 知识
     */
    public KmsMainVo selectKmsMainById(Long id);

    /**
     * 查询知识列表
     *
     * @param kmsMain 知识
     * @return 知识集合
     */
    public List<KmsMainVo> selectKmsMainList(KmsMainDto kmsMain);

        /**
     * 查询知识列表
     *
     * @param kmsMain 知识
     * @return 知识集合
     */
    public List<KmsMainVo> selectKmsMainListTemp(KmsMainDto kmsMain);

    /**
     * 
     * @param sysUser
     * @return
     */
    public List<KmsMainVo> selectKmsTaskRunning(String userName);

    /**
     * 新增知识
     *
     * @param kmsMainDto 知识
     * @return 结果
     */
    public int insertKmsMain(KmsMainDto kmsMainDto) throws IOException;

    /**
     * 修改知识
     *
     * @param kmsMainDto 知识
     * @return 结果
     */
    public int updateKmsMain(KmsMainDto kmsMainDto);

    /**
     * 
     * @param kmsMainDto
     * @return
     */
    public int updateKmsMain2(KmsMainDto kmsMainDto);

    /**
     * 批量删除知识
     *
     * @param ids 需要删除的知识主键集合
     * @return 结果
     */
    public int deleteKmsMainByIds(Long[] ids);

    /**
     * 删除知识信息
     *
     * @param id 知识主键
     * @return 结果
     */
    public int deleteKmsMainById(Long id);

    /**
     * 知识检索查询
     * @param kmsSearchDto
     * @return
     */
    public List<KmsMain> selectForEs(KmsSearchDto kmsSearchDto);

    public List<KmsMain> listVerson(Long id);

    public int startProcess(Long id);
}
