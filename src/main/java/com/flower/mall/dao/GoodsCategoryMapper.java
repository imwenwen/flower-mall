
package com.flower.mall.dao;

import com.flower.mall.entity.GoodsCategory;
import com.flower.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsCategoryMapper {
    List<GoodsCategory> getCategoryList();

    int checkCategoryByName(@Param("categoryName") String categoryName);

    void addCategory(@Param("categoryName")String categoryName);
}
