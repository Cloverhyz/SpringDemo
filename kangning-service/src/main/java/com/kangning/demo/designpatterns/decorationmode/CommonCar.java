package com.kangning.demo.designpatterns.decorationmode;

/**
 * 定义一个被装饰的类 NormalCar
 * @author kangningj Date: 2020-05-07 Time: 12:03 下午
 * @version $
 */
public class CommonCar implements Car {

    @Override
    public void gearchange() {
        System.out.println("汽车进行换档操作");
    }
}
