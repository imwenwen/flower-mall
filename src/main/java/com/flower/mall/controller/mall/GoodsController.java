
package com.flower.mall.controller.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flower.mall.common.Constants;
import com.flower.mall.common.MyException;
import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallGoodsDetailVO;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.service.FlowerMallGoodsService;
import com.flower.mall.util.BeanUtil;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.PageResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class GoodsController {

    @Resource
    private FlowerMallGoodsService flowerMallGoodsService;


    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam String keyword, HttpServletRequest request) {
        //根据关键字，默认1页100条，搜索商品为上架状态的商品
        PageResult pageResult =  flowerMallGoodsService.searchGoodsBy(keyword,new Page<>(1,100),Constants.SELL_STATUS_UP);
        request.setAttribute("pageResult", pageResult);
        request.setAttribute("keyword", keyword);
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
        //根据商品id获取花花
        MallGoods goods = flowerMallGoodsService.getFlowerMallGoodsById(goodsId);
        //花花如果不是上架状态 提示错误
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            MyException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        request.setAttribute("goodsDetail", goods);
        return "mall/detail";
    }

}
