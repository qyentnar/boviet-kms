package com.ruoyi.kms.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 kms_catalog
 *
 * @author ruoyi
 * @date 2022-05-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class KmsCatalog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 目录名称 */
    @Excel(name = "目录名称")
    private String title;

    /** 父目录 */
    @Excel(name = "父目录")
    private Long parentId;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long sort;

    /** 目录描述 */
    @Excel(name = "目录描述")
    private String description;

    /** 目录封面 */
    @Excel(name = "目录封面")
    private String cover;

    /** 创建者ID */
    @ApiModelProperty("创建者ID")
    private Long createUserId;

    /** 更新者ID */
    @ApiModelProperty("更新者ID")
    private Long updateUserId;

    private Long templateId;

    private String parentName;
    
    private List<KmsCatalog> children = new ArrayList<KmsCatalog>();

}
