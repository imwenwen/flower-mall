
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首页分类数据VO
 */
@Data
public class FlowerMallIndexCategoryVO implements Serializable {

    private Long categoryId;

    private Byte categoryLevel;

    private String categoryName;

    private List<FlowerMallSecondLevelCategoryVO> flowerMallSecondLevelCategoryVOS;

}
