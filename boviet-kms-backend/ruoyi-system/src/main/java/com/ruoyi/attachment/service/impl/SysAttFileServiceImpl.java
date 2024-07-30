package com.ruoyi.attachment.service.impl;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.crypto.SecureUtil;
import com.ruoyi.attachment.domain.SysAttFile;
import com.ruoyi.attachment.domain.SysAttMain;
import com.ruoyi.attachment.enums.AttKeyEnums;
import com.ruoyi.attachment.enums.SuffixEnums;
import com.ruoyi.attachment.mapper.SysAttFileMapper;
import com.ruoyi.attachment.mapper.SysAttMainMapper;
import com.ruoyi.attachment.observer.ConversionEvent;
import com.ruoyi.attachment.observer.EventDefinition;
import com.ruoyi.attachment.service.ISysAttFileService;
import com.ruoyi.common.exception.file.FileNameLengthLimitExceededException;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author chenzh
 * @Date 1/5/22 11:02 AM
 */

@Log4j2
@Service
public class SysAttFileServiceImpl implements ISysAttFileService {

    @Autowired
    private SysAttFileMapper sysAttFileMapper;

    @Autowired
    private SysAttMainMapper sysAttMainMapper;

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file    上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public Map<String, String> upload(String baseDir, MultipartFile file, String directory) throws IOException {
        try {
            return upload(baseDir, file, directory, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        } catch (Exception e) {
            log.error("上传文件失败{}", e);
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir          相对应用的基目录
     * @param file             上传的文件
     * @param allowedExtension 上传文件类型
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     * @throws InvalidExtensionException            文件校验异常
     */
    public Map<String, String> upload(String baseDir, MultipartFile file, String directory, String[] allowedExtension)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException,
            InvalidExtensionException {

        return saveAttFile(baseDir, file, directory, allowedExtension);
    }

    private Map<String, String> saveAttFile(String baseDir, MultipartFile multipart, String directory, String[] allowedExtension)
            throws IOException, InvalidExtensionException {

        Map<String, String> map = new HashMap<String, String>();
        // 文件MD5
        String md5 = SecureUtil.md5(multipart.getInputStream());

        // 相同文件直接返回文件信息
        SysAttFile sysAttFile = sysAttFileMapper.selectAttachmentsByMd5(md5);
        if (null != sysAttFile) {
            Long fileId = sysAttFile.getId();
            SysAttMain sysAttMain = sysAttMainMapper.getAttMainById(fileId);
            if (sysAttMain != null) {
                throw new FileAlreadyExistsException("文件已存在");
            }
            Long[] ids = new Long[] { sysAttFile.getId() };
            sysAttFileMapper.deleteByIds(ids);
            // map.put("fileName",sysAttFile.getFilePath());
            // SysAttMain mainEntity = new SysAttMain();
            // mainEntity.setFileId(sysAttFile.getId());
            // mainEntity.setAttKey(AttKeyEnums.KMS.getCode());
            // //保存main表
            // sysAttMainMapper.insertAttMain(mainEntity);
            // map.put("attMainId",mainEntity.getId().toString());
            // return map;
        }

        // 文件大小
        long size = multipart.getSize();

        // 文件名称
        String prefix = FileNameUtil.getPrefix(multipart.getOriginalFilename());

        // 文件后缀
        String suffix = FileNameUtil.getSuffix(multipart.getOriginalFilename());

        // 文件Content-Type
        String contentType = multipart.getContentType();

        // 上传文件
        int fileNamelength = Objects.requireNonNull(multipart.getOriginalFilename()).length();
        if (fileNamelength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }
        FileUploadUtils.assertAllowed(multipart, allowedExtension);
        String fileName = FileUploadUtils.extractFilename(multipart, directory);
        String absPath = FileUploadUtils.getAbsoluteFile(baseDir, fileName).getAbsolutePath();
        multipart.transferTo(Paths.get(absPath));
        String filePath = FileUploadUtils.getPathFileName(baseDir, fileName);

        // 保存数据到文件表
        SysAttFile fileEntity = new SysAttFile();
        fileEntity.setFilePath(filePath);
        fileEntity.setFileName(prefix);
        fileEntity.setMd5(md5);
        fileEntity.setContentType(contentType);
        fileEntity.setFileType(suffix);
        fileEntity.setFileSize(size);
        // fileEntity.setCreateBy(userId);
        // 设置文件状态
        if (SuffixEnums.findAny(suffix, SuffixEnums.PDF.name())) {
            // 在常量池中匹配成功则说明需要转换,将状态置为转换中
            fileEntity.setState(SysAttFile.FileStateEnum.converting.getState());
        } else if (suffix.equalsIgnoreCase(SuffixEnums.PDF.name())) {
            // PDF格式文件无需转换,直接将状态置为成功
            fileEntity.setState(SysAttFile.FileStateEnum.conversion_succeeded.getState());
            fileEntity.setConverted(filePath);
        } else if (suffix.equalsIgnoreCase(SuffixEnums.TXT.name())) {
            // TXT格式文件无需转换,直接将状态置为成功
            fileEntity.setState(SysAttFile.FileStateEnum.conversion_succeeded.getState());
            fileEntity.setConverted(filePath);
        } else {
            // 未在常量池中定义的文件类型,则全部默认为无需转换的文件
            fileEntity.setState(SysAttFile.FileStateEnum.unnecessary.getState());
        }
        // 保存file表
        sysAttFileMapper.insertAttFile(fileEntity);

        SysAttMain mainEntity = new SysAttMain();
        mainEntity.setFileId(fileEntity.getId());
        mainEntity.setAttKey(AttKeyEnums.KMS.getCode());
        // 保存main表
        sysAttMainMapper.insertAttMain(mainEntity);

        // 异步发起文件转换pdf
        EventDefinition event = new EventDefinition();
        event.setAttFileId(fileEntity.getId());
        event.setMultipartFile(multipart);
        event.setDirectory(directory);
        applicationContext.publishEvent(new ConversionEvent(event));

        map.put("fileName", filePath);
        map.put("attMainId", mainEntity.getId().toString());
        return map;
    }

    @Override
    public void updateAttFile(SysAttFile sysAttFile) throws IOException {
        sysAttFileMapper.updateAttFile(sysAttFile);
        // TODO回写到知识主表 这种设计肯定不合理，框架导致，除非主表和附件重新做大改造，知识主表不存附件相关信息，而是在附件表存储知识主表信息。
        // 进度原因，先这样处理，怎么拿到知识id呢？
    }
}
