
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品详情页VO
 */
@Data
public class FlowerMallGoodsDetailVO implements Serializable {

    //商品id
    private Long goodsId;

    //商品名称
    private String goodsName;

    //商品介绍
    private String goodsIntro;

    //商品主图
    private String goodsCoverImg;

    //商品轮播图
    private String[] goodsCarouselList;
    //商品销售价格
    private Integer sellingPrice;

    //商品原始价格
    private Integer originalPrice;
    //商品详情
    private String goodsDetailContent;

}
