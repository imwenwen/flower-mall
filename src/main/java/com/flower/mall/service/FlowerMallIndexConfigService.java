
package com.flower.mall.service;

import com.flower.mall.controller.vo.FlowerMallIndexConfigGoodsVO;
import com.flower.mall.entity.IndexConfig;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.PageResult;

import java.util.List;

public interface FlowerMallIndexConfigService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getConfigsPage(PageQueryUtil pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);

    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     *
     * @param number
     * @return
     */
    List<FlowerMallIndexConfigGoodsVO> getConfigGoodsBy(int configType, int number);

    Boolean deleteBatch(Long[] ids);
}
