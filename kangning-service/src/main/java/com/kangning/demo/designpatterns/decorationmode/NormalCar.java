package com.kangning.demo.designpatterns.decorationmode;

/**
 * @author kangningj Date: 2020-05-07 Time: 12:24 下午
 * @version $
 */
public class NormalCar extends AbstractCar {

    public NormalCar(Car car) {
        super(car);
    }

    @Override
    public void gearchange() {
        super.gearchange();
    }

    public void handleGearchange() {
        System.out.println("汽车需要手动换挡");
    }

}
