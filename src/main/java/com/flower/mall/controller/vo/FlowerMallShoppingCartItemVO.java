
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 购物车页面购物项VO
 */
@Data
public class FlowerMallShoppingCartItemVO implements Serializable {

    private Long cartItemId;

    private Long goodsId;

    private Integer goodsCount;

    private String goodsName;

    private String goodsCoverImg;

    private Integer sellingPrice;
}
