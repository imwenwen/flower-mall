
package com.flower.mall.controller.mall;

import com.flower.mall.common.IndexConfigTypeEnum;
import com.flower.mall.controller.vo.FlowerMallIndexCarouselVO;
import com.flower.mall.controller.vo.FlowerMallIndexCategoryVO;
import com.flower.mall.controller.vo.FlowerMallIndexConfigGoodsVO;
import com.flower.mall.service.FlowerMallCarouselService;
import com.flower.mall.service.FlowerMallCategoryService;
import com.flower.mall.service.FlowerMallIndexConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private FlowerMallCarouselService flowerMallCarouselService;

    @Resource
    private FlowerMallIndexConfigService flowerMallIndexConfigService;

    @Resource
    private FlowerMallCategoryService flowerMallCategoryService;

    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        List<FlowerMallIndexCarouselVO> carousels = flowerMallCarouselService.getCarouselsForIndex(5);
        List<FlowerMallIndexConfigGoodsVO> hotGoods = flowerMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), 5);
        List<FlowerMallIndexConfigGoodsVO> newGoods = flowerMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), 5);
        List<FlowerMallIndexConfigGoodsVO> recommendGoods = flowerMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), 10);
        request.setAttribute("carousels", carousels);//轮播图
        request.setAttribute("hotGoodses", hotGoods);//热销商品
        request.setAttribute("newGoodses", newGoods);//新品
        request.setAttribute("recommendGoodses", recommendGoods);//推荐商品
        return "mall/index";
    }
}
