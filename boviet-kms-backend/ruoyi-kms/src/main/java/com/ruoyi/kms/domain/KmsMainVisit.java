package com.ruoyi.kms.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 访问统计对象 kms_main_visit
 * 
 * @author ruoyi
 * @date 2024-08-09
 */
public class KmsMainVisit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 知识ID */
    @Excel(name = "知识ID")
    private Long kmsId;

    /** 用户 */
    @Excel(name = "用户")
    private String userVisited;

    /** 访问次数 */
    @Excel(name = "访问次数")
    private Long visitCount;

    /** 投票 */
    @Excel(name = "投票")
    private Long vote;

    /** 阅读阶段 */
    @Excel(name = "阅读阶段")
    private String readStage;

    /** 访问时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date visitTime;

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
    public void setUserVisited(String userVisited) 
    {
        this.userVisited = userVisited;
    }

    public String getUserVisited() 
    {
        return userVisited;
    }
    public void setVisitCount(Long visitCount) 
    {
        this.visitCount = visitCount;
    }

    public Long getVisitCount() 
    {
        return visitCount;
    }
    public void setVote(Long vote) 
    {
        this.vote = vote;
    }

    public Long getVote() 
    {
        return vote;
    }
    public void setReadStage(String readStage) 
    {
        this.readStage = readStage;
    }

    public String getReadStage() 
    {
        return readStage;
    }
    public void setVisitTime(Date visitTime) 
    {
        this.visitTime = visitTime;
    }

    public Date getVisitTime() 
    {
        return visitTime;
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
            .append("userVisited", getUserVisited())
            .append("visitCount", getVisitCount())
            .append("vote", getVote())
            .append("readStage", getReadStage())
            .append("visitTime", getVisitTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
