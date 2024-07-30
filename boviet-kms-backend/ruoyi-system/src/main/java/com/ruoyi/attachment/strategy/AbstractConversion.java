package com.ruoyi.attachment.strategy;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.ruoyi.attachment.domain.SysAttFile;
import com.ruoyi.attachment.observer.EventDefinition;
import com.ruoyi.attachment.service.ISysAttFileService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author chenzh
 * @date 1/10/22 11:26 AM
 */
@Log4j2
public abstract class AbstractConversion implements ConversionStrategy {

    public ISysAttFileService attFileService;

    @Resource
    public void setAttFileService(ISysAttFileService attFileService) {
        this.attFileService = attFileService;
    }

    protected abstract void conversion(EventDefinition eventDefinition, BufferedInputStream sourceBytes, File temp);

    /**
     * @see ConversionStrategy#pretreatment(EventDefinition)
     */
    @Override
    public void pretreatment(EventDefinition eventDefinition) throws IOException {
        MultipartFile multipartFile = eventDefinition.getMultipartFile();
        if (multipartFile.isEmpty()) throw new RuntimeException("上传文件不能为空.");
        // 源文件名
        String originalFilename = multipartFile.getOriginalFilename();
        // 文件名称(不带后缀)
        String prefix = FileNameUtil.getPrefix(originalFilename);
        // 文件后缀(不带.)
        String suffix = FileNameUtil.getSuffix(originalFilename);
        // 系统临时文件夹路径
        File tmpDir = FileUtil.getTmpDir();
        // 创建临时文件对象
        String path = tmpDir.getAbsolutePath().concat(FileUtil.FILE_SEPARATOR).concat(prefix).concat(".").concat(suffix);
        File tempFile = FileUtil.newFile(path);
        log.info("系统临时文件:[{}]", () -> path);
        // 获取输入流
        InputStream inputStream = multipartFile.getInputStream();
        BufferedInputStream buffered = IoUtil.toBuffered(inputStream);
        // 执行转换
        long begin = System.currentTimeMillis();
        log.info("aspose starts execution!");
        conversion(eventDefinition, buffered, tempFile);
        log.info("aspose is executed ends! time:[{}]", () -> System.currentTimeMillis() - begin);
    }

    /**
     * @see ConversionStrategy#stateMaintenance(EventDefinition, SysAttFile.FileStateEnum)
     */
    @Override
    public boolean stateMaintenance(EventDefinition eventDefinition, SysAttFile.FileStateEnum stateEnum) throws IOException {
        SysAttFile sysAttFile = new SysAttFile();
        sysAttFile.setId(eventDefinition.getAttFileId());
        sysAttFile.setState(stateEnum.getState());
        attFileService.updateAttFile(sysAttFile);
        return true;
    }


    /**
     * @see ConversionStrategy#saveTheConvertedFile(EventDefinition,String)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveTheConvertedFile(EventDefinition eventDefinition, String filePath) throws IOException {
        // 上传转换成功后的文件
        /*Try<MockMultipartFile> multipartFileTry = Try.of(() -> new MockMultipartFile(file.getName(), file.getName(), ContentType.OCTET_STREAM.getValue(), FileUtil.getInputStream(file)));
        MockMultipartFile multipartFile = multipartFileTry.getOrElseThrow(throwable -> new BizException(String.format("构建Multipart失败:[{}]", throwable)));
        UploadResult uploadResult = attFileService.upload(multipartFile);
        String fullPath = uploadResult.getFullPath();
        boolean del = FileUtil.del(file);*/
        // 保存转换后的地址
        SysAttFile sysAttFile = new SysAttFile();
        sysAttFile.setId(eventDefinition.getAttFileId());
        sysAttFile.setConverted(filePath);
        attFileService.updateAttFile(sysAttFile);
        return true;
    }

    /**
     * @see ConversionStrategy#documentSplitting(java.io.File)
     */
    @SneakyThrows
    @Override
    public void documentSplitting(File file) {

    }

    @Override
    public boolean license(String var) {
        return getLicense(var);
    }

    /**
     * 验证License 若不验证则转化出的pdf文档会有水印产生
     * @return
     */
    private boolean getLicense(String var){
        boolean result = false;
        try {

            InputStream is =this.getClass().getClassLoader().getResourceAsStream("license.xml");
            switch (var) {

                case "words":
                    //注意此处为对应aspose-cells的jar包
                    com.aspose.words.License wordAposeLic = new com.aspose.words.License();
                    wordAposeLic.setLicense(is);
                    result = true;
                    break; //可选

                case "cells":
                    //注意此处为对应aspose-cells的jar包
                    com.aspose.cells.License excelAposeLic = new com.aspose.cells.License();
                    excelAposeLic.setLicense(is);
                    result = true;
                    break; //可选

                case "slides":
                    //注意此处为对应aspose-slides的jar包
                    com.aspose.slides.License pptAposeLic = new com.aspose.slides.License();
                    pptAposeLic.setLicense(is);
                    result = true;
                    break; //可选
                default :
                    result = false;
                    new RuntimeException("Aspose License 授权失败.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
