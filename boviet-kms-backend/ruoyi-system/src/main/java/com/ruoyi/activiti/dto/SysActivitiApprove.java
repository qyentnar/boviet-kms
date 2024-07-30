package com.ruoyi.activiti.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SysActivitiApprove {

    private String processInstanceId;
    private String assignee;
    private String comment;
}
