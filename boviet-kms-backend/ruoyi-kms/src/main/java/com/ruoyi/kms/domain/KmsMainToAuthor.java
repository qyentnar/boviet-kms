package com.ruoyi.kms.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 kms_main_to_author
 *
 * @author ruoyi
 * @date 2022-05-25
 */
public class KmsMainToAuthor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Long id;

    /** 知识id */
    private Long kId;

    /** 作者id */
    private Long authorId;

    /**
     * 作者姓名
     */
    private String authorName;

    public void setkId(Long kId)
    {
        this.kId = kId;
    }

    public Long getkId()
    {
        return kId;
    }
    public void setAuthorId(Long authorId)
    {
        this.authorId = authorId;
    }

    public Long getAuthorId()
    {
        return authorId;
    }

    public void setAuthorName(String authorName){
        this.authorName = authorName;
    }
    public String getAuthorName(){return authorName;}

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("kId", getkId())
            .append("authorId", getAuthorId())
            .append("authorName", getAuthorName())
            .toString();
    }
}
