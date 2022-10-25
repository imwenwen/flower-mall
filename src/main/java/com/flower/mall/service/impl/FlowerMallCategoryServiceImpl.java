
package com.flower.mall.service.impl;

import com.flower.mall.common.Constants;
import com.flower.mall.common.NewBeeMallCategoryLevelEnum;
import com.flower.mall.common.NewBeeMallException;
import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallIndexCategoryVO;
import com.flower.mall.controller.vo.FlowerMallSearchPageCategoryVO;
import com.flower.mall.controller.vo.FlowerMallSecondLevelCategoryVO;
import com.flower.mall.controller.vo.FlowerMallThirdLevelCategoryVO;
import com.flower.mall.dao.GoodsCategoryMapper;
import com.flower.mall.entity.GoodsCategory;
import com.flower.mall.service.FlowerMallCategoryService;
import com.flower.mall.util.BeanUtil;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class FlowerMallCategoryServiceImpl implements FlowerMallCategoryService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public PageResult getCategorisPage(PageQueryUtil pageUtil) {
        List<GoodsCategory> goodsCategories = goodsCategoryMapper.findGoodsCategoryList(pageUtil);
        int total = goodsCategoryMapper.getTotalGoodsCategories(pageUtil);
        PageResult pageResult = new PageResult(goodsCategories, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveCategory(GoodsCategory goodsCategory) {
        GoodsCategory temp = goodsCategoryMapper.selectByLevelAndName(goodsCategory.getCategoryLevel(), goodsCategory.getCategoryName());
        if (temp != null) {
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }
        if (goodsCategoryMapper.insertSelective(goodsCategory) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateGoodsCategory(GoodsCategory goodsCategory) {
        GoodsCategory temp = goodsCategoryMapper.selectByPrimaryKey(goodsCategory.getCategoryId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        GoodsCategory temp2 = goodsCategoryMapper.selectByLevelAndName(goodsCategory.getCategoryLevel(), goodsCategory.getCategoryName());
        if (temp2 != null && !temp2.getCategoryId().equals(goodsCategory.getCategoryId())) {
            //同名且不同id 不能继续修改
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }
        goodsCategory.setUpdateTime(new Date());
        if (goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public GoodsCategory getGoodsCategoryById(Long id) {
        return goodsCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除分类数据
        return goodsCategoryMapper.deleteBatch(ids) > 0;
    }

    @Override
    public List<FlowerMallIndexCategoryVO> getCategoriesForIndex() {
        List<FlowerMallIndexCategoryVO> flowerMallIndexCategoryVOS = new ArrayList<>();
        //获取一级分类的固定数量的数据
        List<GoodsCategory> firstLevelCategories = goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel(), Constants.INDEX_CATEGORY_NUMBER);
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            List<Long> firstLevelCategoryIds = firstLevelCategories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());
            //获取二级分类的数据
            List<GoodsCategory> secondLevelCategories = goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(firstLevelCategoryIds, NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel(), 0);
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                List<Long> secondLevelCategoryIds = secondLevelCategories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());
                //获取三级分类的数据
                List<GoodsCategory> thirdLevelCategories = goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(secondLevelCategoryIds, NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel(), 0);
                if (!CollectionUtils.isEmpty(thirdLevelCategories)) {
                    //根据 parentId 将 thirdLevelCategories 分组
                    Map<Long, List<GoodsCategory>> thirdLevelCategoryMap = thirdLevelCategories.stream().collect(groupingBy(GoodsCategory::getParentId));
                    List<FlowerMallSecondLevelCategoryVO> flowerMallSecondLevelCategoryVOS = new ArrayList<>();
                    //处理二级分类
                    for (GoodsCategory secondLevelCategory : secondLevelCategories) {
                        FlowerMallSecondLevelCategoryVO flowerMallSecondLevelCategoryVO = new FlowerMallSecondLevelCategoryVO();
                        BeanUtil.copyProperties(secondLevelCategory, flowerMallSecondLevelCategoryVO);
                        //如果该二级分类下有数据则放入 secondLevelCategoryVOS 对象中
                        if (thirdLevelCategoryMap.containsKey(secondLevelCategory.getCategoryId())) {
                            //根据二级分类的id取出thirdLevelCategoryMap分组中的三级分类list
                            List<GoodsCategory> tempGoodsCategories = thirdLevelCategoryMap.get(secondLevelCategory.getCategoryId());
                            flowerMallSecondLevelCategoryVO.setThirdLevelCategoryVOS((BeanUtil.copyList(tempGoodsCategories, FlowerMallThirdLevelCategoryVO.class)));
                            flowerMallSecondLevelCategoryVOS.add(flowerMallSecondLevelCategoryVO);
                        }
                    }
                    //处理一级分类
                    if (!CollectionUtils.isEmpty(flowerMallSecondLevelCategoryVOS)) {
                        //根据 parentId 将 thirdLevelCategories 分组
                        Map<Long, List<FlowerMallSecondLevelCategoryVO>> secondLevelCategoryVOMap = flowerMallSecondLevelCategoryVOS.stream().collect(groupingBy(FlowerMallSecondLevelCategoryVO::getParentId));
                        for (GoodsCategory firstCategory : firstLevelCategories) {
                            FlowerMallIndexCategoryVO flowerMallIndexCategoryVO = new FlowerMallIndexCategoryVO();
                            BeanUtil.copyProperties(firstCategory, flowerMallIndexCategoryVO);
                            //如果该一级分类下有数据则放入 newBeeMallIndexCategoryVOS 对象中
                            if (secondLevelCategoryVOMap.containsKey(firstCategory.getCategoryId())) {
                                //根据一级分类的id取出secondLevelCategoryVOMap分组中的二级级分类list
                                List<FlowerMallSecondLevelCategoryVO> tempGoodsCategories = secondLevelCategoryVOMap.get(firstCategory.getCategoryId());
                                flowerMallIndexCategoryVO.setFlowerMallSecondLevelCategoryVOS(tempGoodsCategories);
                                flowerMallIndexCategoryVOS.add(flowerMallIndexCategoryVO);
                            }
                        }
                    }
                }
            }
            if (CollectionUtils.isEmpty(flowerMallIndexCategoryVOS)) {
                NewBeeMallException.fail("分类数据不完善");
            }
            return flowerMallIndexCategoryVOS;
        } else {
            return null;
        }
    }

    @Override
    public FlowerMallSearchPageCategoryVO getCategoriesForSearch(Long categoryId) {
        FlowerMallSearchPageCategoryVO flowerMallSearchPageCategoryVO = new FlowerMallSearchPageCategoryVO();
        GoodsCategory thirdLevelGoodsCategory = goodsCategoryMapper.selectByPrimaryKey(categoryId);
        if (thirdLevelGoodsCategory != null && thirdLevelGoodsCategory.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            //获取当前三级分类的二级分类
            GoodsCategory secondLevelGoodsCategory = goodsCategoryMapper.selectByPrimaryKey(thirdLevelGoodsCategory.getParentId());
            if (secondLevelGoodsCategory != null && secondLevelGoodsCategory.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel()) {
                //获取当前二级分类下的三级分类List
                List<GoodsCategory> thirdLevelCategories = goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelGoodsCategory.getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel(), Constants.SEARCH_CATEGORY_NUMBER);
                flowerMallSearchPageCategoryVO.setCurrentCategoryName(thirdLevelGoodsCategory.getCategoryName());
                flowerMallSearchPageCategoryVO.setSecondLevelCategoryName(secondLevelGoodsCategory.getCategoryName());
                flowerMallSearchPageCategoryVO.setThirdLevelCategoryList(thirdLevelCategories);
                return flowerMallSearchPageCategoryVO;
            }
        }
        return null;
    }

    @Override
    public List<GoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel) {
        return goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(parentIds, categoryLevel, 0);//0代表查询所有
    }
}
