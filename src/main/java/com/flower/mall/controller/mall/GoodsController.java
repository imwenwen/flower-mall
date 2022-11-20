
package com.flower.mall.controller.mall;

import com.flower.mall.common.Constants;
import com.flower.mall.common.MyException;
import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallGoodsDetailVO;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.service.FlowerMallGoodsService;
import com.flower.mall.util.BeanUtil;
import com.flower.mall.util.PageQueryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class GoodsController {

    @Resource
    private FlowerMallGoodsService flowerMallGoodsService;


    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //没有传页码 默认第一页
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        //默认一页10条
        params.put("limit", 10);

        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", flowerMallGoodsService.searchGoods(pageUtil));
        return "mall/search";
    }

    /**
     * 根据花花id  获取花花详情
     * @param goodsId
     * @param request
     * @return
     */
    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        //根据id获取花花
        MallGoods goods = flowerMallGoodsService.getFlowerMallGoodsById(goodsId);
        //花花如果不是上架状态 提示错误
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            MyException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        FlowerMallGoodsDetailVO goodsDetailVO = new FlowerMallGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        request.setAttribute("goodsDetail", goodsDetailVO);
        return "mall/detail";
    }

}
