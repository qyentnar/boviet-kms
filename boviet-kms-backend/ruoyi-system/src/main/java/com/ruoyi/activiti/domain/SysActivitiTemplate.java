package com.ruoyi.activiti.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 流程模板对象 sys_activiti_template
 *
 * @author ruoyi
 * @date 2022-09-30
 */
public class SysActivitiTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 流程模板名称 */
    @Excel(name = "流程模板名称")
    private String name;

    /** 流程说明 */
    @Excel(name = "流程说明")
    private String description;

    /** 流程定义id */
    @Excel(name = "流程定义id")
    private String processDefId;

    /** 流程key */
    @Excel(name = "流程key")
    private String processKey;

    /** 流程部署id */
    @Excel(name = "流程部署id")
    private String processDeploymentId;

    /** 流程定义json */
    @Excel(name = "流程定义json")
    private String processDefXml;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUserId;

    /** 更新人 */
    @Excel(name = "更新人")
    private Long updateUserId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setProcessDefId(String processDefId)
    {
        this.processDefId = processDefId;
    }

    public String getProcessDefId()
    {
        return processDefId;
    }
    public void setProcessKey(String processKey)
    {
        this.processKey = processKey;
    }

    public String getProcessKey()
    {
        return processKey;
    }
    public void setProcessDeploymentId(String processDeploymentId)
    {
        this.processDeploymentId = processDeploymentId;
    }

    public String getProcessDeploymentId()
    {
        return processDeploymentId;
    }
    public void setProcessDefXml(String processDefXml)
    {
        this.processDefXml = processDefXml;
    }

    public String getProcessDefXml()
    {
        return processDefXml;
    }
    public void setCreateUserId(Long createUserId)
    {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId()
    {
        return createUserId;
    }
    public void setUpdateUserId(Long updateUserId)
    {
        this.updateUserId = updateUserId;
    }

    public Long getUpdateUserId()
    {
        return updateUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("description", getDescription())
            .append("processDefId", getProcessDefId())
            .append("processKey", getProcessKey())
            .append("processDeploymentId", getProcessDeploymentId())
            .append("processDefXml", getProcessDefXml())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("createUserId", getCreateUserId())
            .append("updateUserId", getUpdateUserId())
            .toString();
    }
}
