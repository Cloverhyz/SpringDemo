package com.kangning.demo.designpatterns.decorationmode;

/**
 * @author kangningj Date: 2020-05-07 Time: 12:42 下午
 * @version $
 */
public class Client {
    public static void main(String[] args) {
        //创建被装饰的类
        Car car = new CommonCar();

        //创建装饰的类，并添加被装饰类的引用
        Car autoCar = new AutoCar(car);

        //执行增强后的run方法
        autoCar.gearchange();
    }
}
