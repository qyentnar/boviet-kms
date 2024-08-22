package com.ruoyi.attachment.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenzh
 * @Date 1/4/22 4:45 PM
 */
@Data
public class SysAttMain extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Long id;


    /** 文件表主键ID */
    @ApiModelProperty("文件ID")
    private Long fileId;

    /** 业务ID */
    @ApiModelProperty("业务数据ID")
    private String modelId;

    /** 业务实体类路径 */
    @ApiModelProperty("业务实体类路径")
    private String modelName;

    /** 附件key;不同上传入口的key不同 */
    @ApiModelProperty("附件key")
    private String attKey;


}
