package com.ruoyi.attachment.observer;

import cn.hutool.core.io.file.FileNameUtil;
import com.ruoyi.attachment.enums.SuffixEnums;
import com.ruoyi.attachment.strategy.StrategyContext;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenzh
 * @date 1/10/22 1:40 PM
 */

@Component
@Log4j2
public class ConversionListener implements ApplicationListener<ConversionEvent> {

    @Resource
    private StrategyContext strategyContext;

    // 过滤无需转换的文件类型
    final Predicate<String> predicate = type -> !type.equalsIgnoreCase(SuffixEnums.PDF.name()) && !type.equalsIgnoreCase(SuffixEnums.TXT.name());

    private final List<String> supported = Stream.of(SuffixEnums.values()).map(SuffixEnums::name).filter(predicate).map(String::toLowerCase).collect(Collectors.toList());

    @SneakyThrows
    @Override
    public void onApplicationEvent(ConversionEvent conversionEvent) {

        EventDefinition eventDefinition = (EventDefinition) conversionEvent.getSource();
        MultipartFile multipartFile = eventDefinition.getMultipartFile();
        String originalFilename = multipartFile.getOriginalFilename();

        String suffix = FileNameUtil.getSuffix(originalFilename);

        // 只支持转换`supported`中类型的文件.
        Optional<String> any = supported.stream().filter(suffix::equalsIgnoreCase).findAny();
        if (!any.isPresent()) return;
        strategyContext.getConversionProcessor(any.get(), eventDefinition);
    }

}

