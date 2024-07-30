package com.ruoyi.kms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 可阅读者
 *
 * @author lingyun
 * @date 2022-10-07
 */

@Data
@AllArgsConstructor
public class KmsMainUserListDto {

    /**
     * 主文档id
     */
    private Long knowId;

    /**
     * 用户id
     */
    private Long userId;

}
