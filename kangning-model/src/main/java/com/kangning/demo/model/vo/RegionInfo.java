package com.kangning.demo.model.vo;

import java.io.Serializable;

/**
 * @author 加康宁
 * @version ${Id}
 * @date 2018-07-27 Time: 11:47
 */
public class RegionInfo implements Serializable {

    private static final long serialVersionUID = 6210657930695464435L;

    /**
     * 区域id，数据库主键id
     */
    private Long regionId;

    /**
     * 父级区域id
     */
    private Long parentRegionId;

    /**
     * 区域级别,{@link com.kangning.demo.model.enums.EnumRegionLevel}
     */
    private Integer regionLevel;

    /**
     * 区域中文名称
     */
    private String regionCN;

    /**
     * 区域英文名称
     */
    private String regionEN;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getParentRegionId() {
        return parentRegionId;
    }

    public void setParentRegionId(Long parentRegionId) {
        this.parentRegionId = parentRegionId;
    }

    public Integer getRegionLevel() {
        return regionLevel;
    }

    public void setRegionLevel(Integer regionLevel) {
        this.regionLevel = regionLevel;
    }

    public String getRegionCN() {
        return regionCN;
    }

    public void setRegionCN(String regionCN) {
        this.regionCN = regionCN;
    }

    public String getRegionEN() {
        return regionEN;
    }

    public void setRegionEN(String regionEN) {
        this.regionEN = regionEN;
    }

    @Override
    public String toString() {
        return "RegionInfo{" +
            "regionId=" + regionId +
            ", parentRegionId=" + parentRegionId +
            ", regionLevel=" + regionLevel +
            ", regionCN='" + regionCN + '\'' +
            ", regionEN='" + regionEN + '\'' +
            '}';
    }
}
