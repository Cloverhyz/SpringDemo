package com.kangning.demo.designpatterns.prototype;

/**
 * @author 加康宁 Date: 2019-08-29 Time: 00:24
 * @version $
 */
public interface Shape extends Cloneable {

    public Object clone();    //拷贝

    public void countArea();    //计算面积
}

