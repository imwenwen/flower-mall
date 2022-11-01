
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


}
