
package com.flower.mall.service;

import com.flower.mall.entity.MallGoods;
import com.flower.mall.util.PageQueryUtil;
import com.flower.mall.util.PageResult;

import java.util.List;

public interface NewBeeMallGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveNewBeeMallGoods(MallGoods goods);

    /**
     * 批量新增商品数据
     *
     * @param mallGoodsList
     * @return
     */
    void batchSaveNewBeeMallGoods(List<MallGoods> mallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateNewBeeMallGoods(MallGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    MallGoods getNewBeeMallGoodsById(Long id);

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
     * @param pageUtil
     * @return
     */
    PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil);
}
