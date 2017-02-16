package com.yqq.dictwoapp.bean;

/**
 * 商品
 *
 * Created by yq on 2017/2/16.
 */

public class Good {
    private int i;
    private String shop_price;
    private String goods_name;

    public void setI(int i) {
        this.i = i;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getI() {
        return i;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public String getShop_price() {
        return shop_price;
    }

    @Override
    public String toString() {
        return i+shop_price+goods_name;
    }
}
