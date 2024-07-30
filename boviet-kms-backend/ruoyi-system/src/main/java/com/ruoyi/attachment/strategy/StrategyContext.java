package com.ruoyi.attachment.strategy;

import cn.hutool.core.text.CharSequenceUtil;
import com.ruoyi.attachment.enums.SuffixEnums;
import com.ruoyi.attachment.observer.EventDefinition;
import com.ruoyi.attachment.specific.ExcelConversionProcessor;
//import com.ruoyi.attachment.specific.PowerPointConversionProcessor;
//import com.ruoyi.attachment.specific.TxtConversionProcessor;
import com.ruoyi.attachment.specific.PowerPointConversionProcessor;
import com.ruoyi.attachment.specific.WordConversionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author chenzh
 * @date 1/10/22 5:33 PM
 */

@Component
public class StrategyContext {

    private final Map<String, ConversionStrategy> strategyMap = new ConcurrentHashMap<>();

    private static String BEAN_NAME;

    @Autowired
    public StrategyContext(Map<String, ConversionStrategy> strategyMap) {
        this.strategyMap.clear();
        BEAN_NAME = CharSequenceUtil.lowerFirst(WordConversionProcessor.class.getSimpleName());
        // word
        this.strategyMap.put(SuffixEnums.DOC.name().toLowerCase(), strategyMap.get(BEAN_NAME));
        this.strategyMap.put(SuffixEnums.DOCX.name().toLowerCase(), strategyMap.get(BEAN_NAME));
        // excel
        BEAN_NAME = CharSequenceUtil.lowerFirst(ExcelConversionProcessor.class.getSimpleName());
        this.strategyMap.put(SuffixEnums.XLS.name().toLowerCase(), strategyMap.get(BEAN_NAME));
        this.strategyMap.put(SuffixEnums.XLSX.name().toLowerCase(), strategyMap.get(BEAN_NAME));
        // txt
        /*BEAN_NAME = CharSequenceUtil.lowerFirst(TxtConversionProcessor.class.getSimpleName());
        this.strategyMap.put(SuffixEnums.TXT.name().toLowerCase(), strategyMap.get(BEAN_NAME));
        this.strategyMap.put(SuffixEnums.TXT.name().toLowerCase(), strategyMap.get(BEAN_NAME));*/
        // ppt
        BEAN_NAME = CharSequenceUtil.lowerFirst(PowerPointConversionProcessor.class.getSimpleName());
        this.strategyMap.put(SuffixEnums.PPT.name().toLowerCase(), strategyMap.get(BEAN_NAME));
        this.strategyMap.put(SuffixEnums.PPTX.name().toLowerCase(), strategyMap.get(BEAN_NAME));
    }

    public void getConversionProcessor(String status, EventDefinition eventDefinition) throws Exception {
        ConversionStrategy consumerStrategy = strategyMap.get(status);
        consumerStrategy.pretreatment(eventDefinition);
    }

    public void getConversionProcessor(SuffixEnums suffixEnums, EventDefinition eventDefinition) throws Exception {
        ConversionStrategy consumerStrategy = strategyMap.get(suffixEnums.name());
        consumerStrategy.pretreatment(eventDefinition);
    }

}

