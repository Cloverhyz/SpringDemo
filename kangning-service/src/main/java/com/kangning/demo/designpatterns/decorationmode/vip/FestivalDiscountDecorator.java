package com.kangning.demo.designpatterns.decorationmode.vip;

/**
 * @author kangningj Date: 2020-05-07 Time: 12:30 下午
 * @version $
 */
public class FestivalDiscountDecorator extends DiscountDecorator{

    public FestivalDiscountDecorator(BaseDiscount component) {
        super(component);
    }

    @Override
    public void discount() {
        component.discount();
    }

    @Override
    public void discountForVip() {
        //do noting
    }

    @Override
    public void discountForFestival() {
        discount();
        //do something
        System.out.println("扩展节假日折扣业务功能");
    }
}
