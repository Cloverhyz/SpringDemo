package com.kangning.demo.framework.util;

import java.util.Arrays;

/**
 * @author 加康宁
 * @version ${Id}
 * @date 2018-07-26 Time: 16:25
 */
public class EnumUtil {

    /**
     * 通过枚举name属性获取枚举值
     *
     * @param clazz :枚举的class
     * @param name  :枚举name
     * @param <T>   :枚举类型
     * @return
     */
    public static <T extends RbaParentEnum> T getEnumByName(Class<T> clazz, String name) {
        return Arrays.stream(clazz.getEnumConstants()).filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }

    /**
     * 通过枚举code获取枚举值
     *
     * @param clazz :枚举的class
     * @param code  :枚举code
     * @param <T>   :枚举类型
     * @return
     */
    public static <T extends RbaParentEnum> T getEnumByCode(Class<T> clazz, int code) {
        return Arrays.stream(clazz.getEnumConstants()).filter(e -> e.getCode() == code).findFirst().orElse(null);
    }

}
