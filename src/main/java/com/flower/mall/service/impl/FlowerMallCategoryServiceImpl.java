
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

    @Override
    public boolean checkCategoryByName(String categoryName) {
        //判断名字是否重复 count=1 已经重复了 不能在添加了
      int count =  this.goodsCategoryMapper.checkCategoryByName(categoryName);
      if(count==1){
          return true;
      }else {
          return false;
      }
    }

    @Override
    public void addCategory(String categoryName) {
        this.goodsCategoryMapper.addCategory(categoryName);
    }
}
