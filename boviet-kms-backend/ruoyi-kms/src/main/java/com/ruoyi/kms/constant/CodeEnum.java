package com.ruoyi.kms.constant;

/**
 * @Description 定义的枚举公共接口，
 * 实现此接口后可以调用工具类根据传入的code值获取name值
 * 注意：调用接口的枚举类属性名必须是code和name
 * @Author jin_z
 * @Date 2020/11/28 21:55
 * @since:
 * @copyright:
 */
public interface CodeEnum {

    String getCode();

    String getName();

}
