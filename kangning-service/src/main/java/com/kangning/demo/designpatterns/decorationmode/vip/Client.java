package com.kangning.demo.designpatterns.decorationmode.vip;

/**
 * @author kangningj Date: 2020-05-07 Time: 12:29 下午
 * @version $
 */
public class Client {
    public static void main(String[] args) {
        //客户端使用
        BaseDiscount vipDiscountBusiness, festivalDiscountBusiness;

        vipDiscountBusiness = new VIPDiscountBusiness();
        festivalDiscountBusiness = new FestivalDiscountBusiness();

        DiscountDecorator vipDiscountDecorator, festivalDiscountDecorator;

        vipDiscountDecorator = new VIPDiscountDecorator(vipDiscountBusiness);
        festivalDiscountDecorator = new FestivalDiscountDecorator(festivalDiscountBusiness);

        vipDiscountDecorator.discountForVip();
        festivalDiscountDecorator.discountForFestival();

    }
}
