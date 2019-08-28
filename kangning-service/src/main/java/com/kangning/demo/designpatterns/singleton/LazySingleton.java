package com.kangning.demo.designpatterns.singleton;

/**
 * @author 加康宁 Date: 2019-08-28 Time: 00:28
 * @version $
 */
public class LazySingleton {

    private int id;

    private volatile static LazySingleton lazySingleton;

    public static LazySingleton getInstance(){
        if (lazySingleton == null){
            synchronized (LazySingleton.class){
                if (lazySingleton == null){
                    lazySingleton = new LazySingleton();
                }
            }
        }
        return lazySingleton;
    }

    private LazySingleton() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
