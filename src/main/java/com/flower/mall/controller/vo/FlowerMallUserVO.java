
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FlowerMallUserVO implements Serializable {

    //用户id
    private Long userId;

    //昵称
    private String nickName;

    //手机号
    private String loginName;

    //个性签名
    private String introduceSign;

    //收货地址
    private String address;

    //购物车数量
    private int shopCartItemCount;
}
