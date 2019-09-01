package com.kangning.demo.designpatterns.prototype;

/**
 * @author 加康宁 Date: 2019-08-29 Time: 00:35
 * @version $
 */
public class ProtoTypeShape {

    public static void main(String[] args) {
        ProtoTypeManager pm = new ProtoTypeManager();
        Shape obj1 = pm.getShape("Circle");
        obj1.countArea();
        Shape obj2 = pm.getShape("Square");
        obj2.countArea();
    }
}
