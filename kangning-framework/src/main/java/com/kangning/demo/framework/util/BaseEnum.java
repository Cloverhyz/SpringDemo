package com.kangning.demo.framework.util;

/**
 * @author 加康宁
 * @version ${Id}
 * @date 2018-07-27 Time: 18:48
 *
 * 基础枚举类接口，实现该枚举类接口，可以使用EnumUtil{@link EnumUtil}类通
 * 过枚举的code和name属性反向获取到枚举类型
 */
public interface BaseEnum {

    Integer getCode();

    String getName();

}
