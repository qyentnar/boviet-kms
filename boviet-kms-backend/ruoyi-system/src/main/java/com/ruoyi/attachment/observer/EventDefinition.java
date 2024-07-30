package com.ruoyi.attachment.observer;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author chenzh
 * @Date 1/10/22 1:56 PM
 */
@Data
public class EventDefinition {

    /**
     * 文件传输对象
     */
    private MultipartFile multipartFile;

    /**
     * 文件表(sys_att_file)主键ID
     */
    private Long attFileId;

    /**
     * 当前登录用户ID
     */
    private String userId;

    private String directory;

}
