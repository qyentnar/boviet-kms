package com.ruoyi.kms.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.kms.domain.KmsCatalog;

public class KmsTreeSelect implements Serializable{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<KmsTreeSelect> children;

    public KmsTreeSelect()
    {

    }

    public KmsTreeSelect(KmsCatalog catalog)
    {
        this.id = catalog.getId();
        this.label = catalog.getTitle();
        this.children = catalog.getChildren().stream().map(KmsTreeSelect::new).collect(Collectors.toList());
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<KmsTreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<KmsTreeSelect> children)
    {
        this.children = children;
    }
}
