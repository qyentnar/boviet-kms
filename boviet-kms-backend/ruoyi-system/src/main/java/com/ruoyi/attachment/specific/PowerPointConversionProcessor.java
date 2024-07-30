package com.ruoyi.attachment.specific;

import cn.hutool.core.io.FileUtil;
import com.aspose.slides.*;
import com.ruoyi.attachment.domain.SysAttFile;
import com.ruoyi.attachment.enums.SuffixEnums;
import com.ruoyi.attachment.observer.EventDefinition;
import com.ruoyi.attachment.strategy.AbstractConversion;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

/*
 * ppt 转换处理器
 *
 * @author chenzh
 * @Date 2022/1/15 10:05 PM
 */



@Log4j2
@Service
public class PowerPointConversionProcessor extends AbstractConversion {

    @Override
    protected void conversion(EventDefinition eventDefinition, BufferedInputStream sourceBytes, File temp) {
        this.license("slides");

        // 上传文件路径
        String baseDir = RuoYiConfig.getUploadPath();
        String fileName = FileUploadUtils.extractFilename(eventDefinition.getMultipartFile(), eventDefinition.getDirectory());
        try {
            String pdfPath = FileUploadUtils.getAbsoluteFile(baseDir, fileName).getAbsolutePath();
            pdfPath = pdfPath.concat("." + SuffixEnums.PDF.name().toLowerCase(Locale.ROOT));
            //新建一个pdf文档
            File file = new File(pdfPath);
            FileOutputStream os = new FileOutputStream(file);
            Presentation ppt = new Presentation(sourceBytes);
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            ppt.save(os, com.aspose.slides.SaveFormat.Pdf);
            os.close();
            System.out.println("转换完毕");

            // 保存转换后的文件地址到知识主表上
            String filePath = FileUploadUtils.getPathFileName(baseDir, fileName);
            filePath = filePath.concat("." + SuffixEnums.PDF.name().toLowerCase(Locale.ROOT));
            saveTheConvertedFile(eventDefinition, filePath);
            // 转换成功-维护状态
            stateMaintenance(eventDefinition, SysAttFile.FileStateEnum.conversion_succeeded);
        } catch (Exception e) {
            //e.printStackTrace();
            // 转换失败-维护状态
            try {
                stateMaintenance(eventDefinition, SysAttFile.FileStateEnum.conversion_failed);
            } catch (IOException ex) {
                //ex.printStackTrace();
                throw new RuntimeException(String.format("设置附件转换状态.失败:[{}]", e));
            }
            throw new RuntimeException(String.format("PPT-TO-PDF.失败:[{}]", e));
        }
    }

}
