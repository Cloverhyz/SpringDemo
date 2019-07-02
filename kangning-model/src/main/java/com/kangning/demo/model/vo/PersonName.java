package com.kangning.demo.model.vo;

import java.io.Serializable;

/**
 * @author 加康宁 Date: 2019-07-01 Time: 18:55
 * @version $Id$
 */
public class PersonName implements Serializable {

    private static final long serialVersionUID = -1545546666599857552L;

    private String hoseName;

    private String name;

    public String getHoseName() {
        return hoseName;
    }

    public void setHoseName(String hoseName) {
        this.hoseName = hoseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonName{" +
            "hoseName='" + hoseName + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
