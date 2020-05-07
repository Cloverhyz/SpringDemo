package com.kangning.demo.designpatterns.decorationmode.vip;

/**
 * @author kangningj Date: 2020-05-07 Time: 12:30 下午
 * @version $
 */
public class FestivalDiscountBusiness extends BaseDiscount {
    @Override
    public void discount() {
        System.out.println("处理节假日折扣业务");
    }
}
