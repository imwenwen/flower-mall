
package com.flower.mall.controller.admin;

import com.flower.mall.entity.Carousel;
import com.flower.mall.entity.GoodsCategory;
import com.flower.mall.service.FlowerMallCategoryService;
import com.flower.mall.util.MallResult;
import com.flower.mall.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 花花分类
 */
@Controller
@RequestMapping("/admin")
public class GoodsCategoryController {

    @Resource
    private FlowerMallCategoryService flowerMallCategoryService;


    /**
     * 添加分类
     */
    @RequestMapping(value = "/category/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody GoodsCategory goodsCategory) {
        //1.判断是否存在同一个名字的分类，重复的话falg=true
        String categoryName =goodsCategory.getCategoryName();
        boolean flag = flowerMallCategoryService.checkCategoryByName(categoryName);
        if (flag) {
            return MallResult.createFailRes("存在相同分类！");
        }
        //2.不重复则直接添加数据
        flowerMallCategoryService.addCategory(categoryName);
        return MallResult.createSuccessRes();
    }


}