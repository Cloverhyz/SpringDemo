package com.kangning.demo.designpatterns.decorationmode;

/**
 * 定义一个装饰的实现类 AutoCar
 * @author kangningj Date: 2020-05-07 Time: 12:09 下午
 * @version $
 */
public class AutoCar extends AbstractCar  {

    public AutoCar(Car car) {
        super(car);
    }

    @Override
    public void gearchange() {
        autoGearchange();
        super.gearchange();
    }

    public void autoGearchange() {
        System.out.println("汽车会自动换挡");
    }
}
