package com.ruoyi.kms.constant;

import lombok.Getter;

public class KmsConstant {

    /**
     * StateEnum枚举定义
     *状态：10-草稿，11-驳回, 20-待审，30-发布，40-过期，50-删除
     */
    @Getter
    public static enum StateEnum implements CodeEnum {
        STATE_TEMP("草稿","10"),
        STATE_REJECT("驳回","11"),
        STATE_EXAMINE("待审","20"),
        STATE_PUBLISH("发布","30"),
        STATE_EXPIRE("过期","40"),
        STATE_DEL("删除","50");

        private String name;
        private String code;
        StateEnum(String name, String code){
            this.name = name;
            this.code = code;
        }
    }
}
