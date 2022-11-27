
package com.flower.mall.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MallShoppingCartItem {
    //购物项主键id
    private Long cartItemId;
    //用户主键id
    private Long userId;
    //关联商品id
    private Long goodsId;
    //数量
    private Integer goodsCount;
    //删除标识字段0-未删除 1-已删除
    private Byte deleted;
    //创建时间
    private Date createTime;
    //最新修改时间
    private Date updateTime;


}