package com.ruoyi.kms.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.kms.domain.KmsMain;
import com.ruoyi.kms.dto.KmsMainDto;
import com.ruoyi.kms.dto.KmsMainUserDto;
import com.ruoyi.kms.vo.KmsMainVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-25
 */
@Mapper
public interface KmsMainMapper extends BaseMapper<KmsMain>
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public KmsMainVo selectKmsMainById(Long id);

    /**
     * 
     * @param userId
     * @return
     */
    public KmsMainVo selectKmsMainByUserId(Long userId);
    /**
     * 
     * @param processInstanceId
     * @return
     */
    public KmsMainVo selectKmsMainByProcessIntanceId(String processInstanceId);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param kmsMain 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<KmsMainVo> selectKmsMainList(KmsMainDto kmsMain);

    /**
     * 新增【请填写功能名称】
     *
     * @param kmsMainDto 【请填写功能名称】
     * @return 结果
     */
    public int insertKmsMain(KmsMainDto kmsMainDto);

    /**
     * 修改【请填写功能名称】
     *
     * @param kmsMain 【请填写功能名称】
     * @return 结果
     */
    public int updateKmsMain(KmsMain kmsMain);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteKmsMainById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteKmsMainByIds(Long[] ids);

    /**
     * 批量增加可阅读者
     *
     * @param readerDto
     * @return
     */
    void bachAddMainReaders(KmsMainUserDto readerDto);

    /**
     * 查询已存在可阅读者
     * @param knowId
     * @return
     */
    List<String> selectUserIdsByParam(@Param("knowId") Long knowId);
}
