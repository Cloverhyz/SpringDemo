package com.kangning.demo.designpatterns.singleton;

/**
 * @author 加康宁 Date: 2019-08-28 Time: 00:36
 * @version $
 */
public class HungrySingleton {

    private int id;

    private static final HungrySingleton hungrySingleton = new HungrySingleton();

    public static HungrySingleton getInstance(){
        return hungrySingleton;
    }

    private HungrySingleton() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
