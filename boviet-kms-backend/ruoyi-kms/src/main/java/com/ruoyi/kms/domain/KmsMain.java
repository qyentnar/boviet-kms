package com.ruoyi.kms.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 知识对象 kms_main
 *
 * @author ruoyi
 * @date 2022-05-25
 */
@Data
@ApiModel("kms_main")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Document(indexName = "kms_index")
public class KmsMain
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    @Id
    private Long id;

    /**
     * 知识标题
     */
    @ApiModelProperty("知识标题")
    private String title;

    /**
     * 所属目录
     */
    @ApiModelProperty("所属目录")
    private Long catalogId;

    /**
     * 所属部门
     */
    @ApiModelProperty("所属部门")
    private Long deptId;

    /**
     * 知识正文
     */
    @ApiModelProperty("知识正文")
    private String mainContent;

    /**
     * 知识摘要
     */
    @ApiModelProperty("知识摘要")
    private String summary;

    /**
     * 关键字
     */
    @ApiModelProperty("关键字")
    private String keyword;

    /**
     * 知识封面
     */
    @ApiModelProperty("知识封面")
    private String coverImg;

    /**
     * 版本号
     */
    @ApiModelProperty("版本号")
    private Integer version;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private String state;

    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;

    /**
     * 流程实例id
     */
    @ApiModelProperty("流程实例id")
    private String processInstanceId;

    /**
     * 阅读量
     */
    @ApiModelProperty("阅读量")
    private Integer readCount;

    /**
     * 附件路径
     */
    @ApiModelProperty("附件路径")
    private String filePath;

    /**
     * 附件名称
     */
    @ApiModelProperty("附件名称")
    private String fileName;

    /** 创建者ID */
    @ApiModelProperty("创建者ID")
    private Long createUserId;

    /** 更新者ID */
    @ApiModelProperty("更新者ID")
    private Long updateUserId;

    @ApiModelProperty("原始ID:指1.0的ID")
    private Long originId;

    @ApiModelProperty("是否最新版本:1-是，0-不是")
    private Integer isNewVersion;

    /** 创建者 */
    @ApiModelProperty("创建者")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    @ApiModelProperty("更新者")
    private String updateBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
