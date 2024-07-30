package com.ruoyi.kms.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 知识检索平台 入参KmsSearchDto
 *
 * @author ruoyi
 * @date 2022-05-25
 */
@Data
public class KmsSearchDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 查询key
     */
    @ApiModelProperty("查询key")
    private String searchKey;

    /**
     * 查询值
     */
    @ApiModelProperty("查询值")
    private String searchValue;

    /**
     * 查询每页显示
     */
    @ApiModelProperty("查询每页显示")
    private Integer pageSize;

    /**
     * 排序选项
     */
    @ApiModelProperty("排序选项")
    private String sortBy;

    /**
     * 排序方式
     */
    @ApiModelProperty("排序方式")
    private String sortType;

    /**
     * 查询方式
     */
    @ApiModelProperty("查询方式")
    private String searchBy;


}
