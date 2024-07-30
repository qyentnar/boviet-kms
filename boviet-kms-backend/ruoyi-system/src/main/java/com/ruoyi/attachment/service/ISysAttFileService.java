package com.ruoyi.attachment.service;

import com.ruoyi.attachment.domain.SysAttFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author chenzh
 * @Date 1/5/22 11:01 AM
 */
public interface ISysAttFileService{

    Map<String,String> upload(String baseDir, MultipartFile file, String directory) throws IOException;

    void updateAttFile(SysAttFile sysAttFile) throws IOException;

}
