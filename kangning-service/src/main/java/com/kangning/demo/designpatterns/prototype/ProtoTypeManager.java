package com.kangning.demo.designpatterns.prototype;

import java.util.HashMap;

/**
 * @author 加康宁 Date: 2019-08-29 Time: 00:36
 * @version $
 */
public class ProtoTypeManager {

    private HashMap<String, Shape> ht = new HashMap<String, Shape>();

    public ProtoTypeManager() {
        ht.put("Circle", new Circle());
        ht.put("Square", new Square());
    }

    public Shape getShape(String key) {
        Shape temp = ht.get(key);
        return (Shape) temp.clone();
    }
}
