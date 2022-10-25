
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 首页分类数据VO(第三级)
 */
@Data
public class FlowerMallThirdLevelCategoryVO implements Serializable {

    private Long categoryId;

    private Byte categoryLevel;

    private String categoryName;
}
