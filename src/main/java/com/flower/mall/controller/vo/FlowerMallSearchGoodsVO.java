
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 搜索列表页商品VO
 */
@Data
public class FlowerMallSearchGoodsVO implements Serializable {

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private String goodsCoverImg;

    private Integer sellingPrice;


}
