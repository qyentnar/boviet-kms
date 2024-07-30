package com.ruoyi.attachment.strategy;

import com.ruoyi.attachment.domain.SysAttFile;
import com.ruoyi.attachment.observer.EventDefinition;

import java.io.File;
import java.io.IOException;

/**
 * @author chenzh
 * @date 1/10/22 11:26 AM
 */
public interface ConversionStrategy {

    /**
     * 转换文件预处理
     *
     * @param eventDefinition
     */
    void pretreatment(EventDefinition eventDefinition) throws Exception;

    /**
     * 文件状态维护
     *
     * @param eventDefinition
     */
    boolean stateMaintenance(EventDefinition eventDefinition, SysAttFile.FileStateEnum stateEnum) throws IOException;

    /**
     * 保存转换后的文件
     */
    boolean saveTheConvertedFile(EventDefinition eventDefinition,String filePath) throws IOException;

    /**
     * 分割文件到每一页
     *
     * @param file
     */
    @Deprecated
    void documentSplitting(File file);

    /**
     * Aspose 授权认证
     *
     * @param var
     */
    boolean license(String var);

}
