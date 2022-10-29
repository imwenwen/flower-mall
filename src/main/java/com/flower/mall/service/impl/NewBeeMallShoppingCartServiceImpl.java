
package com.flower.mall.service.impl;

import com.flower.mall.common.Constants;
import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallShoppingCartItemVO;
import com.flower.mall.dao.NewBeeMallGoodsMapper;
import com.flower.mall.dao.NewBeeMallShoppingCartItemMapper;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.entity.MallShoppingCartItem;
import com.flower.mall.service.NewBeeMallShoppingCartService;
import com.flower.mall.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NewBeeMallShoppingCartServiceImpl implements NewBeeMallShoppingCartService {

    @Autowired
    private NewBeeMallShoppingCartItemMapper newBeeMallShoppingCartItemMapper;

    @Autowired
    private NewBeeMallGoodsMapper newBeeMallGoodsMapper;

    @Override
    public String saveNewBeeMallCartItem(MallShoppingCartItem mallShoppingCartItem) {
        MallShoppingCartItem temp = newBeeMallShoppingCartItemMapper.selectByUserIdAndGoodsId(mallShoppingCartItem.getUserId(), mallShoppingCartItem.getGoodsId());
        if (temp != null) {
            //已存在则修改该记录
            temp.setGoodsCount(mallShoppingCartItem.getGoodsCount());
            return updateNewBeeMallCartItem(temp);
        }
        MallGoods mallGoods = newBeeMallGoodsMapper.selectByPrimaryKey(mallShoppingCartItem.getGoodsId());
        //商品为空
        if (mallGoods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = newBeeMallShoppingCartItemMapper.selectCountByUserId(mallShoppingCartItem.getUserId()) + 1;
        //超出单个商品的最大数量
        if (mallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
        //保存记录
        if (newBeeMallShoppingCartItemMapper.insertSelective(mallShoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateNewBeeMallCartItem(MallShoppingCartItem mallShoppingCartItem) {
        MallShoppingCartItem mallShoppingCartItemUpdate = newBeeMallShoppingCartItemMapper.selectByPrimaryKey(mallShoppingCartItem.getCartItemId());
        if (mallShoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        //超出单个商品的最大数量
        if (mallShoppingCartItem.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //当前登录账号的userId与待修改的cartItem中userId不同，返回错误
        if (!mallShoppingCartItemUpdate.getUserId().equals(mallShoppingCartItem.getUserId())) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
        }
        //数值相同，则不执行数据操作
        if (mallShoppingCartItem.getGoodsCount().equals(mallShoppingCartItemUpdate.getGoodsCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        mallShoppingCartItemUpdate.setGoodsCount(mallShoppingCartItem.getGoodsCount());
        mallShoppingCartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (newBeeMallShoppingCartItemMapper.updateByPrimaryKeySelective(mallShoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public MallShoppingCartItem getNewBeeMallCartItemById(Long newBeeMallShoppingCartItemId) {
        return newBeeMallShoppingCartItemMapper.selectByPrimaryKey(newBeeMallShoppingCartItemId);
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId, Long userId) {
        MallShoppingCartItem mallShoppingCartItem = newBeeMallShoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemId);
        if (mallShoppingCartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!userId.equals(mallShoppingCartItem.getUserId())) {
            return false;
        }
        return newBeeMallShoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }

    @Override
    public List<FlowerMallShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId) {
        List<FlowerMallShoppingCartItemVO> flowerMallShoppingCartItemVOS = new ArrayList<>();
        List<MallShoppingCartItem> mallShoppingCartItems = newBeeMallShoppingCartItemMapper.selectByUserId(newBeeMallUserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        if (!CollectionUtils.isEmpty(mallShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> newBeeMallGoodsIds = mallShoppingCartItems.stream().map(MallShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<MallGoods> mallGoods = newBeeMallGoodsMapper.selectByPrimaryKeys(newBeeMallGoodsIds);
            Map<Long, MallGoods> newBeeMallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(mallGoods)) {
                newBeeMallGoodsMap = mallGoods.stream().collect(Collectors.toMap(MallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (MallShoppingCartItem mallShoppingCartItem : mallShoppingCartItems) {
                FlowerMallShoppingCartItemVO flowerMallShoppingCartItemVO = new FlowerMallShoppingCartItemVO();
                BeanUtil.copyProperties(mallShoppingCartItem, flowerMallShoppingCartItemVO);
                if (newBeeMallGoodsMap.containsKey(mallShoppingCartItem.getGoodsId())) {
                    MallGoods mallGoodsTemp = newBeeMallGoodsMap.get(mallShoppingCartItem.getGoodsId());
                    flowerMallShoppingCartItemVO.setGoodsCoverImg(mallGoodsTemp.getGoodsCoverImg());
                    String goodsName = mallGoodsTemp.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    flowerMallShoppingCartItemVO.setGoodsName(goodsName);
                    flowerMallShoppingCartItemVO.setSellingPrice(mallGoodsTemp.getSellingPrice());
                    flowerMallShoppingCartItemVOS.add(flowerMallShoppingCartItemVO);
                }
            }
        }
        return flowerMallShoppingCartItemVOS;
    }
}
