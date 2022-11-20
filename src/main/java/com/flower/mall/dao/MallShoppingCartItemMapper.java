
package com.flower.mall.dao;

import com.flower.mall.entity.MallShoppingCartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MallShoppingCartItemMapper {
    int deleteByPrimaryKey(Long cartItemId);

    int insert(MallShoppingCartItem record);

    int insertSelective(MallShoppingCartItem record);

    MallShoppingCartItem selectByPrimaryKey(Long cartItemId);

    MallShoppingCartItem selectByUserIdAndGoodsId(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    List<MallShoppingCartItem> selectByUserId(@Param("userId") Long userId);

    int selectCountByUserId(Long userId);

    int updateByPrimaryKeySelective(MallShoppingCartItem record);

    int updateByPrimaryKey(MallShoppingCartItem record);

    int deleteBatch(List<Long> ids);
}