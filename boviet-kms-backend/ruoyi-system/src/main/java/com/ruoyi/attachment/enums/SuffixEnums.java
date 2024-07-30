package com.ruoyi.attachment.enums;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * @author chenzh
 * @Date 1/13/22 1:52 PM
 */
@Getter
public enum SuffixEnums {


    DOC, DOCX, PPT, PPTX, PDF, XLS, XLSX, TXT;



    public static boolean findAny(String suffix){
        return Stream.of(SuffixEnums.values()).map(SuffixEnums::name).filter(constants -> suffix.equalsIgnoreCase(constants)).findAny().isPresent();
    }

    public static boolean findAny(String suffix,String filter){
        return Stream.of(SuffixEnums.values()).map(SuffixEnums::name).filter(constants -> suffix.equalsIgnoreCase(constants)).filter(constants -> !filter.equalsIgnoreCase(constants)).findAny().isPresent();
    }

}
