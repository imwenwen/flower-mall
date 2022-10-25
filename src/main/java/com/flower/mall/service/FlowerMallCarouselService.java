
package com.flower.mall.service;

import com.flower.mall.controller.vo.FlowerMallIndexCarouselVO;
import com.flower.mall.entity.Carousel;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.PageResult;

import java.util.List;

public interface FlowerMallCarouselService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    List<FlowerMallIndexCarouselVO> getCarouselsForIndex(int number);
}
