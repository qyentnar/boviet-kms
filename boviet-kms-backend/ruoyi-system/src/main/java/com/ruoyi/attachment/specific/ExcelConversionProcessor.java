package com.ruoyi.attachment.specific;

import cn.hutool.core.io.FileUtil;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.ruoyi.attachment.domain.SysAttFile;
import com.ruoyi.attachment.enums.SuffixEnums;
import com.ruoyi.attachment.observer.EventDefinition;
import com.ruoyi.attachment.strategy.AbstractConversion;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Locale;


/**
 * Excel转换处理器
 *
 * @author chenzh
 * @date 1/14/22 4:06 PM
 */

@Log4j2
@Service
public class ExcelConversionProcessor extends AbstractConversion {

    @Override
    protected void conversion(EventDefinition eventDefinition, BufferedInputStream sourceBytes, File temp) {
        this.license("cells");
        // 上传文件路径
        String baseDir = RuoYiConfig.getUploadPath();
        String fileName = FileUploadUtils.extractFilename(eventDefinition.getMultipartFile(), eventDefinition.getDirectory());
        try {
            String pdfPath = FileUploadUtils.getAbsoluteFile(baseDir, fileName).getAbsolutePath();
            pdfPath = pdfPath.concat("." + SuffixEnums.PDF.name().toLowerCase(Locale.ROOT));
            Workbook wb = new Workbook(sourceBytes);// 原始excel路径
            //FileOutputStream fileOS = new FileOutputStream(pdfPath);
            File file = new File(pdfPath);
            FileOutputStream fileOS = new FileOutputStream(file);
            PdfSaveOptions pdfSaveOptions = new PdfSaveOptions();
            pdfSaveOptions.setOnePagePerSheet(true);
            wb.save(fileOS, pdfSaveOptions);
            fileOS.flush();
            fileOS.close();
            System.out.println("转换完毕");

            // 保存转换后的文件地址到知识主表上
            String filePath = FileUploadUtils.getPathFileName(baseDir, fileName);
            filePath = filePath.concat("." + SuffixEnums.PDF.name().toLowerCase(Locale.ROOT));
            saveTheConvertedFile(eventDefinition, filePath);
            // 转换成功-维护状态
            stateMaintenance(eventDefinition, SysAttFile.FileStateEnum.conversion_succeeded);
        } catch (Exception e) {
            //e.printStackTrace();
            try {
                stateMaintenance(eventDefinition, SysAttFile.FileStateEnum.conversion_failed);
            } catch (IOException ex) {
                //ex.printStackTrace();
                throw new RuntimeException(String.format("设置附件转换状态失败:[{}]", e));
            }
            throw new RuntimeException(String.format("Excel-TO-PDF失败:[{}]", e));
        }

    }
}

