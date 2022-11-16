
package com.flower.mall.controller.mall;

import com.flower.mall.common.IndexConfigTypeEnum;
import com.flower.mall.controller.vo.FlowerMallIndexCarouselVO;
import com.flower.mall.controller.vo.FlowerMallIndexConfigGoodsVO;
import com.flower.mall.service.FlowerMallCarouselService;
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

    /**
     * 进入首页前 会请求的方法 查询轮播图、热门、新品、为你推荐的数据
     * @param request
     * @return
     */
    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        //查询轮播图 默认5张
        List<FlowerMallIndexCarouselVO> carousels = flowerMallCarouselService.getCarouselsForIndex(5);
        //查询热门花花 默认5个
        List<FlowerMallIndexConfigGoodsVO> hotGoods = flowerMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), 5);
        //查询新品花花 默认5个
        List<FlowerMallIndexConfigGoodsVO> newGoods = flowerMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), 5);
        //查询推荐花花 默认5个
        List<FlowerMallIndexConfigGoodsVO> recommendGoods = flowerMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), 10);
        //轮播图
        request.setAttribute("carousels", carousels);
        //热销商品
        request.setAttribute("hotGoodses", hotGoods);
        //新品
        request.setAttribute("newGoodses", newGoods);
        //推荐商品
        request.setAttribute("recommendGoodses", recommendGoods);
        return "mall/index";
    }
}
