
package com.flower.mall.controller.admin;

import com.flower.mall.common.Constants;
import com.flower.mall.common.NewBeeMallCategoryLevelEnum;
import com.flower.mall.common.NewBeeMallException;
import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.entity.GoodsCategory;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.service.FlowerMallCategoryService;
import com.flower.mall.service.NewBeeMallGoodsService;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.Result;
import com.flower.mall.util.MallResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
public class NewBeeMallGoodsController {

    @Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;
    @Resource
    private FlowerMallCategoryService flowerMallCategoryService;

    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_goods");
        return "admin/newbee_mall_goods";
    }

    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        return "admin/newbee_mall_goods_edit";
    }

    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        MallGoods mallGoods = newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId);
        request.setAttribute("goods", mallGoods);
        request.setAttribute("path", "goods-edit");
        return "admin/newbee_mall_goods_edit";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return MallResult.createSuccessRes(newBeeMallGoodsService.getNewBeeMallGoodsPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody MallGoods mallGoods) {
        if (StringUtils.isBlank(mallGoods.getGoodsName())
                || StringUtils.isBlank(mallGoods.getGoodsIntro())
                || StringUtils.isBlank(mallGoods.getTag())
                || Objects.isNull(mallGoods.getOriginalPrice())
                || Objects.isNull(mallGoods.getGoodsCategoryId())
                || Objects.isNull(mallGoods.getSellingPrice())
                || Objects.isNull(mallGoods.getStockNum())
                || Objects.isNull(mallGoods.getGoodsSellStatus())
                || StringUtils.isBlank(mallGoods.getGoodsCoverImg())
                || StringUtils.isBlank(mallGoods.getGoodsDetailContent())) {
            return MallResult.createFailRes("参数异常！");
        }
        String result = newBeeMallGoodsService.saveNewBeeMallGoods(mallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody MallGoods mallGoods) {
        if (Objects.isNull(mallGoods.getGoodsId())
                || org.apache.commons.lang3.StringUtils.isBlank(mallGoods.getGoodsName())
                || StringUtils.isBlank(mallGoods.getGoodsIntro())
                || StringUtils.isBlank(mallGoods.getTag())
                || Objects.isNull(mallGoods.getOriginalPrice())
                || Objects.isNull(mallGoods.getSellingPrice())
                || Objects.isNull(mallGoods.getGoodsCategoryId())
                || Objects.isNull(mallGoods.getStockNum())
                || Objects.isNull(mallGoods.getGoodsSellStatus())
                || StringUtils.isBlank(mallGoods.getGoodsCoverImg())
                || StringUtils.isBlank(mallGoods.getGoodsDetailContent())) {
            return MallResult.createFailRes("参数异常！");
        }
        String result = newBeeMallGoodsService.updateNewBeeMallGoods(mallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        MallGoods goods = newBeeMallGoodsService.getNewBeeMallGoodsById(id);
        return MallResult.createSuccessRes(goods);
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return MallResult.createFailRes("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return MallResult.createFailRes("状态异常！");
        }
        if (newBeeMallGoodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes("修改失败");
        }
    }

}