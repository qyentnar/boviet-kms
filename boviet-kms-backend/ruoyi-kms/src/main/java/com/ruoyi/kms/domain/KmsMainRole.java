package com.ruoyi.kms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * demo对象 kms_main_role
 * 
 * @author ruoyi
 * @date 2024-08-10
 */
public class KmsMainRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /**  */
    @Excel(name = "")
    private Long groupId;

    /**  */
    @Excel(name = "")
    private String userIds;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String kmsIds;

    /** 删除标志（0代表存在 1代表删除） */
    private Integer delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setUserIds(String userIds) 
    {
        this.userIds = userIds;
    }

    public String getUserIds() 
    {
        return userIds;
    }
    public void setKmsIds(String kmsIds) 
    {
        this.kmsIds = kmsIds;
    }

    public String getKmsIds() 
    {
        return kmsIds;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("groupId", getGroupId())
            .append("userIds", getUserIds())
            .append("kmsIds", getKmsIds())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
