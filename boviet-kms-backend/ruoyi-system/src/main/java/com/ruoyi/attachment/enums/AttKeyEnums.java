package com.ruoyi.attachment.enums;

/**
 * 附件KEY
 *
 * @author ruoyi
 */
public enum AttKeyEnums {

    KMS("KMS_ATT", "KMS知识附件KEY","com.ruoyi.kms.domain.KmsMain");

    private final String code;
    private final String info;
    private final String model;

    AttKeyEnums(String code, String info, String model)
    {
        this.code = code;
        this.info = info;
        this.model = model;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }

    public String getModel()
    {
        return model;
    }

}
