
package com.flower.mall.service;

import com.flower.mall.controller.vo.FlowerMallIndexCarouselVO;
import com.flower.mall.entity.Carousel;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.PageResult;

import java.util.List;

public interface FlowerMallCarouselService {

   //轮播图列表
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    //轮播图列表
    String saveCarousel(Carousel carousel);
    //轮播图更新
    String updateCarousel(Carousel carousel);
    //根据id获取轮播图
    Carousel getCarouselById(Integer id);
    //批量删除轮播图
    Boolean deleteBatch(Integer[] ids);

    /**
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    List<FlowerMallIndexCarouselVO> getCarouselsBy(int number);
}
