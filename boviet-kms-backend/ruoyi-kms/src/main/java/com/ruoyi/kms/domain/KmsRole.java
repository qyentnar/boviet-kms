package com.ruoyi.kms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 档案权限对象 kms_role
 * 
 * @author ruoyi
 * @date 2024-08-12
 */
public class KmsRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /**  */
    @Excel(name = "")
    private Long kmsId;

    /**  */
    @Excel(name = "")
    private String deptIds;

    /**  */
    @Excel(name = "")
    private String groupIds;

    /**  */
    @Excel(name = "")
    private String userIds;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String blackList;

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
    public void setKmsId(Long kmsId) 
    {
        this.kmsId = kmsId;
    }

    public Long getKmsId() 
    {
        return kmsId;
    }
    public void setDeptIds(String deptIds) 
    {
        this.deptIds = deptIds;
    }

    public String getDeptIds() 
    {
        return deptIds;
    }
    public void setGroupIds(String groupIds) 
    {
        this.groupIds = groupIds;
    }

    public String getGroupIds() 
    {
        return groupIds;
    }
    public void setUserIds(String userIds) 
    {
        this.userIds = userIds;
    }

    public String getUserIds() 
    {
        return userIds;
    }
    public void setBlackList(String blackList) 
    {
        this.blackList = blackList;
    }

    public String getBlackList() 
    {
        return blackList;
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
            .append("kmsId", getKmsId())
            .append("deptIds", getDeptIds())
            .append("groupIds", getGroupIds())
            .append("userIds", getUserIds())
            .append("blackList", getBlackList())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
