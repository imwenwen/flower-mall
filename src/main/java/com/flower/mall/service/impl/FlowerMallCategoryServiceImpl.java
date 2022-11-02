
package com.flower.mall.service.impl;

import com.flower.mall.dao.GoodsCategoryMapper;
import com.flower.mall.entity.GoodsCategory;
import com.flower.mall.service.FlowerMallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowerMallCategoryServiceImpl implements FlowerMallCategoryService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;


    @Override
    public List<GoodsCategory> getCategoryList() {
        return this.goodsCategoryMapper.getCategoryList();
    }
}
