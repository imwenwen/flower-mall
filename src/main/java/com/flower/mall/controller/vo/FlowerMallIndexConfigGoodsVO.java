
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 首页配置商品VO
 */
@Data
public class FlowerMallIndexConfigGoodsVO implements Serializable {

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private String goodsCoverImg;

    private Integer sellingPrice;

    private String tag;

}
