
package com.flower.mall.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首页分类数据VO(第二级)
 */
@Data
public class FlowerMallSecondLevelCategoryVO implements Serializable {

    private Long categoryId;

    private Long parentId;

    private Byte categoryLevel;

    private String categoryName;


    private List<FlowerMallThirdLevelCategoryVO> thirdLevelCategoryVOS;


}
