
package com.flower.mall.controller.admin;

import com.flower.mall.entity.GoodsCategory;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.service.FlowerMallCategoryService;
import com.flower.mall.service.FlowerMallGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/admin")
@Slf4j
public class RouteMallGoodsController {

    @Resource
    private FlowerMallGoodsService flowerMallGoodsService;
    @Resource
    private FlowerMallCategoryService flowerMallCategoryService;

    @GetMapping("/carousels")
    public String carouselPage(HttpServletRequest request) {
        request.setAttribute("path", "mall_carousel");
        return "admin/flower_mall_carousel";
    }

    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "mall_goods");
        return "admin/newbee_mall_goods";
    }

    //新增商品
    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        List<GoodsCategory> categoryList = flowerMallCategoryService.getCategoryList();
        request.setAttribute("path", "edit");
        request.setAttribute("goodsCategoryList", categoryList);
        return "admin/newbee_mall_goods_edit";
    }

    //选中商品修改
    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        MallGoods mallGoods = flowerMallGoodsService.getFlowerMallGoodsById(goodsId);
        List<GoodsCategory> categoryList = flowerMallCategoryService.getCategoryList();
        request.setAttribute("goodsCategoryList", categoryList);
        request.setAttribute("goods", mallGoods);
        request.setAttribute("path", "goods-edit");
        return "admin/newbee_mall_goods_edit";
    }








}