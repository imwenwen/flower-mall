
package com.flower.mall.service.impl;

import com.flower.mall.common.ServiceResultEnum;
import com.flower.mall.controller.vo.FlowerMallShoppingCartItemVO;
import com.flower.mall.dao.MallGoodsMapper;
import com.flower.mall.dao.MallShoppingCartItemMapper;
import com.flower.mall.entity.MallGoods;
import com.flower.mall.entity.MallShoppingCartItem;
import com.flower.mall.service.ShoppingCartService;
import com.flower.mall.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private MallShoppingCartItemMapper mallShoppingCartItemMapper;

    @Autowired
    private MallGoodsMapper mallGoodsMapper;


    @Override
    public String saveCartItem(MallShoppingCartItem mallShoppingCartItem) {
        MallShoppingCartItem temp = mallShoppingCartItemMapper.selectByUserIdAndGoodsId(mallShoppingCartItem.getUserId(), mallShoppingCartItem.getGoodsId());
        MallGoods mallGoods = mallGoodsMapper.selectgoodsById(mallShoppingCartItem.getGoodsId());
        //商品为空
        if (mallGoods == null) {
            return "商品不存在";
        }
        if (temp != null) {
            //已存在则修改该记录
            if(mallShoppingCartItem.getGoodsCount()>mallGoods.getStockNum()){
                return "库存不足";
            }
            temp.setGoodsCount(mallShoppingCartItem.getGoodsCount());
            return updateCartItem(temp);
        }
        //保存记录
        if (mallShoppingCartItemMapper.insertSelective(mallShoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateCartItem(MallShoppingCartItem mallShoppingCartItem) {
        MallShoppingCartItem mallShoppingCartItemUpdate = mallShoppingCartItemMapper.selectByPrimaryKey(mallShoppingCartItem.getCartItemId());
        if (mallShoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
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
        if (mallShoppingCartItemMapper.updateByPrimaryKeySelective(mallShoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId, Long userId) {
        MallShoppingCartItem mallShoppingCartItem = mallShoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemId);
        if (mallShoppingCartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!userId.equals(mallShoppingCartItem.getUserId())) {
            return false;
        }
        return mallShoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }

    @Override
    public List<FlowerMallShoppingCartItemVO> getMyShoppingCartItems(Long userId) {
        List<FlowerMallShoppingCartItemVO> flowerMallShoppingCartItemVOS = new ArrayList<>();
        //1.根据用户id 搜索购物车信息
        List<MallShoppingCartItem> mallShoppingCartItems = mallShoppingCartItemMapper.selectByUserId(userId);
        //2.购物车不为空的情况下
        if (!CollectionUtils.isEmpty(mallShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> goodsIds = mallShoppingCartItems.stream().map(MallShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<MallGoods> mallGoods = mallGoodsMapper.selectByPrimaryKeys(goodsIds);
            Map<Long, MallGoods> goodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(mallGoods)) {
                goodsMap = mallGoods.stream().collect(Collectors.toMap(MallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (MallShoppingCartItem mallShoppingCartItem : mallShoppingCartItems) {
                FlowerMallShoppingCartItemVO flowerMallShoppingCartItemVO = new FlowerMallShoppingCartItemVO();
                BeanUtil.copyProperties(mallShoppingCartItem, flowerMallShoppingCartItemVO);
                if (goodsMap.containsKey(mallShoppingCartItem.getGoodsId())) {
                    MallGoods mallGoodsTemp = goodsMap.get(mallShoppingCartItem.getGoodsId());
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
