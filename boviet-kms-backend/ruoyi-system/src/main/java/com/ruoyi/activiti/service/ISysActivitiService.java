package com.ruoyi.activiti.service;

import com.ruoyi.activiti.dto.ActivitiDto;

public interface ISysActivitiService {
    void deployment(ActivitiDto activitiDto);
    void startProcess();

    void testDeployment();
    void testStartProcess();
}
