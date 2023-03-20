
package com.flower.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.PageResult;

public interface FlowerMallGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getFlowerMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveFlowerMallGoods(MallGoods goods);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateGoods(MallGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    MallGoods getFlowerMallGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

    /**
     * 商品搜索
     *
     * @param keyword 关键词
     * @return
     */
    PageResult searchGoodsBy(String keyword, IPage iPage, int goodsSellStatus);
}
