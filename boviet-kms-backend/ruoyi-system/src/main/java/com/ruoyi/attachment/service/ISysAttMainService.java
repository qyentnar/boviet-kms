package com.ruoyi.attachment.service;

import com.ruoyi.attachment.domain.SysAttFile;

import java.util.List;

/**
 * @author chenzh
 * @Date 1/4/22 1:55 PM
 */
public interface ISysAttMainService{

    /**
     * 
     * @param kmsId
     * @return
     */
    List<SysAttFile> findConverted(String kmsId);

    /**
     * 
     */
    void deleteNoUsedAttAndFile();
}
