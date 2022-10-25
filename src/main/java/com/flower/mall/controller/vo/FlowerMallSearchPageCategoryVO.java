
package com.flower.mall.controller.vo;

import lombok.Data;
import com.flower.mall.entity.GoodsCategory;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索页面分类数据VO
 */
@Data
public class FlowerMallSearchPageCategoryVO implements Serializable {

    private String firstLevelCategoryName;

    private List<GoodsCategory> secondLevelCategoryList;

    private String secondLevelCategoryName;

    private List<GoodsCategory> thirdLevelCategoryList;

    private String currentCategoryName;
}
