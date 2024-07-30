package com.ruoyi.attachment.mapper;

import com.ruoyi.attachment.domain.SysAttFile;
import com.ruoyi.attachment.domain.SysAttMain;
import org.apache.ibatis.annotations.Mapper;

import java.io.IOException;
import java.util.List;

/**
 * @author chenzh
 * @Date 1/5/22 11:04 AM
 */
@Mapper
public interface SysAttFileMapper{

    /**
     * 查询md5相同的文件数量
     * @param md5
     * @return
     */
    public SysAttFile selectAttachmentsByMd5(String md5);

    /**
     * 新增附件
     *
     * @param sysAttFile 附件
     * @return 结果
     */
    public int insertAttFile(SysAttFile sysAttFile);

    /**
     * 更新附件字段值
     * @param sysAttFile
     * @throws IOException
     */
    public void updateAttFile(SysAttFile sysAttFile) throws IOException;

    /**
     * 更新主表converted字段值
     * @param sysAttFile
     * @throws IOException
     */
    public void updateKMSConverted(SysAttFile sysAttFile) throws IOException;


    /**
     * 查询附件预览列表
     *
     * @param sysAttMain
     * @return
     */
    public List<SysAttFile> selectAttConvertedList(SysAttMain sysAttMain);


    /**
     * 
     * @param ids
     * @return
     */
    public int deleteByIds(Long[] ids);

}
