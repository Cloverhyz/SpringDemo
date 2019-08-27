package com.kangning.demo.model.vo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @JsonProperty("region_id")
    private Long regionId;

    /**
     * 父级区域id
     */
    @JsonProperty("parent_region_id")
    private Long parentRegionId;

    /**
     * 区域级别,{@link com.kangning.demo.model.enums.EnumRegionLevel}
     */
    @JsonProperty("region_level")
    private Integer regionLevel;

    /**
     * 区域中文名称
     */
//    @JSONField(name = "region_jp")
    @JsonProperty("region_cn")
    private String regionCN;

    /**
     * 区域英文名称
     */
    @JsonProperty("region_en")
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


    public static void main(String[] args) throws JsonProcessingException {
        RegionInfo regionInfo = new RegionInfo();
        regionInfo.setParentRegionId(1L);
        regionInfo.setRegionCN("北京");
        regionInfo.setRegionEN("beijing");
        regionInfo.setRegionId(1L);
        regionInfo.setRegionLevel(1);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = "{\"region_id\":1,\"parent_region_id\":1,\"region_level\":1,\"region_en\":\"beijing\",\"region_cn\":\"北京\"}";
        System.out.println(jsonStr);
        RegionInfo region = JSONObject.parseObject(jsonStr,RegionInfo.class);
        System.out.println(region);
    }
}
