package com.ruoyi.kms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 知识 入参KmsMainDto
 *
 * @author ruoyi
 * @date 2022-05-25
 */
@SuppressWarnings("unused")
@Data
@EqualsAndHashCode(callSuper = false)
public class KmsMainDto extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
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
    @ApiModelProperty("状态：10-草稿，11-驳回, 20-待审，30-发布，40-过期，50-删除")
    private String state;

    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;

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

    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 附件main-id数据
     */
    @ApiModelProperty("附件ID")
    private String attMainIds;

    /** 创建者ID */
    @ApiModelProperty("创建者ID")
    private Long createUserId;

    /** 更新者ID */
    @ApiModelProperty("更新者ID")
    private Long updateUserId;

    private List<String> extAuthor;

    private Long originId;

    @ApiModelProperty("是否最新版本:1-是，0-不是")
    private Integer isNewVersion;

    @ApiModelProperty("操作：0-暂存，1-提交，2-新版本暂存, 3-新版本提交")
    private Integer operation;

    @ApiModelProperty("当前最新版本id")
    private Long currentVersionId;

    /** 档案类型 */
    @ApiModelProperty("档案类型")
    private String attType;

    /** 所属区域 */
    @ApiModelProperty("所属区域")
    private String area;

    /** 归档人 */
    @ApiModelProperty("归档人")
    private String archiver;

    /** 档案总称 */
    @ApiModelProperty("档案总称")
    private String generalName;

    /** 档案编号 */
    @ApiModelProperty("档案编号")
    private String attCode;

    /** 历史档案编号 */
    @ApiModelProperty("历史档案编号")
    private String attCodeH;

    /** 资料形成日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("资料形成日期")
    private Date attCreateTime;

    /** 保管期限 */
    @ApiModelProperty("保管期限")
    private Long storageTime;

    /** 档案有效期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("档案有效期")
    private Date attExpirationTime;

    /**  箱号  */
    @ApiModelProperty(" 箱号 ")
    private String boxNo;

    /**  盒号  */
    @ApiModelProperty(" 盒号 ")
    private String boxesNo;

    /** 案卷号 */
    @ApiModelProperty("案卷号")
    private String attNumber;

    /** 卷内序号 */
    @ApiModelProperty("卷内序号")
    private String attSerialNumber;

    /** 目录号 */
    @ApiModelProperty("目录号")
    private String catalogNumber;

    /** 密级程度（1：秘密，2：机密，3：绝密） */
    @ApiModelProperty("密级程度")
    private Long attClassification;

    /** 件数/本 */
    @ApiModelProperty("件数/本")
    private String attCount;

    /** 页数/页码 */
    @ApiModelProperty("页数/页码")
    private String pageNumber;

    /** 保管单位 */
    @ApiModelProperty("保管单位")
    private String custodyUnit;

    /** 外部相关单位 */
    @ApiModelProperty("外部相关单位")
    private String externalUnit;

    /** 移交档案部门 */
    @ApiModelProperty("移交档案部门")
    private String filingDept;

    /** 备注 */
    @ApiModelProperty("备注")
    private String marks;

    /** 请求参数 */
    private Map<String, Object> params;


}
