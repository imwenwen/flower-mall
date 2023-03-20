
package com.flower.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MallGoods {
    /**
     * 商品主键
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品介绍
     */
    private String goodsIntro;

    /**
     * 商品类别Id
     */
    private Long goodsCategoryId;

    /**
     * 商品封面图片
     */
    private String goodsCoverImg;

    /**
     * 商品轮播图
     */
    private String goodsCarousel;
    /**
     * 原始价格
     */
    private Integer originalPrice;
    /**
     * 销售价格
     */
    private Integer sellingPrice;
    /**
     * 库存
     */
    private Integer stockNum;
    /**
     * 标签
     */
    private String tag;
    /**
     * 销售状态
     */
    private Byte goodsSellStatus;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    //商品详情
    private String goodsDetailContent;
}