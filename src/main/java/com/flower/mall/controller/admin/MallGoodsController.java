package com.flower.mall.controller.admin;


import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.service.FlowerMallGoodsService;
import com.flower.mall.util.MallResult;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * 花花商品的数据库操作层
 */
@Controller
public class MallGoodsController {
    @Resource
    private FlowerMallGoodsService flowerMallGoodsService;

    /**
     * 添加商品
     */
    @PostMapping(value = "/admin/goods/save")
    @ResponseBody
    public Result save(@RequestBody MallGoods mallGoods) {
        //校验参数
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
        //保存商品 返回 成功或者失败
        String result = flowerMallGoodsService.saveFlowerMallGoods(mallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes(result);
        }
    }

    /**
     * 修改 商品明细
     */
    @PostMapping(value = "/admin/goods/update")
    @ResponseBody
    public Result update(@RequestBody MallGoods mallGoods) {
        //校验参数
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
        //更新商品
        String result = flowerMallGoodsService.updateGoods(mallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes(result);
        }
    }

    /**
     * 列表
     */
    @GetMapping(value = "/admin/goods/list")
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return MallResult.createSuccessRes(flowerMallGoodsService.getFlowerMallGoodsPage(pageUtil));
    }

    /**
     * 批量修改销售状态  选中商品的id  更新为那个状态 要么上架要么下架
     */
    @PutMapping(value = "/admin/goods/status/{sellStatus}")
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (flowerMallGoodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes("修改失败");
        }
    }

    /**
     * 详情  根据商品id
     */
    @GetMapping("/admin/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        MallGoods goods = flowerMallGoodsService.getFlowerMallGoodsById(id);
        return MallResult.createSuccessRes(goods);
    }
}
