package com.ruoyi.attachment.mapper;

import com.ruoyi.attachment.domain.SysAttMain;
import org.apache.ibatis.annotations.Mapper;

import java.io.IOException;
import java.util.List;

/**
 * @author chenzh
 * @Date 1/5/22 10:51 AM
 */
@Mapper
public interface SysAttMainMapper{

    /**
     * 新增附件
     *
     * @param sysAttMain 附件
     * @return 结果
     */
    public int insertAttMain(SysAttMain sysAttMain);

    /**
     * 更新附件字段值
     * @param sysAttMain
     * @throws IOException
     */
    public void updateAttMain(SysAttMain sysAttMain) throws IOException;

    /**
     * 查询附件列表
     *
     * @param sysAttMain
     * @return
     */
    public List<SysAttMain> selectAttMainList(SysAttMain sysAttMain);

    /**
     * 
     * @return
     */
    List<Long> findNoUsedAttId();

    /**
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(String[] ids);

    /**
     * 
     * @param id
     * @return
     */
    public SysAttMain getAttMainById (Long fileId);
}
