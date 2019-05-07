package com.kangning.demo.model.enums;

import com.kangning.demo.framework.util.RbaParentEnum;

/**
 * @author 加康宁
 * @version ${Id}
 * @date 2018-07-27 Time: 12:21
 * 城市区域级别
 */
public enum EnumRegionLevel  implements RbaParentEnum {

    Country(2, "国家"),
    Province(3, "省份"),
    City(4, "城市"),
    Area(5, "区域");

    /**
     * 区域级别code
     */
    private Integer code;
    /**
     * 区域级别名称
     */
    private String name;

    EnumRegionLevel(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
