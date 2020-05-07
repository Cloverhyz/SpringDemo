package com.kangning.demo.designpatterns.decorationmode;

/**
 * 定义一个装饰的抽象类 AbstractCar
 * @author kangningj Date: 2020-05-07 Time: 12:07 下午
 * @version $
 */
public abstract class AbstractCar implements Car {

    /**
     * 持有被装饰类的引用
     */
    private Car car;

    public AbstractCar(Car car) {
        this.car = car;
    }

    @Override
    public void gearchange() {
        car.gearchange();
    }
}
