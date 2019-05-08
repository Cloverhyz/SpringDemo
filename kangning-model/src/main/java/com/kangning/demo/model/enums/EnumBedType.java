package com.kangning.demo.model.enums;

import com.kangning.demo.framework.util.BaseEnum;

/**
 * @author 加康宁
 * @version ${Id}
 * @date 2018-07-27 Time: 17:55
 * 床型枚举
 */
public enum EnumBedType implements BaseEnum {
    BigBed(1,"大床"),
    SingleBed(2,"单人床"),
    RoundBed(3,"圆床"),
    Tatami(4,"榻榻米"),
    SingleSofaBed(5,"单人沙发床"),
    DoubleSofaBed(6,"双人沙发床"),
    BunkBed(7,"双层床");

    /**
     * 床型信息code
     */
    private Integer code;

    /**
     * 床型名称
     */
    private String name;

    EnumBedType(Integer code, String name) {
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
