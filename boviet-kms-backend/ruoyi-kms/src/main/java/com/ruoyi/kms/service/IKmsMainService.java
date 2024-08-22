package com.ruoyi.kms.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.kms.domain.KmsMain;
import com.ruoyi.kms.domain.KmsMainVisit;
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
    public KmsMainVo selectKmsMainById(String id);

    /**
     * 
     * @param id
     * @param username
     * @return
     */
    public KmsMainVo selectKmsMainByAuthor(String id, SysUser sysUser);

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
    public List<KmsMainVo> selectKmsTaskRunning(KmsMainDto kmsMain, SysUser sysUser);

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
    public int deleteKmsMainByIds(String[] ids);

    /**
     * 删除知识信息
     *
     * @param id 知识主键
     * @return 结果
     */
    public int deleteKmsMainById(String id);

    /**
     * 知识检索查询
     * @param kmsSearchDto
     * @return
     */
    public List<KmsMain> selectForEs(KmsSearchDto kmsSearchDto);
    
    /**
     * 
     * @param id
     * @return
     */
    public List<KmsMain> listVersion(String id);

    /**
     * 
     * @param id
     * @return
     */
    public int startProcess(String id);

    /**
     * 
     * @param kmsMainDto
     * @return
     */
    public int kmsReader(KmsMainDto kmsMainDto, KmsMainVisit kmsMainVisit);

    /**
     * 
     * @param sysUser
     * @return
     */
    public List<Map<String, Object>> getHistoryApprove(SysUser sysUser);
}
