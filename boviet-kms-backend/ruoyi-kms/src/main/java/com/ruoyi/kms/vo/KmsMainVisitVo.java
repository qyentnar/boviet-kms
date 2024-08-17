package com.ruoyi.kms.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 访问统计对象 kms_main_visit
 * 
 * @author ruoyi
 * @date 2024-08-09
 */
public class KmsMainVisitVo extends BaseEntity
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

    private String nickName;

    private String deptName;
    

    /** 访问次数 */
    @Excel(name = "访问次数")
    private Long visitCount;

    /** 投票 */
    @Excel(name = "投票")
    private Long vote;

    private String readState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitTime;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getReadState() {
        return readState;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("kmsId", getKmsId())
            .append("userVisited", getUserVisited())
            .append("nickName", getNickName())
            .append("deptName", getDeptName())
            .append("visitCount", getVisitCount())
            .append("vote", getVote())
            .append("readState", getReadState())
            .append("visitTime", getVisitTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
