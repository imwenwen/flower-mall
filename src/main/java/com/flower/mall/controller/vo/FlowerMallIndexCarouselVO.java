
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 首页轮播图VO
 */
@Data
public class FlowerMallIndexCarouselVO implements Serializable {

    private String carouselUrl;

    private String redirectUrl;
}
