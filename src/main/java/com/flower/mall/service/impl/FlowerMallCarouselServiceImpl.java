
package com.flower.mall.service.impl;

import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallIndexCarouselVO;
import com.flower.mall.dao.CarouselMapper;
import com.flower.mall.entity.Carousel;
import com.flower.mall.service.FlowerMallCarouselService;
import com.flower.mall.util.BeanUtil;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FlowerMallCarouselServiceImpl implements FlowerMallCarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public PageResult getCarouselPage(PageQueryUtil pageUtil) {
        List<Carousel> carousels = carouselMapper.findCarouselList(pageUtil);
        int total = carouselMapper.getTotalCarousels(pageUtil);
        PageResult pageResult = new PageResult(carousels, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveCarousel(Carousel carousel) {
        if (carouselMapper.insertSelective(carousel) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateCarousel(Carousel carousel) {
        Carousel temp = carouselMapper.selectByPrimaryKey(carousel.getCarouselId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        temp.setCarouselRank(carousel.getCarouselRank());
        temp.setCarouselUrl(carousel.getCarouselUrl());
        temp.setUpdateTime(new Date());
        if (carouselMapper.updateByPrimaryKeySelective(temp) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Carousel getCarouselById(Integer id) {
        return carouselMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return carouselMapper.deleteBatch(ids) > 0;
    }

    @Override
    public List<FlowerMallIndexCarouselVO> getCarouselsBy(int number) {
        List<FlowerMallIndexCarouselVO> flowerMallIndexCarousels = new ArrayList<>(number);
        //查询 number大小的轮播图 默认number=5,隐藏条件是根据carousel_rank排序值(字段越大越靠前)
        List<Carousel> carousels = carouselMapper.findCarouselsByNum(number);
        if (!CollectionUtils.isEmpty(carousels)) {
            //拷贝复制方法 将获取到的carousels对象转变成我们前端页面要展示的FlowerMallIndexCarouselVO
            flowerMallIndexCarousels = BeanUtil.copyList(carousels, FlowerMallIndexCarouselVO.class);
        }
        return flowerMallIndexCarousels;
    }
}
