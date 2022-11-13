
package com.flower.mall.common;


public class Constants {
    //public final static String FILE_UPLOAD_DIC = "/Users/chenwenwei/Desktop/myProject/flower-mall/upload/";//上传文件的默认url前缀，根据部署设置自行修改
    //public final static String FILE_UPLOAD_DIC = "D:\\upload\\";//上传文件的默认url前缀，根据部署设置自行修改
    public final static String FILE_UPLOAD_DIC = "/imwenwen/flower/upload/";
    public final static int SHOPPING_CART_ITEM_TOTAL_NUMBER = 13;//购物车中商品的最大数量(可根据自身需求修改)

    public final static int SHOPPING_CART_ITEM_LIMIT_NUMBER = 5;//购物车中单个商品的最大购买数量(可根据自身需求修改)

    public final static String MALL_VERIFY_CODE_KEY = "mallVerifyCode";//验证码key

    public final static String MALL_USER_SESSION_KEY = "newBeeMallUser";//session中user的key

    public final static int GOODS_SEARCH_PAGE_LIMIT = 10;//搜索分页的默认条数(每页10条)

    public final static int ORDER_SEARCH_PAGE_LIMIT = 3;//我的订单列表分页的默认条数(每页3条)

    public final static int SELL_STATUS_UP = 0;//商品上架状态
    public final static int SELL_STATUS_DOWN = 1;//商品下架状态

}
