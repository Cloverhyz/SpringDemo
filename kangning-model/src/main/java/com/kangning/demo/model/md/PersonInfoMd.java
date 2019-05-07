package com.kangning.demo.model.md;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 加康宁 Date: 2019-04-23 Time: 11:49
 * @version $Id$
 */
public class PersonInfoMd implements Serializable {

    private static final long serialVersionUID = 7802382357016929421L;

    private Long personId;

    private String personName;

    private Integer personAge;

    private Date updateTime;

    private Date createTime;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Integer getPersonAge() {
        return personAge;
    }

    public void setPersonAge(Integer personAge) {
        this.personAge = personAge;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PersonInfoMd{" +
            "personId=" + personId +
            ", personName='" + personName + '\'' +
            ", personAge=" + personAge +
            ", updateTime=" + updateTime +
            ", createTime=" + createTime +
            '}';
    }
}
