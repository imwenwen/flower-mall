
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 首页配置商品VO
 */
@Data
public class FlowerMallIndexConfigGoodsVO implements Serializable {

    //商品id
    private Long goodsId;
    //商品名称
    private String goodsName;
    /**
     * 商品介绍
     */
    private String goodsIntro;
    /**
     * 商品封面图片
     */
    private String goodsCoverImg;
    /**
     * 销售价格
     */
    private Integer sellingPrice;
    /**
     * 标签
     */
    private String tag;

}
