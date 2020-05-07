package com.kangning.demo.designpatterns.decorationmode.vip;

/**
 * @author kangningj Date: 2020-05-07 Time: 12:29 下午
 * @version $
 */
public abstract class DiscountDecorator extends BaseDiscount{

    public BaseDiscount component;

    public DiscountDecorator(BaseDiscount component) {
        this.component = component;
    }
    public abstract void discountForVip();

    public abstract void discountForFestival();
}
