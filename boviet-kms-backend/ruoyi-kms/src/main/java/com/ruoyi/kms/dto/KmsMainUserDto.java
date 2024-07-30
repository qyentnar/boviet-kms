package com.ruoyi.kms.dto;

import lombok.Data;

import java.util.List;


/**
 * 可阅读者
 *
 * @author lingyun
 * @date 2022-10-07
 */
@Data
public class KmsMainUserDto {
    /**
     * 用户id
     */
    private List<KmsMainUserListDto> userList;




}
