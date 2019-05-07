package com.kangning.demo.model.vo;

/**
 * @author 加康宁 Date: 2018-08-27 Time: 21:28
 * @version $Id$
 */

public class PCData {
    private long value;

    public void set(long value) {
        this.value = value;

    }

    public long get() {
        return value;
    }

    @Override
    public String toString() {
        return "PCData{" +
                "value=" + value +
                '}';
    }
}