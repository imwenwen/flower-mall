
package com.flower.mall.controller.admin;

import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.entity.Carousel;
import com.flower.mall.service.FlowerMallCarouselService;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.Result;
import com.flower.mall.util.MallResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
public class MallCarouselController {

    @Resource
    FlowerMallCarouselService flowerMallCarouselService;

    @GetMapping("/carousels")
    public String carouselPage(HttpServletRequest request) {
        request.setAttribute("path", "mall_carousel");
        return "admin/flower_mall_carousel";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/carousels/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return MallResult.createFailRes("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return MallResult.createSuccessRes(flowerMallCarouselService.getCarouselPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/carousels/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Carousel carousel) {
        if (StringUtils.isEmpty(carousel.getCarouselUrl())
                || Objects.isNull(carousel.getCarouselRank())) {
            return MallResult.createFailRes("参数异常！");
        }
        String result = flowerMallCarouselService.saveCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/carousels/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Carousel carousel) {
        if (Objects.isNull(carousel.getCarouselId())
                || StringUtils.isEmpty(carousel.getCarouselUrl())
                || Objects.isNull(carousel.getCarouselRank())) {
            return MallResult.createFailRes("参数异常！");
        }
        String result = flowerMallCarouselService.updateCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/carousels/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        Carousel carousel = flowerMallCarouselService.getCarouselById(id);
        if (carousel == null) {
            return MallResult.createFailRes(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return MallResult.createSuccessRes(carousel);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/carousels/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return MallResult.createFailRes("参数异常！");
        }
        if (flowerMallCarouselService.deleteBatch(ids)) {
            return MallResult.createSuccessRes();
        } else {
            return MallResult.createFailRes("删除失败");
        }
    }

}