
package com.flower.mall.service;

import com.flower.mall.controller.vo.FlowerMallShoppingCartItemVO;
import com.flower.mall.entity.MallShoppingCartItem;

import java.util.List;

public interface NewBeeMallShoppingCartService {

    /**
     * 保存商品至购物车中
     *
     * @param mallShoppingCartItem
     * @return
     */
    String saveNewBeeMallCartItem(MallShoppingCartItem mallShoppingCartItem);

    /**
     * 修改购物车中的属性
     *
     * @param mallShoppingCartItem
     * @return
     */
    String updateNewBeeMallCartItem(MallShoppingCartItem mallShoppingCartItem);

    /**
     * 获取购物项详情
     *
     * @param newBeeMallShoppingCartItemId
     * @return
     */
    MallShoppingCartItem getNewBeeMallCartItemById(Long newBeeMallShoppingCartItemId);

    /**
     * 删除购物车中的商品
     *
     *
     * @param shoppingCartItemId
     * @param userId
     * @return
     */
    Boolean deleteById(Long shoppingCartItemId, Long userId);

    /**
     * 获取我的购物车中的列表数据
     *
     * @param newBeeMallUserId
     * @return
     */
    List<FlowerMallShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId);
}
