package com.kangning.demo.designpatterns.decorationmode.vip;

/**
 * @author kangningj Date: 2020-05-07 Time: 12:28 下午
 * @version $
 */

public class VIPDiscountBusiness extends BaseDiscount {
    @Override
    public void discount() {
        System.out.println("处理vip用户的折扣业务");
    }
}
